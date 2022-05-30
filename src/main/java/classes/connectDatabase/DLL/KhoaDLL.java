package classes.connectDatabase.DLL;
import classes.connectDatabase.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class KhoaDLL extends ConnectionDB{
    public KhoaDLL(){
        super();
    }
    public KhoaDLL(String Name_DB,String username,String password,String host,String port)
    {
        super(Name_DB,username,password,host,port);
    }
    public KhoaDLL(String Name_DB, String username,String password)
    {
        super(Name_DB,username,password);
    }
    @Override
    public ResultSet querySelect(String sql) {
        if(checkedConnection())
        {
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                return rs;
            } catch (SQLException ex) {
                System.err.println(ex.getSQLState()+": "+ex.getMessage());
                return null;
            }
        }
        else{
            return null;
        }
    }

    @Override
    public ResultSet queryUpdate(String sql) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet queryInsert(String sql) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet queryDelete(String sql) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTableName() {
        return "Khoa";
    }
}
