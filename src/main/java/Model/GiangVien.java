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
public class GiangVien {
    int magv;
    String tengv;

    public int getMagv() {
        return magv;
    }

    public void setMagv(int magv) {
        this.magv = magv;
    }

    public String getTengv() {
        return tengv;
    }

    public void setTengv(String tengv) {
        this.tengv = tengv;
    }

    public GiangVien(int magv, String tengv) {
        this.magv = magv;
        this.tengv = tengv;
    }

    @Override
    public String toString() {
        return "GiangVien{" + "magv=" + magv + ", tengv=" + tengv + '}';
    }
    
}
