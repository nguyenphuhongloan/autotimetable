/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import classes.model.HocPhan;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class GiaiThuatDiTruyen {
    public static final int pop_size = 10;
    public static final double crossOver_rate = 0.5;
    public static final double mutation_rate = 0.2;
    public static final int soLanLap = 100;
    private static Timetable dsMonDangKy;
    public static void regTimetable(Timetable dsMonDangKy){
        GiaiThuatDiTruyen.dsMonDangKy = dsMonDangKy;
    }

    /**
     * Do schedule the timetable was registered (Sắp xếp thời khoá biểu được đăng ký)
     */
    public static void schedulingTimetable()
    {
        int generation = -1;
        while(generation < soLanLap)
        {
            generation += 1;
        }
    }
    public static int getNSTLength() {
        return GiaiThuatDiTruyen.dsMonDangKy.size();
    }
    public static ArrayList<MonDangKy> getDSMonDangky()
    {
        return GiaiThuatDiTruyen.dsMonDangKy.getDSMonHoc();
    }
    
}
