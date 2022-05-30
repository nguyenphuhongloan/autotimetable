package classes;
import classes.connectDatabase.BLL.ChiTietMonHocBLL;
import classes.model.ChiTietMonHoc;
import java.util.ArrayList;
public class NhiemSacThe {
    private int[] gens;
    private double fitness = 0.0;
    private double soLuongTrungTiet = 0;
    private boolean isChanged = true;
    public NhiemSacThe(int[] gens)
    {
        this.gens = gens;
        isChanged = false;
    }
    public NhiemSacThe(int length)
    {
        this.gens = new int[length];
    }
    public NhiemSacThe[] laighep(NhiemSacThe nst2)
    {
        NhiemSacThe[] con = new NhiemSacThe[2];
        if(getGens().length != GiaiThuatDiTruyen.getNSTLength() || nst2.getGens().length != GiaiThuatDiTruyen.getNSTLength()) {
            return null;
        }
        con[0] = new NhiemSacThe(GiaiThuatDiTruyen.getNSTLength());
        con[1] = new NhiemSacThe(GiaiThuatDiTruyen.getNSTLength());
        java.util.Random rd = new java.util.Random();
        for(int i=0; i<GiaiThuatDiTruyen.getNSTLength(); i++)
        {
            boolean randbool = rd.nextBoolean();
            if(randbool)
            {
                con[0].setValueAt(this.getValueAt(i),i);
                con[1].setValueAt(nst2.getValueAt(i),i);
            }
            else{
                con[0].setValueAt(nst2.getValueAt(i),i);
                con[1].setValueAt(this.getValueAt(i),i);
            }
        }
        return con;
    }
    public void sinhNgauNhien()
    {
        ArrayList<MonDangKy> dsMonDangky = new ArrayList<>(GiaiThuatDiTruyen.getDSMonDangky());
        java.util.Random rd = new java.util.Random();
        for(int i=0;i<gens.length;i++){
            int randnum = rd.nextInt(dsMonDangky.size());
            gens[i] = dsMonDangky.get(randnum).getDoUuTien();
            dsMonDangky.remove(randnum);
        }
        isChanged = false;
    }
    public void dotBien()
    {
        
    }
    public int getValueAt(int index)
    {
        return gens[index];
    }
    public void setValueAt(int value, int index)
    {
        this.gens[index] = value;
    }
    public void doThichNghi()
    {
        ChiTietMonHocBLL ctbll = new ChiTietMonHocBLL();
        for(int i = 0;i < gens.length-1; i++)
        {
            ArrayList<ChiTietMonHoc> giohoc = ctbll.getListChiTietMonHoc(GiaiThuatDiTruyen.getDSMonDangky().get(gens[i]-1).getMaMH());
            for(int j=i; j < gens.length;j++)
            {
                
            }
        }
    }
    public int[] getGens()
    {
        return gens;
    }
    @Override
    public String toString()
    {
        return "NhiemSacThe[gens = "+java.util.Arrays.toString(gens)+",fitness = "+fitness+"]";
    }
    public static void main(String[] args)
    {
        ArrayList<MonDangKy> dsMonhoc = new ArrayList<>();
        dsMonhoc.add(new MonDangKy(1,"841114","Công nghệ phần mềm nâng cao"));
        dsMonhoc.add(new MonDangKy(2,"862308","Giáo dục Quốc phòng An ninh 3"));
        dsMonhoc.add(new MonDangKy(3,"841310","Lý thuyêt đồ thị"));
        dsMonhoc.add(new MonDangKy(4,"841048","Phân tích thiết kế HTTT"));
        Timetable table = new Timetable(Timetable.sapxepRandom,dsMonhoc);
        GiaiThuatDiTruyen.regTimetable(table);
        NhiemSacThe nst1 = new NhiemSacThe(GiaiThuatDiTruyen.getNSTLength());
        nst1.sinhNgauNhien();
        NhiemSacThe nst2 = new NhiemSacThe(GiaiThuatDiTruyen.getNSTLength());
        nst2.sinhNgauNhien();
        NhiemSacThe[] con = nst1.laighep(nst2);
        System.out.println(java.util.Arrays.toString(con));
    }
}