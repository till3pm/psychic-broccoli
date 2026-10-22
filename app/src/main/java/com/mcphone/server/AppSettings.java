package com.mcphone.server;

import android.content.Context;
import android.content.SharedPreferences;

final class AppSettings {
    private AppSettings() { }
    private static SharedPreferences p(Context c) { return c.getApplicationContext().getSharedPreferences("app_settings", Context.MODE_PRIVATE); }
    static String language(Context c) { return p(c).getString("language", "de"); }
    static String theme(Context c) { return p(c).getString("theme", "light"); }
    static void setLanguage(Context c, String value) { p(c).edit().putString("language", value).apply(); }
    static void setTheme(Context c, String value) { p(c).edit().putString("theme", value).apply(); }
}
