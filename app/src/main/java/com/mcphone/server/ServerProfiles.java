package com.mcphone.server;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

final class ServerProfiles {
    private static final String STORE = "mc_profiles";
    private ServerProfiles() { }
    private static SharedPreferences p(Context c) { return c.getApplicationContext().getSharedPreferences(STORE, 0); }
    static synchronized void ensureMigrated(Context c) {
        SharedPreferences sp = p(c);
        if (sp.contains("ids")) return;
        SharedPreferences old = c.getApplicationContext().getSharedPreferences("mc", 0);
        if (Boolean.parseBoolean(old.getString("setup", "false")) || new File(c.getFilesDir(), "server/server.jar").isFile()) {
            sp.edit().putString("ids", "[\"default\"]").putString("name.default", "Minecraft Server").apply();
        } else sp.edit().putString("ids", "[]").apply();
    }
    static synchronized List<String> ids(Context c) {
        ensureMigrated(c);
        ArrayList<String> out = new ArrayList<>();
        try { JSONArray a = new JSONArray(p(c).getString("ids", "[]")); for (int i=0;i<a.length();i++) out.add(safeId(a.getString(i))); }
        catch (Exception ignored) { }
        return out;
    }
    static synchronized String create(Context c, String name) {
        List<String> ids = ids(c);
        String id = "s" + Long.toString(System.currentTimeMillis(), 36);
        while (ids.contains(id)) id = "s" + Long.toString(System.nanoTime(), 36);
        ids.add(id);
        JSONArray a = new JSONArray(); for (String v : ids) a.put(v);
        p(c).edit().putString("ids", a.toString()).putString("name." + id, cleanName(name)).apply();
        return id;
    }
    static String name(Context c, String id) { return p(c).getString("name." + safeId(id), "default".equals(id) ? "Minecraft Server" : "Minecraft Server"); }
    static void rename(Context c, String id, String name) { p(c).edit().putString("name." + safeId(id), cleanName(name)).apply(); }
    static String cleanName(String name) { String n = name == null ? "" : name.trim(); if (n.isEmpty()) n = "Minecraft Server"; return n.length() > 48 ? n.substring(0,48) : n; }
    static String safeId(String id) { return id != null && id.matches("[A-Za-z0-9_-]{1,64}") ? id : "default"; }
}
