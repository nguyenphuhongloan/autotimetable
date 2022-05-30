/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admins
 */
public class Xampp_Connection {
    //jdbc:mysql://localhost:3306/tkb1
    private String user = "root";
    private String password ="";
    private String url = "jdbc:mysql://localhost:3306/tkb2";
    Connection conn;
    public Connection getConnectionDB () {
        
        try {         
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Kết nối mysql thành công!");
            System.out.println(conn.getCatalog());
        } catch (SQLException e) {
            Logger.getLogger(Xampp_Connection.class.getName()).log(Level.SEVERE,null,e);
        }
        return conn;
    }
    public void Cclose() {
        try {
            conn.close();
            System.out.println("Đóng kết nối dữ liệu thành công!");
        } catch (SQLException e) {
            Logger.getLogger(Xampp_Connection.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    
    // DAO Chi tiết môn học
    
    public ArrayList<CTMonhoc> uploadAllCTMH () {
        ArrayList<CTMonhoc> ctmh = new ArrayList<>();
        CTMonhoc ct;
        try {
            String str = "SELECT * FROM chitietmonhoc";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(str);
            
            while (rs.next()) {
                ct = new CTMonhoc(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getString(6),rs.getInt(7),rs.getString(8));
                ctmh.add(ct);
            }
        } catch (Exception e) {
            System.out.print("Tải dữ liệu chi tiết môn học thất bại");
            e.printStackTrace();
        }
        return ctmh;
    }
    
    public ArrayList<CTMonhoc> uploadCTMH (int mamh, int nhommh) {
        ArrayList<CTMonhoc> ctmh = new ArrayList<>();
        CTMonhoc ct;
        try {
            String str = "SELECT * FROM chitietmonhoc where mamh ="+mamh+" and nhommh = '"+nhommh+"'";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(str);
            
            while (rs.next()) {
                ct = new CTMonhoc(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getString(6),rs.getInt(7),rs.getString(8));
                ctmh.add(ct);
            }
        } catch (Exception e) {
            System.out.print("Tải dữ liệu chi tiết môn học thất bại");
            e.printStackTrace();
        }
        return ctmh;
    }
    
    public void addAllCTMH(ArrayList<CTMonhoc> ctmh) {
        for (int i=0;i<ctmh.size();i++) {
            addCTMH(ctmh.get(i).getMamh(),ctmh.get(i).getNhommh(),ctmh.get(i).getThu(),ctmh.get(i).getTietbd(),ctmh.get(i).getSotiet(),ctmh.get(i).getMaphong(),ctmh.get(i).getMagv(),ctmh.get(i).getThuchanh());
            System.out.println("CTMH-"+(i+1));
        }
    }
    
    public void addCTMH(int mamh, int manhom, String thu, int tietbd, int sotiet, String maphong, int magv, String th) {
        String query = "insert into chitietmonhoc values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,mamh);
            ps.setInt(2,manhom);
            ps.setString(3,thu);
            ps.setInt(4,tietbd);
            ps.setInt(5,sotiet);
            ps.setString(6,maphong);
            ps.setInt(7,magv);
            ps.setString(8,th);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Thêm chi tiết môn học thất bại!");
            }
            else System.out.println("Thêm chi tiết môn học thành công!");
        } catch (SQLException ex) {
            Logger.getLogger(Xampp_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateCTMH(int mamh, int manhom, String thu, int tietbd, int sotiet, String maphong, int magv, String th) {     
        String query = "update chitietmonhoc set thu = ?, tietbd = ?, sotiet = ?, sophong = ?, magv = ? where mamh = "+mamh+" and nhommh = "+manhom+" and thuchanh = '"+th+"'";
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1,thu);
            ps.setInt(2,tietbd);
            ps.setInt(3,sotiet);
            ps.setString(4,maphong);
            ps.setInt(5,magv);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Cập nhật chi tiết môn học thất bại!");
            }
            else System.out.println("Cập nhật chi tiết môn học thành công!");
        } catch (SQLException ex) {
            Logger.getLogger(Xampp_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void deleteCTMH(int mamh) {
        String query = "delete from chitietmonhoc where mamh = "+mamh;
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Xóa chi tiết môn học thất bại!");
            }
            else System.out.println("Xóa chi tiết môn học thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void deleteCTMH(int mamh, int manhom) {
        String query = "delete from chitietmonhoc where mamh = "+mamh+" and nhommh = "+manhom;
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Xóa chi tiết môn học thất bại!");
            }
            else System.out.println("Xóa chi tiết môn học thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteCTMH(int mamh, int manhom, String th) {
        String query = "delete from chitietmonhoc where mamh = "+mamh+" and nhommh = "+manhom+" and thuchanh = '"+th+"'";
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Xóa chi tiết môn học thất bại!");
            }
            else System.out.println("Xóa chi tiết môn học thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteAllCTMH() {
        String query = "delete from chitietmonhoc";
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Xóa chi tiết môn học thất bại!");
            }
            else System.out.println("Xóa chi tiết môn học thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Dao giảng viên
    
    public ArrayList<GiangVien> uploadAllGV () {
        ArrayList<GiangVien> dsgv = new ArrayList<GiangVien>();
        GiangVien gv;
        try {
            String str = "SELECT * FROM giangvien";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(str);
            
            while (rs.next()) {
                gv = new GiangVien(rs.getInt(1),rs.getString(2));
                dsgv.add(gv);
            }
        } catch (Exception e) {
            System.out.print("Tải dữ liệu giảng viên thất bại");
            e.printStackTrace();
        }
        return dsgv;
    }
    public void addAllGV(ArrayList<GiangVien> dsgv) {
        for (int i=0;i<dsgv.size();i++) {
            addGV(dsgv.get(i).getMagv(),dsgv.get(i).getTengv());
            System.out.println("GV-"+(i+1));
        }
    }
    public void addGV(int magv, String tengv) {
        String query = "insert into giangvien values (?,?)";
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,magv);
            ps.setString(2,tengv);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Thêm giảng viên thất bại!");
            }
            else System.out.println("Thêm giảng viên thành công!");
        } catch (SQLException ex) {
            Logger.getLogger(Xampp_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteGV(int magv) {
        String query = "delete from giangvien where magv = "+magv;
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Xóa giảng viên thất bại!");
            }
            else System.out.println("Xóa giảng viên thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteGV(String tengv) {
        String query = "delete from giangvien where tengv = '"+tengv+"'";
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Xóa giảng viên thất bại!");
            }
            else System.out.println("Xóa giảng viên thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteAllGV() {
        String query = "delete from giangvien";
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Xóa giảng viên thất bại!");
            }
            else System.out.println("Xóa giảng viên thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Dao học phần
    
    public ArrayList<HocPhan> uploadAllHP () {
        ArrayList<HocPhan> dshp = new ArrayList<HocPhan>();
        HocPhan hp;
        try {
            String str = "SELECT * FROM hocphan";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(str);
            
            while (rs.next()) {
                hp = new HocPhan(rs.getInt(1),rs.getString(2),Integer.parseInt(rs.getString(3)),rs.getString(4));
                dshp.add(hp);
            }
        } catch (Exception e) {
            System.out.print("Tải dữ liệu học phần thất bại");
            e.printStackTrace();
        }
        return dshp;
    }
    public ArrayList<HocPhan> uploadHP (int mamh) {
        ArrayList<HocPhan> dshp = new ArrayList<HocPhan>();
        HocPhan hp;
        try {
            String str = "SELECT * FROM hocphan where mamh = "+mamh;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(str);
            
            while (rs.next()) {
                hp = new HocPhan(rs.getInt(1),rs.getString(2),Integer.parseInt(rs.getString(3)),rs.getString(4));
                dshp.add(hp);
            }
        } catch (Exception e) {
            System.out.print("Tải dữ liệu học phần thất bại");
            e.printStackTrace();
        }
        return dshp;
    }
    
    public void addAllHP(ArrayList<HocPhan> hp) {
        for (int i=0;i<hp.size();i++) {
            addHP(hp.get(i).getMamh(),hp.get(i).getTenmh(),hp.get(i).getNhommh(),hp.get(i).getMakhoa());
            System.out.println("HP-"+(i+1));
        }
    }
    
    public void addHP(int mamh, String tenmh, int nhommh, String makhoa) {
        String query = "insert into hocphan values (?,?,?,?)";
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,mamh);
            ps.setString(2,tenmh);
            ps.setString(3,String.valueOf(nhommh));
            ps.setString(4,makhoa);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Thêm học phần thất bại!");
            }
            else System.out.println("Thêm học phần thành công!");
        } catch (SQLException ex) {
            Logger.getLogger(Xampp_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteMH(int mamh) {
        String query = "delete from hocphan where mamh = "+mamh;
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Xóa môn học thất bại!");
            }
            else {
                System.out.println("Xóa môn học thành công!");
                deleteCTMH(mamh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteHP(int mamh, String nhommh) {
        String query = "delete from hocphan where mamh = "+mamh+" and nhommh = '"+nhommh+"'";
        try {
            Statement stm = conn.createStatement();
            int n=stm.executeUpdate(query);   
            if(n==0){
                System.out.println("Xóa học phần thất bại!");
            }
            else {
                System.out.println("Xóa học phần thành công!");
                deleteCTMH(mamh, Integer.parseInt(nhommh));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteAllHP() {
        String query = "delete from hocphan";
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Xóa học phần thất bại!");
            }
            else System.out.println("Xóa học phần thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Dao khoa
    
    public ArrayList<Khoa> uploadAllK () {
        ArrayList<Khoa> dsk = new ArrayList<Khoa>();
        Khoa khoa;
        try {
            String str = "SELECT * FROM khoa";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(str);
            
            while (rs.next()) {
                khoa = new Khoa(rs.getString(1),rs.getString(2));
                dsk.add(khoa);
            }
        } catch (Exception e) {
            System.out.print("Tải dữ liệu khoa thất bại");
            e.printStackTrace();
        }
        return dsk;
    }
    public void addAllKhoa(ArrayList<Khoa> dsk) {
        for (int i=0;i<dsk.size();i++) {
            addK(dsk.get(i).getMakhoa(),dsk.get(i).getTenkhoa());
            System.out.println("Khoa-"+(i+1));
        }
    }
    public void addK(String makhoa, String tenkhoa) {
        String query = "insert into khoa values (?,?)";
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1,makhoa);
            ps.setString(2,tenkhoa);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Thêm khoa thất bại!");
            }
            else System.out.println("Thêm khoa thành công!");
        } catch (SQLException ex) {
            Logger.getLogger(Xampp_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteK(String makhoa) {
        String query = "delete from khoa where makhoa = "+makhoa;
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Xóa khoa thất bại!");
            }
            else System.out.println("Xóa khoa thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteAllK() {
        String query = "delete from khoa";
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            int n=ps.executeUpdate();   
            if(n==0){
                System.out.println("Xóa khoa thất bại!");
            }
            else System.out.println("Xóa khoa thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HocPhan findHocPhan(int mamh) {

        HocPhan kt = null;
        try {
            String str = "SELECT mamh,tenmh FROM hocphan where mamh = "+mamh+" group by mamh";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(str);

            while (rs.next()) {
                kt=new HocPhan(rs.getInt(1),rs.getString(2));
            }
            System.out.println(kt);
            return kt;
        } catch (Exception e) {
            System.out.print("liên kết csdl thất bại");
            e.printStackTrace();
        }
        return null;
    }

    //hàm kiểm tra môn có mở không
    public ArrayList<MonHoc> checkMonhoc(int mamh) {
        ArrayList<MonHoc> a = new ArrayList<>();
        MonHoc kt;
        try {
            String str = "SELECT mamh,tenmh FROM hocphan where mamh like '%"+mamh+"%' group by mamh";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(str);

            while (rs.next()) {
                kt=new MonHoc(rs.getInt(1),rs.getString(2));
                a.add(kt);
            }
        } catch (Exception e) {
            System.out.print("liên kết csdl thất bại");
            e.printStackTrace();
        }
        return a;
    }
    public ArrayList<MonHoc> checkMonhoc(String tenmh) {
        ArrayList<MonHoc> a = new ArrayList<>();
        MonHoc kt;
        try {
            String str = "SELECT mamh,tenmh FROM hocphan where tenmh like '%"+tenmh+"%' group by mamh";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(str);

            while (rs.next()) {
                kt=new MonHoc(rs.getInt(1),rs.getString(2));
                a.add(kt);
            }
        } catch (Exception e) {
            System.out.print("liên kết csdl thất bại");
            e.printStackTrace();
        }
        return a;
    }
}
    