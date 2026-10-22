package com.mcphone.server;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.File;
import java.util.List;

public class ServerListActivity extends Activity {
    private LinearLayout list;
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state); Ui.applyTheme(this); Ui.applySystemBars(this); Dirs.init(this); ServerProfiles.ensureMigrated(this); build();
    }
    private void build() {
        int pad = Ui.dp(this,20);
        LinearLayout outer = new LinearLayout(this); outer.setFitsSystemWindows(true); outer.setBackgroundColor(Ui.BG); outer.setOrientation(LinearLayout.VERTICAL); outer.setPadding(pad,pad,pad,pad); setContentView(outer);
        LinearLayout top = new LinearLayout(this); top.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout titleCol = new LinearLayout(this); titleCol.setOrientation(LinearLayout.VERTICAL);
        titleCol.addView(Ui.text(this,"$ mc-server --profiles",12,Ui.ACCENT));
        TextView title = Ui.text(this,"Deine Server",29,Ui.TEXT); title.setTypeface(Ui.font(this),android.graphics.Typeface.BOLD); titleCol.addView(title);
        TextView dots = Ui.text(this,"⋮",27,Ui.TEXT); dots.setGravity(Gravity.CENTER); dots.setBackground(Ui.box(this,Ui.FIELD,Ui.STROKE,14)); dots.setPadding(Ui.dp(this,13),Ui.dp(this,3),Ui.dp(this,13),Ui.dp(this,3)); dots.setContentDescription(L.t(this,"Einstellungen")); dots.setOnClickListener(v -> openSettingsMenu());
        top.addView(titleCol,new LinearLayout.LayoutParams(0,-2,1)); LinearLayout.LayoutParams dl=new LinearLayout.LayoutParams(-2,-2); dl.leftMargin=Ui.dp(this,12); top.addView(dots,dl); outer.addView(top);
        outer.addView(Ui.text(this,"Verwalte deine Minecraft-Welten an einem Ort.",14,Ui.DIM),Ui.row(this,5));
        ScrollView scroll = new ScrollView(this); list = new LinearLayout(this); list.setOrientation(LinearLayout.VERTICAL); scroll.addView(list,new ScrollView.LayoutParams(-1,-2)); LinearLayout.LayoutParams sl=new LinearLayout.LayoutParams(-1,0,1); sl.topMargin=Ui.dp(this,16); outer.addView(scroll,sl);
        TextView add = Ui.button(this,"+  Server erstellen",true); add.setOnClickListener(v -> createServer()); outer.addView(add,Ui.row(this,12)); render();
    }
    private void openSettingsMenu() {
        Ui.dialog(this).setItems(new String[]{L.t(this,"Einstellungen")},(d,which)->startActivity(new Intent(this,SettingsActivity.class))).show();
    }
    private void render() {
        list.removeAllViews(); List<String> ids=ServerProfiles.ids(this);
        if(ids.isEmpty()) { LinearLayout card=Ui.card(this); TextView h=Ui.text(this,"Noch keine Server",18,Ui.TEXT); h.setTypeface(Ui.font(this),android.graphics.Typeface.BOLD); card.addView(h); card.addView(Ui.text(this,"Erstelle deinen ersten Server. Du kannst mehrere Profile mit eigenen Einstellungen, Plugins und Config-Dateien verwalten.",14,Ui.DIM),Ui.row(this,8)); list.addView(card,Ui.row(this,0)); return; }
        for(String id:ids) addServerCard(id);
    }
    private void addServerCard(final String id) {
        Prefs p=new Prefs(this,id); LinearLayout card=Ui.card(this); LinearLayout heading=new LinearLayout(this); heading.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout names=new LinearLayout(this); names.setOrientation(LinearLayout.VERTICAL); TextView name=Ui.text(this,ServerProfiles.name(this,id),18,Ui.TEXT); name.setTypeface(Ui.font(this),android.graphics.Typeface.BOLD); names.addView(name);
        File jar=new File(Dirs.server(this,id),"server.jar"); String detail=p.b("setup",false)&&jar.isFile()?p.s("software","paper")+" "+p.s("version","")+" · Port "+p.i("port",25565):L.t(this,"Noch nicht eingerichtet"); names.addView(Ui.text(this,detail,12,Ui.DIM),Ui.row(this,3));
        boolean active=ServerService.running&&id.equals(ServerService.activeServerId); TextView status=Ui.text(this,active?(ServerService.ready?"● online":"● startet"):"● offline",11,active?(ServerService.ready?Ui.OK:Ui.WARN):Ui.DIM); status.setPadding(Ui.dp(this,9),Ui.dp(this,6),Ui.dp(this,9),Ui.dp(this,6)); status.setBackground(Ui.box(this,Ui.FIELD,Ui.STROKE,20)); heading.addView(names,new LinearLayout.LayoutParams(0,-2,1)); heading.addView(status); card.addView(heading);
        LinearLayout primary=new LinearLayout(this); TextView open=Ui.button(this,p.b("setup",false)&&jar.isFile()?"Öffnen":"Einrichten",true); open.setOnClickListener(v->openServer(id)); primary.addView(open,new LinearLayout.LayoutParams(0,-2,1)); TextView rename=Ui.button(this,"Name",false); rename.setOnClickListener(v->rename(id)); LinearLayout.LayoutParams rp=new LinearLayout.LayoutParams(0,-2,.65f); rp.leftMargin=Ui.dp(this,8); primary.addView(rename,rp); card.addView(primary,Ui.row(this,12));
        LinearLayout tools=new LinearLayout(this); TextView plugins=Ui.button(this,"Plugins",false); plugins.setOnClickListener(v->startActivity(new Intent(this,PluginActivity.class).putExtra("server_id",id))); TextView config=Ui.button(this,"Configs",false); config.setOnClickListener(v->startActivity(new Intent(this,ConfigActivity.class).putExtra("server_id",id))); tools.addView(plugins,new LinearLayout.LayoutParams(0,-2,1)); LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(0,-2,1); cp.leftMargin=Ui.dp(this,8); tools.addView(config,cp); card.addView(tools,Ui.row(this,8)); list.addView(card,Ui.row(this,12));
    }
    private void openServer(String id) { Intent i=new Intent(this,ConsoleActivity.class).putExtra("server_id",id); startActivity(i); }
    private void createServer() {
        EditText input=Ui.input(this,"","z. B. Survival-Welt",false); input.setSingleLine(true); input.setBackground(Ui.box(this,Ui.FIELD,Ui.STROKE,16));
        LinearLayout wrap=new LinearLayout(this); wrap.setPadding(Ui.dp(this,5),Ui.dp(this,4),Ui.dp(this,5),Ui.dp(this,4)); wrap.setBackground(Ui.box(this,Ui.CARD,Ui.STROKE,18)); wrap.addView(input,new LinearLayout.LayoutParams(-1,-2));
        Ui.dialog(this).setTitle(L.t(this,"Neuer Server")).setMessage(L.t(this,"Gib deinem Server einen Namen.")).setView(wrap).setNegativeButton(L.t(this,"Abbrechen"),null).setPositiveButton("Weiter",(d,w)->{String id=ServerProfiles.create(this,input.getText().toString()); startActivity(new Intent(this,SetupActivity.class).putExtra("server_id",id));}).show();
    }
    private void rename(String id) {
        EditText input=Ui.input(this,ServerProfiles.name(this,id),"Servername",false); input.setSingleLine(true); input.setBackground(Ui.box(this,Ui.FIELD,Ui.STROKE,16));
        LinearLayout wrap=new LinearLayout(this); wrap.setPadding(Ui.dp(this,5),Ui.dp(this,4),Ui.dp(this,5),Ui.dp(this,4)); wrap.setBackground(Ui.box(this,Ui.CARD,Ui.STROKE,18)); wrap.addView(input,new LinearLayout.LayoutParams(-1,-2));
        Ui.dialog(this).setTitle(L.t(this,"Server benennen")).setView(wrap).setNegativeButton(L.t(this,"Abbrechen"),null).setPositiveButton(L.t(this,"Speichern"),(d,w)->{ServerProfiles.rename(this,id,input.getText().toString());render();}).show();
    }
    @Override protected void onResume(){super.onResume(); Ui.applyTheme(this); Ui.applySystemBars(this); if(list!=null) build();}
}
