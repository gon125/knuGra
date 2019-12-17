package com.knucse.knugra.UI_package.career_success;

import android.widget.ProgressBar;

public class RecyclerItem {
    private String sc_item;
    private String sc_pct;
    private String sc_percent;
    private ProgressBar success_prg;
    private int prg;

    public String getSc_item() {
        return sc_item;
    }

    public void setSc_item(String sc_item) {
        this.sc_item = sc_item;
    }

    public String getSc_pct() {
        return sc_pct;
    }

    public void setSc_pct(String sc_pct) {
        this.sc_pct = sc_pct;
    }

    public String getSc_percent() {
        return sc_percent;
    }

    public void setSc_percent(String sc_percent) {
        this.sc_percent = sc_percent;
    }

    public ProgressBar getSuccess_prg() { return success_prg; }

    public void setSuccess_prg(ProgressBar success_prg) {
        this.success_prg = success_prg;
    }

    public int getPrg() {
        return prg;
    }

    public void setPrg(int prg) {
        this.prg = prg;
    }
}
