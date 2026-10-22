package com.mcphone.server;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.content.Context;
import android.view.Window;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.TextWatcher;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

final class Ui {
    static int BG = 0xFFFAF9F5, CARD = 0xFFFFFFFF, FIELD = 0xFFF3F1EA, STROKE = 0xFFE4E0D6,
            TEXT = 0xFF1F1E1D, DIM = 0xFF6F6B63, ACCENT = 0xFFD97757, ONACC = 0xFFFFFFFF,
            OK = 0xFF2E7D4F, WARN = 0xFFB7791F, ERR = 0xFFC2412D;

    static void applyTheme(Context c) {
        if ("dark".equals(AppSettings.theme(c))) {
            BG = 0xFF191816; CARD = 0xFF24221F; FIELD = 0xFF302D29; STROKE = 0xFF46413A;
            TEXT = 0xFFF2EEE7; DIM = 0xFFB1AAA0; ACCENT = 0xFFD97757; ONACC = 0xFFFFFFFF;
            OK = 0xFF82C99A; WARN = 0xFFE0B46A; ERR = 0xFFFF8D7A;
        } else {
            BG = 0xFFFAF9F5; CARD = 0xFFFFFFFF; FIELD = 0xFFF3F1EA; STROKE = 0xFFE4E0D6;
            TEXT = 0xFF1F1E1D; DIM = 0xFF6F6B63; ACCENT = 0xFFD97757; ONACC = 0xFFFFFFFF;
            OK = 0xFF2E7D4F; WARN = 0xFFB7791F; ERR = 0xFFC2412D;
        }
    }

    static void applySystemBars(Activity a) {
        boolean dark = "dark".equals(AppSettings.theme(a));
        a.getWindow().setStatusBarColor(BG);
        a.getWindow().setNavigationBarColor(BG);
        a.getWindow().getDecorView().setSystemUiVisibility(dark ? 0 : (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR));
    }

    static AlertDialog.Builder dialog(Context c) {
        int style = "dark".equals(AppSettings.theme(c)) ? android.R.style.Theme_Material_Dialog_Alert : android.R.style.Theme_Material_Light_Dialog_Alert;
        return new AlertDialog.Builder(new ContextThemeWrapper(c, style));
    }

    interface Pick { void on(int index); }

    abstract static class Watch implements TextWatcher {
        public void beforeTextChanged(CharSequence s, int a, int b, int c) { }
        public void onTextChanged(CharSequence s, int a, int b, int c) { }
    }

    private static Typeface tf;

    static Typeface font(Context c) {
        if (tf == null) tf = Fonts.load(c);
        return tf;
    }

    static int dp(Context c, float v) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v, c.getResources().getDisplayMetrics());
    }

    static GradientDrawable box(Context c, int fill, int stroke, float radiusDp) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(fill);
        g.setCornerRadius(dp(c, radiusDp));
        if (stroke != 0) g.setStroke(dp(c, 1), stroke);
        return g;
    }

    static TextView text(Context c, String s, float sp, int color) {
        TextView t = new TextView(c);
        t.setText(L.t(c, s));
        t.setTextSize(sp);
        t.setTextColor(color);
        t.setTypeface(font(c));
        return t;
    }

    static LinearLayout.LayoutParams row(Context c, int topDp) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.topMargin = dp(c, topDp);
        return p;
    }

    static LinearLayout card(Context c) {
        LinearLayout l = new LinearLayout(c);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(dp(c, 16), dp(c, 14), dp(c, 16), dp(c, 14));
        l.setBackground(box(c, CARD, STROKE, 16));
        return l;
    }

    static TextView label(Context c, String s) {
        TextView t = text(c, L.t(c, s).toUpperCase(java.util.Locale.ROOT), 11, DIM);
        t.setLetterSpacing(0.12f);
        return t;
    }

    static TextView button(Context c, String s, boolean primary) {
        TextView t = text(c, s, 15, primary ? ONACC : TEXT);
        t.setTypeface(font(c), Typeface.BOLD);
        t.setGravity(Gravity.CENTER);
        t.setPadding(dp(c, 18), dp(c, 14), dp(c, 18), dp(c, 14));
        t.setBackground(box(c, primary ? ACCENT : 0, primary ? 0 : STROKE, 14));
        t.setClickable(true);
        return t;
    }

    static EditText input(Context c, String value, String hint, boolean number) {
        EditText e = new EditText(c);
        e.setText(value);
        e.setHint(L.t(c, hint));
        e.setTextColor(TEXT);
        e.setHintTextColor(0xFF9A968D);
        e.setTypeface(font(c));
        e.setTextSize(15);
        e.setSingleLine(true);
        e.setBackground(box(c, FIELD, STROKE, 12));
        e.setPadding(dp(c, 14), dp(c, 12), dp(c, 14), dp(c, 12));
        if (number) e.setInputType(InputType.TYPE_CLASS_NUMBER);
        return e;
    }

    static void chip(Context c, TextView t, boolean on) {
        t.setTextColor(on ? ONACC : TEXT);
        t.setBackground(box(c, on ? ACCENT : FIELD, on ? 0 : STROKE, 12));
    }

    static LinearLayout chips(Context c, String[] labels, int sel, Pick cb) {
        LinearLayout row = new LinearLayout(c);
        TextView[] tv = new TextView[labels.length];
        for (int i = 0; i < labels.length; i++) {
            final int idx = i;
            TextView t = text(c, labels[i], 14, TEXT);
            t.setGravity(Gravity.CENTER);
            t.setPadding(dp(c, 6), dp(c, 12), dp(c, 6), dp(c, 12));
            chip(c, t, i == sel);
            t.setOnClickListener(v -> {
                for (int j = 0; j < tv.length; j++) chip(c, tv[j], j == idx);
                cb.on(idx);
            });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, -2, 1);
            if (i > 0) lp.leftMargin = dp(c, 8);
            row.addView(t, lp);
            tv[i] = t;
        }
        return row;
    }
}
