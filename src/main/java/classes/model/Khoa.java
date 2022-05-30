package classes.model;
public class Khoa {
    private String maKhoa;
    private String tenKhoa;
    public Khoa()
    {
        maKhoa = null;
        tenKhoa = null;
    }
    public Khoa(String maKhoa, String tenKhoa)
    {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
    }
    public String getMaKhoa()
    {
        return maKhoa;
    }
    public String getTenKhoa()
    {
        return tenKhoa;
    }
    public void setMaKhoa(String maKhoa)
    {
        this.maKhoa = maKhoa;
    }
    public void setTenKhoa(String tenKhoa)
    {
        this.tenKhoa = tenKhoa;
    }
    @Override
    public String toString()
    {
        return String.format("Khoa[makhoa='%s',tenkhoa='%s']",maKhoa,tenKhoa);
    }
}
