package classes;
public class MonDangKy {
    private int doUuTien;
    private String maMH;
    private String tenMH;
    public MonDangKy(int doUuTien, String maMH, String tenMH)
    {
        this.doUuTien = doUuTien;
        this.maMH = maMH;
        this.tenMH = tenMH;
    }
    public void setDoUuTien(int doUuTien)
    {
        this.doUuTien = doUuTien;
    }
    public void setMaMH(String maMH)
    {
        this.maMH = maMH;
    }
    public void setTenMH(String tenMH)
    {
        this.tenMH = tenMH;
    }
    public int getDoUuTien()
    {
        return doUuTien;
    }
    public String getMaMH()
    {
        return maMH;
    }
    public String getTenMH()
    {
        return tenMH;
    }
}
