/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Admins
 */
public class HocPhan {
    int mamh;
    String tenmh;
    int nhommh;
    String makhoa;

    public HocPhan(int mamh, String tenmh){
        this.mamh = mamh;
        this.tenmh = tenmh;
    }


    public HocPhan(int mamh, String tenmh, int nhommh, String makhoa) {
        this.mamh = mamh;
        this.tenmh = tenmh;
        this.nhommh = nhommh;
        this.makhoa = makhoa;
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

    public int getNhommh() {
        return nhommh;
    }

    public void setNhommh(int nhommh) {
        this.nhommh = nhommh;
    }

    public String getMakhoa() {
        return makhoa;
    }

    public void setMakhoa(String makhoa) {
        this.makhoa = makhoa;
    }
    @Override
    public String toString() {
        return "Hocphan{" + "mamh=" + mamh + ", tenmh=" + tenmh + ", nhommh=" + nhommh + ", makhoa=" + makhoa + '}';
    }
}
