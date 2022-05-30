/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Admins
 */
public class MonHoc {
    boolean MHstatus;
    int mamh;
    String tenmh;
    int SoNhom;
    int priority;
    ArrayList<HocPhan_CT> mhp;

    public MonHoc() {
        MHstatus = false;
        mhp = new ArrayList<>();
    }
    public MonHoc(int mamh, String tenmh) {
        this.mamh = mamh;
        this.tenmh = tenmh;
    }

    public boolean isMHstatus() {
        return MHstatus;
    }

    public void setMHstatus(boolean MHstatus) {
        this.MHstatus = MHstatus;
    }

    public String getTenmh() {
        return tenmh;
    }

    public void setTenmh(String tenmh) {
        this.tenmh = tenmh;
    }


    public boolean isStatus() {
        return MHstatus;
    }

    public void setStatus(boolean status) {
        this.MHstatus = status;
    }

    public int getMamh() {
        return mamh;
    }

    public void setMamh(int mamh) {
        this.mamh = mamh;
    }

    public int getSoNhom() {
        return SoNhom;
    }

    public void setSoNhom(int SoNhom) {
        this.SoNhom = SoNhom;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ArrayList<HocPhan_CT> getMhp() {
        return mhp;
    }

    public void setMhp(ArrayList<HocPhan_CT> hp) {
        this.mhp = hp;
    }

    //@Override
    /*public String toString() {
        return "MonHoc{" + "MHstatus=" + MHstatus + ", mamh=" + mamh + ", tenmh=" + tenmh + ", SoNhom=" + SoNhom + ", priority=" + priority + ", mhp=" + mhp + '}';
    }*/
    
}
