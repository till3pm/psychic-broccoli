package com.mcphone.server;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SettingsActivity extends Activity {
    private LinearLayout root;
    @Override protected void onCreate(Bundle b){super.onCreate(b);Ui.applyTheme(this);Ui.applySystemBars(this);build();}
    private void build(){
        root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(Ui.dp(this,20),Ui.dp(this,20),Ui.dp(this,20),Ui.dp(this,18));root.setBackgroundColor(Ui.BG);root.setFitsSystemWindows(true);setContentView(root);
        root.addView(Ui.text(this,"$ mc-server --settings",12,Ui.ACCENT));TextView title=Ui.text(this,"Einstellungen",28,Ui.TEXT);title.setTypeface(Ui.font(this),android.graphics.Typeface.BOLD);root.addView(title,Ui.row(this,3));
        ScrollView scroll=new ScrollView(this);LinearLayout body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);scroll.addView(body,new ScrollView.LayoutParams(-1,-2));root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        LinearLayout language=Ui.card(this);language.addView(Ui.label(this,"Sprache"));language.addView(Ui.text(this,"Wähle die Sprache der App-Oberfläche.",13,Ui.DIM),Ui.row(this,5));
        String[] codes={"de","en","es","fr","tr"};String[] names={"Deutsch","English","Español","Français","Türkçe"};
        for(int i=0;i<codes.length;i++){final String code=codes[i];TextView row=Ui.text(this,(code.equals(AppSettings.language(this))?"●  ":"○  ")+names[i],15,code.equals(AppSettings.language(this))?Ui.ACCENT:Ui.TEXT);row.setPadding(Ui.dp(this,10),Ui.dp(this,12),Ui.dp(this,10),Ui.dp(this,12));row.setBackground(Ui.box(this,code.equals(AppSettings.language(this))?Ui.FIELD:Ui.CARD,Ui.STROKE,12));row.setOnClickListener(v->{AppSettings.setLanguage(this,code);recreate();});language.addView(row,Ui.row(this,6));}body.addView(language,Ui.row(this,0));
        LinearLayout theme=Ui.card(this);theme.addView(Ui.label(this,"Erscheinungsbild"));theme.addView(Ui.text(this,"Hell oder dunkel – Schrift und Akzentfarbe bleiben gleich.",13,Ui.DIM),Ui.row(this,5));
        String active=AppSettings.theme(this);LinearLayout options=new LinearLayout(this);String[] themes={"light","dark"};String[] labels={"Hell","Dunkel"};for(int i=0;i<2;i++){final String key=themes[i];TextView button=Ui.button(this,L.t(this,labels[i]),key.equals(active));button.setBackground(Ui.box(this,key.equals(active)?Ui.ACCENT:Ui.FIELD,key.equals(active)?0:Ui.STROKE,13));button.setTextColor(key.equals(active)?Ui.ONACC:Ui.TEXT);button.setOnClickListener(v->{AppSettings.setTheme(this,key);Ui.applyTheme(this);Ui.applySystemBars(this);recreate();});LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,-2,1);if(i>0)lp.leftMargin=Ui.dp(this,8);options.addView(button,lp);}theme.addView(options,Ui.row(this,10));body.addView(theme,Ui.row(this,14));
        TextView back=Ui.button(this,"Zurück",false);back.setOnClickListener(v->finish());root.addView(back,Ui.row(this,14));
    }
}
