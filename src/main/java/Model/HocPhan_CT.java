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
public class HocPhan_CT {
    int mamh;
    String tenmh;
    boolean HPstatus;
    int sttmh;
    int manhom;
    int dieukienloc;
    ArrayList<CTMonhoc> ctmh;

    public HocPhan_CT(int mamh, String tenmh, int sttmh, int manhom) {
        this.mamh=mamh;
        this.tenmh=tenmh;
        this.sttmh=sttmh;
        ctmh = new ArrayList<>();
        this.manhom=manhom;
        HPstatus = false;
    }

    public int getMamh() {
        return mamh;
    }

    public void setMamh(int mamh) {
        this.mamh = mamh;
    }

    public String getTenmh() {
        return tenmh;
    }

    public void setTenmh(String tenmh) {
        this.tenmh = tenmh;
    }
        
    public int getSttmh() {
        return sttmh;
    }

    public void setSttmh(int sttmh) {
        this.sttmh = sttmh;
    }

    public int getDieukienloc() {
        return dieukienloc;
    }

    public void setDieukienloc(int floor) {
        this.dieukienloc = floor;
    }


    
    public ArrayList<CTMonhoc> getCtmh() {
        return ctmh;
    }

    public int getManhom() {
        return manhom;
    }

    public void setManhom(int manhom) {
        this.manhom = manhom;
    }

    public void setCtmh(ArrayList<CTMonhoc> ctmh) {
        this.ctmh = ctmh;
    }
    

    public boolean isHPstatus() {
        return HPstatus;
    }

    public void setHPstatus(boolean HPstatus) {
        this.HPstatus = HPstatus;
    }

    @Override

    public String toString() {
        return "HocPhan_CT{" + "HPstatus=" + HPstatus + ", sttmh=" + sttmh + ", tenmh=" + tenmh + ", manhom=" + manhom + ", ctmh=" + ctmh/* + ", trung=" + trung*/ + '}';
    }
    
    
}
