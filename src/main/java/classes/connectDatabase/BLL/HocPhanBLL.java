package classes.connectDatabase.BLL;
import classes.connectDatabase.ConnectionDB;
import classes.connectDatabase.DLL.HocPhanDLL;
import classes.model.HocPhan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class HocPhanBLL {
        private HocPhanDLL hocphanDLL;
    public HocPhanBLL()
    {
        hocphanDLL = new HocPhanDLL();
    }
    public HocPhanBLL(HocPhanDLL hocphanDLL)
    {
        this.hocphanDLL = hocphanDLL;
    }
    public ArrayList<HocPhan> getListHocPhan()
    {
        String sql = "SELECT * FROM hocphan";
        ResultSet rs = hocphanDLL.querySelect(sql);
        ArrayList<HocPhan> dsHocphan = null;
        try{
            if(rs != null)
            {
                dsHocphan = new ArrayList<>();
                while(rs.next())
                {
                    HocPhan hp = new HocPhan(rs.getString("mamh"),rs.getString("tenmh"),
                                             rs.getString("nhommh"),rs.getString("makhoa"));
                    dsHocphan.add(hp);
                }
            }
        }
        catch(SQLException ex)
        {
            System.err.println(ex.getNextException());
        }
        return dsHocphan;   
    }
    public HocPhan getMaHocPhan(String mamh)
    {
        String sql = String.format("SELECT * FROM hocphan WHERE mamh = '%s'",mamh);
        HocPhan hp = null;
        try{
            ResultSet rs = hocphanDLL.querySelect(sql);
            if(rs.next())
            {
                hp = new HocPhan(rs.getString("mamh"),rs.getString("tenmh"),
                                 rs.getString("nhommh"),rs.getString("makhoa"));
            }
        }catch(SQLException ex)
        {
            System.err.println(ex);
        }
        return hp;
    }
    public void setConnection(ConnectionDB conn)
    {
        if(conn instanceof HocPhanDLL)
        {
            this.hocphanDLL = (HocPhanDLL) conn;
        }
    }
}
