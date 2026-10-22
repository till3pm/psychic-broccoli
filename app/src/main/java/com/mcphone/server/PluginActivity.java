package com.mcphone.server;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PluginActivity extends Activity {
    private static final int PICK_JARS = 4172;
    private static final long MAX_JAR_BYTES = 128L * 1024L * 1024L;
    private String serverId;
    private Prefs p;
    private EditText urlInput;
    private TextView status;
    private LinearLayout installedList;
    private final ExecutorService io = Executors.newSingleThreadExecutor();
    private final android.os.Handler main = new android.os.Handler(android.os.Looper.getMainLooper());

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b); Ui.applyTheme(this); Ui.applySystemBars(this); Dirs.init(this);
        serverId = ServerProfiles.safeId(getIntent().getStringExtra("server_id")); p = new Prefs(this, serverId); build();
    }
    private void build() {
        LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(Ui.dp(this,20),Ui.dp(this,20),Ui.dp(this,20),Ui.dp(this,16)); root.setBackgroundColor(Ui.BG); root.setFitsSystemWindows(true); setContentView(root);
        root.addView(Ui.text(this,"$ plugin install",12,Ui.ACCENT)); TextView title=Ui.text(this,ServerProfiles.name(this,serverId),26,Ui.TEXT); title.setTypeface(Ui.font(this),android.graphics.Typeface.BOLD); root.addView(title,Ui.row(this,4)); root.addView(Ui.text(this,"Plugin-Installer",14,Ui.DIM),Ui.row(this,2));
        ScrollView sc=new ScrollView(this); LinearLayout body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);sc.addView(body,new ScrollView.LayoutParams(-1,-2));root.addView(sc,new LinearLayout.LayoutParams(-1,0,1));
        LinearLayout card=Ui.card(this);card.addView(Ui.label(this,"JAR-Downloadlink"));urlInput=Ui.input(this,p.s("pluginUrl",""),"https://…/plugin.jar",false);urlInput.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_URI);urlInput.setSingleLine(true);card.addView(urlInput,Ui.row(this,8));
        TextView note=Ui.text(this,"Füge einen direkten HTTPS-Link zu einer .jar-Datei ein. Nutze nur Plugins aus Quellen, denen du vertraust.",12,Ui.DIM);note.setLineSpacing(0,1.15f);card.addView(note,Ui.row(this,8));TextView install=Ui.button(this,"Plugin installieren",true);install.setOnClickListener(v->downloadUrl());card.addView(install,Ui.row(this,10));
        TextView importButton=Ui.button(this,"Dateien vom Handy importieren",false);importButton.setOnClickListener(v->pickFiles());card.addView(importButton,Ui.row(this,8));
        TextView importHint=Ui.text(this,"Du kannst mehrere .jar-Dateien gleichzeitig auswählen.",12,Ui.DIM);card.addView(importHint,Ui.row(this,5));status=Ui.text(this,"",12,Ui.DIM);status.setLineSpacing(0,1.15f);card.addView(status,Ui.row(this,8));body.addView(card);
        LinearLayout current=Ui.card(this);current.addView(Ui.label(this,"Installierte JAR-Dateien"));installedList=new LinearLayout(this);installedList.setOrientation(LinearLayout.VERTICAL);current.addView(installedList,Ui.row(this,8));body.addView(current,Ui.row(this,14));TextView back=Ui.button(this,"Zurück zur Serverliste",false);back.setOnClickListener(v->finish());root.addView(back,Ui.row(this,12));refreshPlugins();
        if(!"paper".equalsIgnoreCase(p.s("software","paper"))){status.setText(L.t(this,"Plugins benötigen Paper; Vanilla unterstützt das Plugin-System nicht."));status.setTextColor(Ui.WARN);}
    }
    private boolean canInstall(){
        if(!"paper".equalsIgnoreCase(p.s("software","paper"))){status.setText("✗ Plugins funktionieren nur mit Paper, nicht mit Vanilla.");status.setTextColor(Ui.ERR);return false;}
        if(ServerService.running&&serverId.equals(ServerService.activeServerId)){status.setText(L.t(this,"Bitte stoppe diesen Server vor der Installation."));status.setTextColor(Ui.WARN);return false;}
        return true;
    }
    private void downloadUrl(){
        if(!canInstall())return;String link=urlInput.getText().toString().trim();final String filename;
        try{URL u=new URL(link);if(!"https".equalsIgnoreCase(u.getProtocol()))throw new IllegalArgumentException("Nur HTTPS-Links werden akzeptiert.");String path=u.getPath();filename=cleanJarName(path.substring(path.lastIndexOf('/')+1));if(filename==null)throw new IllegalArgumentException("Der Link muss direkt auf eine .jar-Datei zeigen.");}
        catch(Exception e){status.setText("✗ Ungültiger Link: "+e.getMessage());status.setTextColor(Ui.ERR);return;}
        p.put("pluginUrl",link);status.setText("Download wird gestartet: "+filename);status.setTextColor(Ui.DIM);
        io.execute(()->{try{File dir=pluginDir();if(!dir.exists()&&!dir.mkdirs())throw new java.io.IOException("Plugin-Ordner konnte nicht angelegt werden.");File dest=uniqueFile(dir,filename);Net.download(link,dest,msg->main.post(()->status.setText(msg)),filename);main.post(()->{status.setText("✓ Installiert: "+dest.getName()+". Wird beim nächsten Serverstart geladen.");status.setTextColor(Ui.OK);refreshPlugins();});}catch(Exception e){main.post(()->{status.setText("✗ Installation fehlgeschlagen: "+e.getMessage());status.setTextColor(Ui.ERR);});}});
    }
    private void pickFiles(){
        if(!canInstall())return;
        Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);intent.addCategory(Intent.CATEGORY_OPENABLE);intent.setType("*/*");intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);intent.putExtra(Intent.EXTRA_MIME_TYPES,new String[]{"application/java-archive","application/octet-stream","application/zip"});intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try{startActivityForResult(intent,PICK_JARS);}catch(Exception e){status.setText("Dateiauswahl konnte nicht geöffnet werden: "+e.getMessage());status.setTextColor(Ui.ERR);}
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != PICK_JARS || resultCode != RESULT_OK || data == null) return;
        if (!canInstall()) return;
        java.util.ArrayList<Uri> uris = new java.util.ArrayList<>();
        if (data.getClipData() != null) {
            for (int i = 0; i < data.getClipData().getItemCount(); i++) uris.add(data.getClipData().getItemAt(i).getUri());
        } else if (data.getData() != null) uris.add(data.getData());
        if (uris.isEmpty()) return;
        final java.util.ArrayList<Uri> selected = uris;
        status.setText("Importiere " + selected.size() + " Datei(en)…");
        status.setTextColor(Ui.DIM);
        io.execute(() -> {
            int ok = 0;
            java.util.ArrayList<String> errors = new java.util.ArrayList<>();
            try {
                File dir = pluginDir();
                if (!dir.exists() && !dir.mkdirs()) throw new java.io.IOException("Plugin-Ordner konnte nicht angelegt werden.");
                for (Uri uri : selected) {
                    String name = queryName(uri);
                    String safe = cleanJarName(name);
                    if (safe == null) {
                        errors.add((name == null ? uri.getLastPathSegment() : name) + ": keine .jar-Datei");
                        continue;
                    }
                    File dest = uniqueFile(dir, safe);
                    File tmp = new File(dest.getPath() + ".part");
                    try (InputStream in = getContentResolver().openInputStream(uri)) {
                        if (in == null) throw new java.io.IOException("Datei konnte nicht gelesen werden");
                        long total = 0;
                        try (FileOutputStream out = new FileOutputStream(tmp)) {
                            byte[] buf = new byte[65536];
                            int n;
                            while ((n = in.read(buf)) != -1) {
                                total += n;
                                if (total > MAX_JAR_BYTES) throw new java.io.IOException("Datei größer als 128 MB");
                                out.write(buf, 0, n);
                            }
                        }
                        if (!tmp.renameTo(dest)) throw new java.io.IOException("Datei konnte nicht gespeichert werden");
                        ok++;
                    } catch (Exception e) {
                        tmp.delete();
                        errors.add(name + ": " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                errors.add(String.valueOf(e.getMessage()));
            }
            final int count = ok;
            final java.util.ArrayList<String> err = errors;
            main.post(() -> {
                String msg = "✓ " + count + " JAR-Datei(en) importiert.";
                if (!err.isEmpty()) msg += "\nNicht importiert: " + android.text.TextUtils.join("; ", err);
                status.setText(msg);
                status.setTextColor(err.isEmpty() ? Ui.OK : Ui.WARN);
                refreshPlugins();
            });
        });
    }
    private String queryName(Uri uri){Cursor c=null;try{c=getContentResolver().query(uri,null,null,null,null);if(c!=null&&c.moveToFirst()){int i=c.getColumnIndex(OpenableColumns.DISPLAY_NAME);if(i>=0)return c.getString(i);}}catch(Exception ignored){}finally{if(c!=null)c.close();}return uri.getLastPathSegment();}
    private String cleanJarName(String name){if(name==null)return null;String n=name.substring(name.lastIndexOf('/')+1).trim();if(!n.toLowerCase(Locale.ROOT).endsWith(".jar"))return null;n=n.replaceAll("[^A-Za-z0-9._ -]","_").replace(' ','_');if(n.length()>120){String ext=n.substring(n.length()-4);n=n.substring(0,116)+ext;}if(n.equalsIgnoreCase(".jar")||n.startsWith("."))return null;return n;}
    private File uniqueFile(File dir,String name){File f=new File(dir,name);if(!f.exists())return f;String stem=name.substring(0,name.length()-4);for(int i=2;i<10000;i++){f=new File(dir,stem+" ("+i+").jar");if(!f.exists())return f;}return new File(dir,stem+"_"+System.currentTimeMillis()+".jar");}
    private File pluginDir(){return new File(Dirs.server(this,serverId),"plugins");}
    private void refreshPlugins(){if(installedList==null)return;installedList.removeAllViews();File[] files=pluginDir().listFiles((d,n)->n.toLowerCase(Locale.ROOT).endsWith(".jar"));if(files==null||files.length==0){installedList.addView(Ui.text(this,"Noch keine Plugins installiert.",13,Ui.DIM));return;}Arrays.sort(files,(a,b)->a.getName().compareToIgnoreCase(b.getName()));for(File f:files)installedList.addView(Ui.text(this,"•  "+f.getName(),13,Ui.TEXT),Ui.row(this,4));}
    @Override protected void onDestroy(){io.shutdownNow();super.onDestroy();}
}
