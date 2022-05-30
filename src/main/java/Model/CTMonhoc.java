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
public class CTMonhoc {
    int mamh;
    int nhommh;
    String thu;
    int tietbd;
    int sotiet;
    String maphong;
    int magv;
    String thuchanh;

    public CTMonhoc() {
        this.mamh = 1;
        this.nhommh = 1;
        this.thu = "1";
        this.tietbd = 1;
        this.sotiet = 1;
        this.maphong = "1";
        this.magv = 1;
        this.thuchanh = "1";
    }

    public CTMonhoc(int mamh, int nhommh, String thu, int tietbd, int sotiet, String maphong, int magv, String thuchanh) {
        this.mamh = mamh;
        this.nhommh = nhommh;
        this.thu = thu;
        this.tietbd = tietbd;
        this.sotiet = sotiet;
        this.maphong = maphong;
        this.magv = magv;
        this.thuchanh = thuchanh;
    }

    public int getMamh() {
        return mamh;
    }

    public void setMamh(int mamh) {
        this.mamh = mamh;
    }

    public int getNhommh() {
        return nhommh;
    }

    public void setNhommh(int nhommh) {
        this.nhommh = nhommh;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public int getTietbd() {
        return tietbd;
    }

    public void setTietbd(int tietbd) {
        this.tietbd = tietbd;
    }

    public int getSotiet() {
        return sotiet;
    }

    public void setSotiet(int sotiet) {
        this.sotiet = sotiet;
    }

    public String getMaphong() {
        return maphong;
    }

    public void setMaphong(String maphong) {
        this.maphong = maphong;
    }

    public int getMagv() {
        return magv;
    }

    public void setMagv(int magv) {
        this.magv = magv;
    }

    public String getThuchanh() {
        return thuchanh;
    }

    public void setThuchanh(String thuchanh) {
        this.thuchanh = thuchanh;
    }

    @Override
    public String toString() {
        return "CTMonhoc{" + "mamh=" + mamh + ", nhommh=" + nhommh + ", thu=" + thu + ", tietbd=" + tietbd + ", sotiet=" + sotiet + ", maphong=" + maphong + ", magv=" + magv + ", thuchanh=" + thuchanh + '}';
    }

   
}
