package classes.model;
public class GiangVien {
    private String maGV;
    private String tenGV;
    public GiangVien()
    {
        maGV = null;
        tenGV = null;
    }
    public GiangVien(String maGV, String tenGV)
    {
        this.maGV = maGV;
        this.tenGV = tenGV;
    }
    public void setMaGV(String maGV)
    {
        this.maGV = maGV;
    }
    public void setTenGV(String tenGV)
    {
        this.tenGV = tenGV;
    }
    public String getMaGV()
    {
        return maGV;
    }
    public String getTenGV()
    {
        return tenGV;
    }
    @Override
    public String toString()
    {
        return String.format("GiangVien[magv='%s',tengv='%s']",maGV,tenGV);
    }
}
