package classes.connectDatabase.BLL;
import classes.connectDatabase.ConnectionDB;
import classes.connectDatabase.DLL.KhoaDLL;
import classes.model.Khoa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class KhoaBLL {
    private KhoaDLL khoaDLL;
    public KhoaBLL()
    {
        khoaDLL = new KhoaDLL();
    }
    public KhoaBLL(KhoaDLL khoaDLL)
    {
        this.khoaDLL = khoaDLL;
    }
    public ArrayList<Khoa> getListKhoa()
    {
        String sql = "SELECT * FROM khoa";
        ResultSet rs = khoaDLL.querySelect(sql);
        ArrayList<Khoa> dsKhoa = null;
        try{
            if(rs != null)
            {
                dsKhoa = new ArrayList<>();
                while(rs.next())
                {
                    Khoa khoa = new Khoa(rs.getString("makhoa"),rs.getString("tenkhoa"));
                    dsKhoa.add(khoa);
                }
            }
        }
        catch(SQLException ex)
        {
            System.err.println(ex.getNextException());
        }
        return dsKhoa;   
    }

    public void setConnection(ConnectionDB conn)
    {
        if(conn instanceof KhoaDLL)
        {
            this.khoaDLL = (KhoaDLL) conn;
        }
    }
}
