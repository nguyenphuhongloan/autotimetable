package classes.model;
public class HocPhan {
    private String mamh;
    private String tenmh;
    private String nhommh;
    private String makhoa;
    public HocPhan(String mamh, String tenmh, String nhommh, String makhoa)
    {
        this.mamh = mamh;
        this.tenmh = tenmh;
        this.nhommh = nhommh;
        this.makhoa = makhoa;
    }
    public void setMaMonHoc(String mamh)
    {
        this.mamh = mamh;
    }
    public void setTenMonHoc(String tenmh)
    {
        this.tenmh = tenmh;
    }
    public void setNhomMonHoc(String nhommh)
    {
        this.nhommh = nhommh;
    }
    public void setMaKhoa(String makhoa)
    {
        this.makhoa = makhoa;
    }
    public String getMaMonHoc()
    {
        return mamh;
    }
    public String getTenMonHoc()
    {
        return tenmh;
    }
    public String getNhomMonHoc()
    {
        return nhommh;
    }
    public String getMaKhoa()
    {
        return makhoa;
    }
    @Override
    public String toString()
    {
        return String.format("HocPhan[mamh='%s',tenmh='%s',nhommh='%s',makhoa='%s']",mamh,tenmh,nhommh,makhoa);
    }
}
