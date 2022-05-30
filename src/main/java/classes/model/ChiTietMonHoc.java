package classes.model;
public class ChiTietMonHoc {
    private String mamh;
    private String nhommh;
    private String thu;
    private int tietbd;
    private int sotiet;
    private String soPhong;
    private String magv;
    private String thuchanh;
    public ChiTietMonHoc()
    {
        mamh = null;
        nhommh = null;
        thu = null;
        tietbd = -1;
        sotiet = -1;
        soPhong = null;
        magv = null;
        thuchanh = null;
    }
    public ChiTietMonHoc(String mamh, String nhommh, String thu, int tietbd, int sotiet, String soPhong, String magv)
    {
        this.mamh = mamh;
        this.nhommh = nhommh;
        this.thu = thu;
        this.tietbd = tietbd;
        this.sotiet = sotiet;
        this.soPhong = soPhong;
        this.magv = magv;
    }
    public ChiTietMonHoc(String mamh, String nhommh, String thu, int tietbd, int sotiet, String soPhong, String magv,String thuchanh)
    {
        this(mamh,nhommh,thu,tietbd,sotiet,soPhong,magv);
        this.thuchanh = thuchanh;
    }
    public String getMaMonHoc()
    {
        return mamh;
    }
    public String getNhomMonHoc()
    {
        return nhommh;
    }
    public int getTietBatDau()
    {
        return tietbd;
    }
    public int getSotiet()
    {
        return sotiet;
    }
    public String getThu()
    {
        return thu;
    }
    public String getSoPhong()
    {
        return soPhong;
    }
    public String getThucHanh()
    {
        return thuchanh;
    }
    public void setSoPhong(String soPhong)
    {
        this.soPhong = soPhong;
    }
    public void setMaGiangVien(String magv)
    {
        this.magv = magv;
    }
    public void setMaMH(String mamh)
    {
        this.mamh = mamh;
    }
    public void setThu(String thu)
    {
        this.thu = thu;
    }
    public void setTietBatDau(int tietbd)
    {
        this.tietbd = tietbd;
    }
    public void setNhomMonHoc(String nhommh)
    {
        this.nhommh = nhommh;
    }
    public void setThucHanh(String thuchanh)
    {
        this.thuchanh = thuchanh;
    }
    @Override
    public String toString()
    {
        return "ChiTietMonHoc[mamh = '"+mamh+"', nhommh = '"+nhommh+"'"+","
                + " thu = '"+thu+"'"+", tietbd = '"+tietbd+"', sotiet = '"+sotiet+"',"
                + " soPhong = '"+soPhong+"'"+", magv = '"+magv+"', thuchanh = "+thuchanh+"]";
    }
}
