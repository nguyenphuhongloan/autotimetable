package classes;
import java.util.ArrayList;
public class Timetable {
    public static final int itTiet = 1;
    public static final int itBuoi = 2;
    public static final int phonghocThap = 3;
    public static final int sapxepRandom = 4;
    private int thutuSapXep;
    private static int length;
    private ArrayList<MonDangKy> dsMonHocDangKy;
    public Timetable(int thutuSapXep, ArrayList<MonDangKy> dsMonHocDangKy)
    {
        this.thutuSapXep = thutuSapXep;
        this.dsMonHocDangKy = dsMonHocDangKy;
        length = dsMonHocDangKy.size();
    }
    public void setThutuSapXep(int thutuSapxep)
    {
        this.thutuSapXep = thutuSapxep;
    }
    public void setDSMonHoc(ArrayList<MonDangKy> dsMonHocDangKy)
    {
        this.dsMonHocDangKy = dsMonHocDangKy;
    }
    public int getThutuSapXep()
    {
        return thutuSapXep;
    }
    public ArrayList<MonDangKy> getDSMonHoc()
    {
        return dsMonHocDangKy;
    }
    public Object getValueAt(int index)
    {
        return dsMonHocDangKy.get(index);
    }
    public void setValueAt(MonDangKy value, int index)
    {
        dsMonHocDangKy.set(index, value);
    }
    public int size()
    {
        return length;
    }
}
