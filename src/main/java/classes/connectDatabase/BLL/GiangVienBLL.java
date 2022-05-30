package classes.connectDatabase.BLL;
import classes.connectDatabase.ConnectionDB;
import classes.connectDatabase.DLL.GiangVienDLL;
import classes.model.GiangVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class GiangVienBLL {
    private GiangVienDLL giangvienDLL;
    public GiangVienBLL()
    {
        giangvienDLL = new GiangVienDLL();
    }
    public GiangVienBLL(GiangVienDLL giangvienDLL)
    {
        this.giangvienDLL = giangvienDLL;
    }
    public ArrayList<GiangVien> getListGiangVien()
    {
        String sql = "SELECT * FROM giangvien";
        ResultSet rs = giangvienDLL.querySelect(sql);
        ArrayList<GiangVien> dsGiangvien = null;
        try{
            if(rs != null)
            {
                dsGiangvien = new ArrayList<>();
                while(rs.next())
                {
                    GiangVien gv = new GiangVien(rs.getString("magv"),rs.getString("tengv"));
                    dsGiangvien.add(gv);
                }
            }
        }
        catch(SQLException ex)
        {
            System.err.println(ex);
        }
        return dsGiangvien;   
    }
    public GiangVien getMaGiangVien(String magv)
    {
        String sql = String.format("SELECT * FROM giangvien WHERE magv = '%s'",magv);
        GiangVien gv = null;
        try{
            ResultSet rs = giangvienDLL.querySelect(sql);
            if(rs.next())
            {
                gv = new GiangVien(rs.getString("magv"),rs.getString("tengv"));
            }
        }catch(SQLException ex)
        {
            System.err.println(ex);
        }
        return gv;
    }
    public void setConnection(ConnectionDB conn)
    {
        if(conn instanceof GiangVienDLL)
        {
            this.giangvienDLL = (GiangVienDLL) conn;
        }
    }
}
