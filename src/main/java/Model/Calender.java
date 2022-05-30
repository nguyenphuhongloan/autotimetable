/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.Xampp_Connection;
import java.util.ArrayList;

/**
 *
 * @author Admins
 */
public class Calender {
    Xampp_Connection conn = new Xampp_Connection();
    private boolean[][] week = new boolean[7][13];
    public ArrayList<HocPhan_CT> ct = new ArrayList<>(); //Mảng lưu chi tiết học phần các môn sẽ chọn
    ArrayList<HocPhan> mhp = new ArrayList<>(); //Mảng đẩy thông tin học phần tạm lên - mảng này chỉ để lấy mã nhóm và tên môn học
    public ArrayList<MonHoc> mmh; //Mảng lưu chi tiết về môn học, học phần và chi tiết học phần môn học sẽ chọn
    public ArrayList<Integer> mmht; //Mảng lưu môn bị trùng lịch không đăng ký được
    HocPhan_CT hpn; //biến tạm để lưu Học phần kèm chi tiết
    MonHoc mh; //biến tạm để lưu Môn học
    int gioihan; //biến lưu giá trị tránh truy xuất nhiều lần
    int stt_montrung; // biến tạm lưu số thứ tự môn bị trùng không đăng ký được


    // Hàm khởi tạo, đẩy dự liệu môn học lên
    public Calender (int[] mamh) {
        conn.getConnectionDB();
        int i,j;
        mmh=new ArrayList<>();
        mmht=new ArrayList<>();
        stt_montrung = 0;
        for (i = 0; i < mamh.length; i++) {
            mh = new MonHoc();
            mh.setMamh(mamh[i]);
            mhp = new ArrayList<>();
            mhp.addAll(conn.uploadHP(mamh[i]));
            mh.setSoNhom(mhp.size());
            mh.setTenmh(mhp.get(0).getTenmh());
            for (j=0;j<mh.SoNhom;j++) {
                hpn = new HocPhan_CT(mh.mamh,mh.tenmh,i,mhp.get(j).getNhommh());
                mh.mhp.add(hpn);
            }
            mmh.add(mh);
        }
        for (i=0;i<mmh.size();i++){
            gioihan=mmh.get(i).mhp.size();
            for (j=0;j<gioihan;j++) {
                mmh.get(i).mhp.get(j).ctmh.addAll(conn.uploadCTMH(mmh.get(i).mamh,mmh.get(i).mhp.get(j).getManhom()));
            }
        }
        for (i=0;i<7;i++)
            for (j=0;j<13;j++)
                week[i][j]=false;
    }

    public Calender(int[] a, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void XepLich(int dieukien) {
        switch (dieukien){
            case 1: {
                assignLesson();
                SortAll();
                while (VetCan(0)<mmh.size()) {
                    research();
                }
                break;
            }
            case 2: {
                while (VetCanNangCao(0)<mmh.size()) {
                    research();
                }
                break;
            }
            case 3:{
                assignFloor();
                SortAll();
                while (VetCan(0)<mmh.size()) {
                    research();
                }
                break;
            }
            case 4: {
                while (VetCan(0)<mmh.size())
                    research();
                break;
            }
            default: {
                System.out.print("dieu kien loc sai");
                break;
            }
        }
    }

    // Hàm kiểm tra học phần có trùng lịch không
    private boolean checkTrung(ArrayList<CTMonhoc> a) {
        for (int i=0;i<a.size();i++) {
            for (int j = a.get(i).tietbd;j<a.get(i).tietbd+a.get(i).sotiet;j++) {
                if (week[exchangeThu(a.get(i).thu)][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    // hàm chiếm lịch
    public void fillWeek(ArrayList<CTMonhoc> ctmh) {
        for (int i=0;i<ctmh.size();i++) {
            for(int j=ctmh.get(i).tietbd;j<ctmh.get(i).tietbd+ctmh.get(i).sotiet;j++) {
                week[exchangeThu(ctmh.get(i).thu)][j]=true;
            }
        }
    }

    // hàm xóa khỏi lịch
    public void deleteWeek(ArrayList<CTMonhoc> ctmh) {
        for (int i=0;i<ctmh.size();i++) {
            for(int j=ctmh.get(i).tietbd;j<ctmh.get(i).tietbd+ctmh.get(i).sotiet;j++) {
                week[exchangeThu(ctmh.get(i).thu)][j]=false;
            }
        }
    }

    // Hàm quy đổi thứ ra int tương ứng
    public int exchangeThu(String thu){
        switch (thu) {
            case "2":{
                return 0;
            }
            case "3":{
                return 1;
            }
            case "4":{
                return 2;
            }
            case "5":{
                return 3;
            }
            case "6":{
                return 4;
            }
            case "7":{
                return 5;
            }
        }
        return 6;
    }
    // Hàm gán tầng cho tất cả học phần
    private void assignFloor() {
        for (int i = 0; i<mmh.size();i++) {
            for (int j =0;j<mmh.get(i).mhp.size();j++) {
                mmh.get(i).mhp.get(j).setDieukienloc(getFloor(mmh.get(i).mhp.get(j).getCtmh()));
            }
        }
    }

    // Hàm gán tổng số tiết học cho tất cả học phần
    private void assignLesson() {
        for (int i = 0; i<mmh.size();i++) {
            for (int j =0;j<mmh.get(i).mhp.size();j++) {
                mmh.get(i).mhp.get(j).setDieukienloc(getLesson(mmh.get(i).mhp.get(j).getCtmh()));
            }
        }
    }

    // Hàm trả về tổng số tiết học của học phần
    private int getLesson(ArrayList<CTMonhoc> ctmh) {
        int sum=0;

        for (int i=0; i<ctmh.size();i++) {
            sum+=ctmh.get(i).getSotiet();
        }

        return sum;
    }
    private int getNewSession(ArrayList<CTMonhoc> ctmh) {
        int dem=0;
        boolean kt;
        for (int i = 0;i<ctmh.size();i++) {
            kt=true;
            if(ctmh.get(i).getTietbd()<6) {
                for (int j=1; j<6; j++) {
                    if (week[exchangeThu(ctmh.get(i).getThu())][j]){
                        kt=false;
                    }
                }
            }
            else {
                for (int j=6; j<13; j++) {
                    if (week[exchangeThu(ctmh.get(i).getThu())][j]){
                        kt=false;
                    }
                }
            }
            if (kt)
                dem++;
        }
        return dem;
    }
    // Hàm trả về tầng của 1 học phần
    private int getFloor(ArrayList<CTMonhoc> ctmh) {
        int max=0;

        for (int i=0; i<ctmh.size();i++) {
            try {
                if (Integer.parseInt(ctmh.get(i).maphong.substring(ctmh.get(i).maphong.length()-3,ctmh.get(i).maphong.length()-2))>max) {
                    max = Integer.parseInt(ctmh.get(i).maphong.substring(ctmh.get(i).maphong.length()-3,ctmh.get(i).maphong.length()-2));
                }
            }
            catch (Exception e) {
            }
        }

        return max;
    }

    private void SortAll() {
        for (int i = 0; i<mmh.size();i++) {
            Sort(mmh.get(i).mhp);
        }
    }
    //hàm sắp xếp học phần môn học theo điều kiện
    private void Sort(ArrayList<HocPhan_CT> hpct) {
        for (int i = 0; i<hpct.size()-1; i++)
            for (int j=i+1; j<hpct.size(); j++)
                if(hpct.get(i).getDieukienloc()>hpct.get(j).getDieukienloc()) {
                    hpn=hpct.get(i);
                    hpct.add(i,hpct.get(j));
                    hpct.remove(i+1);
                    hpct.add(j,hpn);
                    hpct.remove(j+1);
                }
    }

    // Hàm xếp lịch thông thường
    public int VetCan(int monhientai) {
        if (monhientai>=mmh.size()){
            return mmh.size();
        }
        boolean kt;
        for (int i=0;i<mmh.get(monhientai).mhp.size();i++){
            kt=true;
            for (int j=0;j<ct.size();j++) {
                if (!checkTrung(mmh.get(monhientai).mhp.get(i).ctmh)){
                    kt = false;
                    break;
                }
            }
            if (kt) {
                ct.add(mmh.get(monhientai).mhp.get(i));
                fillWeek(mmh.get(monhientai).mhp.get(i).getCtmh());
                if(VetCan(monhientai+1)==mmh.size()) {
                    return mmh.size();
                }
                else{
                    ct.remove(ct.size()-1);
                    deleteWeek(mmh.get(monhientai).mhp.get(i).getCtmh());
                }
            }
        }
        if (monhientai>stt_montrung) {
            stt_montrung = monhientai;
        }
        return stt_montrung;
    }

    // Hàm xếp lịch theo ít buổi nhất
    public int VetCanNangCao(int monhientai) {
        if (monhientai>=mmh.size()){
            return mmh.size();
        }
        boolean kt;
        for (int i=0;i<mmh.get(monhientai).mhp.size();i++) {
            mmh.get(monhientai).mhp.get(i).setDieukienloc(getNewSession(mmh.get(monhientai).mhp.get(i).ctmh));
        }
        Sort(mmh.get(monhientai).mhp);
        for (int i=0;i<mmh.get(monhientai).mhp.size();i++){
            kt=true;
            for (int j=0;j<ct.size();j++) {
                if (!checkTrung(mmh.get(monhientai).mhp.get(i).ctmh)){
                    kt = false;
                    break;
                }
            }
            if (kt) {
                ct.add(mmh.get(monhientai).mhp.get(i));
                fillWeek(mmh.get(monhientai).mhp.get(i).getCtmh());
                if(VetCan(monhientai+1)==mmh.size()) {
                    return mmh.size();
                }
                else{
                    ct.remove(ct.size()-1);
                    deleteWeek(mmh.get(monhientai).mhp.get(i).getCtmh());
                }
            }
        }
        if (monhientai>stt_montrung) {
            stt_montrung = monhientai;
        }
        return stt_montrung;
    }

 /*   //Hàm kiểm tra học phần có chứa buổi học ưu tiên không
    private boolean checkBuoi(ArrayList<CTMonhoc> ctmh, String b) {
        if (b.equals("chieu")){
            for (int i=0; i<ctmh.size(); i++) {
                if(ctmh.get(i).getTietbd()>5)
                    return true;
            }
        }
        else {
            for (int i=0; i<ctmh.size(); i++) {
                if(ctmh.get(i).getTietbd()<6)
                    return true;
            }
        }

        return false;
    }

    //hàm xếp lịch theo buổi ưu tiên
    public int VetCanBuoi(int monhientai, String buoi) {
        if (monhientai>=mmh.size()){
            return mmh.size();
        }
        boolean kt;
        for (int i=0;i<mmh.get(monhientai).mhp.size();i++){
            if (checkBuoi(mmh.get(monhientai).mhp.get(i).ctmh,buoi)){
                kt=true;
                for (int j=0;j<ct.size();j++) {
                    if (!checkTrung(mmh.get(monhientai).mhp.get(i).ctmh)){
                        kt = false;
                        break;
                    }
                }
                if (kt) {
                    ct.add(mmh.get(monhientai).mhp.get(i));
                    fillWeek(mmh.get(monhientai).mhp.get(i).getCtmh());
                    if(VetCan(monhientai+1)==mmh.size()) {
                        return mmh.size();
                    }
                    else{
                        ct.remove(ct.size()-1);
                        deleteWeek(mmh.get(monhientai).mhp.get(i).getCtmh());
                    }
                }
            }
        }
        for (int i=0;i<mmh.get(monhientai).mhp.size();i++){
            if (!checkBuoi(mmh.get(monhientai).mhp.get(i).ctmh,buoi)){
                kt=true;
                for (int j=0;j<ct.size();j++) {
                    if (!checkTrung(mmh.get(monhientai).mhp.get(i).ctmh)){
                        kt = false;
                        break;
                    }
                }
                if (kt) {
                    ct.add(mmh.get(monhientai).mhp.get(i));
                    fillWeek(mmh.get(monhientai).mhp.get(i).getCtmh());
                    if(VetCan(monhientai+1)==mmh.size()) {
                        return mmh.size();
                    }
                    else{
                        ct.remove(ct.size()-1);
                        deleteWeek(mmh.get(monhientai).mhp.get(i).getCtmh());
                    }
                }
            }
        }
        if (monhientai>stt_montrung) {
            stt_montrung = monhientai;
        }
        return stt_montrung;
    }
*/
    //hàm chuyển môn không đăng ký được do bị trùng lịch ra mảng riêng
    public void research() {
        mmht.add(mmh.get(stt_montrung).mamh);
        mmh.remove(stt_montrung);
        stt_montrung = 0;
    }

    //hàm trả về các học phần kèm chi tiết được chọn
    public ArrayList<HocPhan_CT> outCalender() {
        return ct;
    }

    //hàm trả về mã các môn học bị trùng
    public ArrayList<Integer> outMonTrung() {
        return mmht;
    }

    @Override
    public String toString() {
        return "Monhoc = {"+mmh+"}";
    }
    //Các hàm truy xuất dữ liệu để fix bug
    public void printWeek() {
        for (int i=1;i<11;i++) {
            System.out.println();
            for (int j=0; j<7;j++) {
                if (week[j][i])
                    System.out.print("x  ");
                else
                    System.out.print("0  ");
            }
        }
    }
    public void printNhom() {
        for (int i =0;i<mmh.size();i++) {
            for (int j = 0; j<mmh.get(i).mhp.size();j++){
                System.out.print(mmh.get(i).mhp.get(j).manhom+" ");
            }
            System.out.println();
        }
    }
    public void printNhom1() {
        for (int i=0; i<ct.size();i++)
            System.out.print(ct.get(i).getManhom()+" ");
    }
    public void printDK() {
        for (int i =0;i<mmh.size();i++) {
            for (int j = 0; j<mmh.get(i).mhp.size();j++){
                System.out.print(mmh.get(i).mhp.get(j).dieukienloc+" ");
            }
            System.out.println();
        }
    }
}
