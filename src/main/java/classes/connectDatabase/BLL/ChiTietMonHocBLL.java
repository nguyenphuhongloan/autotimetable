package classes.connectDatabase.BLL;
import classes.connectDatabase.ConnectionDB;
import classes.connectDatabase.DLL.ChiTietMonHocDLL;
import classes.model.ChiTietMonHoc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class ChiTietMonHocBLL {
    private ChiTietMonHocDLL chitietmhDLL;
    public ChiTietMonHocBLL()
    {
        chitietmhDLL = new ChiTietMonHocDLL();
    }
    public ChiTietMonHocBLL(ChiTietMonHocDLL chitietmhDLL)
    {
        this.chitietmhDLL = chitietmhDLL;
    }
    public ArrayList<ChiTietMonHoc> getListChiTietMonHoc(String mamh)
    {
        String sql = String.format("SELECT * FROM chitietmonhoc where mamh = '%s'",mamh);
        ResultSet rs = chitietmhDLL.querySelect(sql);
        ArrayList<ChiTietMonHoc> dsGiangvien = null;
        try{
            if(rs != null)
            {
                dsGiangvien = new ArrayList<>();
                while(rs.next())
                {
                    ChiTietMonHoc ctmh = new ChiTietMonHoc(rs.getString("mamh"),rs.getString("nhommh"),
                                                           rs.getString("thu"),rs.getInt("tietbd"),
                                                           rs.getInt("sotiet"),rs.getString("sophong"),
                                                           rs.getString("magv"),rs.getString("thuchanh"));
                    dsGiangvien.add(ctmh);
                }
            }
        }
        catch(SQLException ex)
        {
            System.err.println(ex);
        }
        return dsGiangvien;
    }
    public ArrayList<ChiTietMonHoc> getListChiTietMonHoc()
    {
        String sql = "SELECT * FROM chitietmonhoc";
        ResultSet rs = chitietmhDLL.querySelect(sql);
        ArrayList<ChiTietMonHoc> dsGiangvien = null;
        try{
            if(rs != null)
            {
                dsGiangvien = new ArrayList<>();
                while(rs.next())
                {
                    ChiTietMonHoc ctmh = new ChiTietMonHoc(rs.getString("mamh"),rs.getString("nhommh"),
                                                           rs.getString("thu"),rs.getInt("tietbd"),
                                                           rs.getInt("sotiet"),rs.getString("sophong"),
                                                           rs.getString("magv"),rs.getString("thuchanh"));
                    dsGiangvien.add(ctmh);
                }
            }
        }
        catch(SQLException ex)
        {
            System.err.println(ex);
        }
        return dsGiangvien;   
    }
    public void setConnection(ConnectionDB conn)
    {
        if(conn instanceof ChiTietMonHocDLL)
        {
            this.chitietmhDLL = (ChiTietMonHocDLL) conn;
        }
    }
}
