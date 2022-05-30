package classes.connectDatabase;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
public abstract class ConnectionDB {
    protected String password;
    protected String username;
    protected String Name_DB;
    protected String port;
    protected String host;
    protected java.sql.Connection conn = null;
    protected boolean isConnected = false;
    public ConnectionDB()
    {
        password = "";
        username = "root";
        Name_DB = "tkb";
        port = "3306";
        host = "localhost";
        setupConnection();
    }
    public ConnectionDB(String Name_DB,String username,String password,String host,String port)
    {
        this.password = password;
        this.username = username;
        this.Name_DB = Name_DB;
        this.port = port;
        this.host = host;
        setupConnection();
    }
    public ConnectionDB(String Name_DB, String username,String password)
    {
        this.password = password;
        this.username = username;
        this.Name_DB = Name_DB;
        setupConnection();
    }
    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }
    public String getNameDatabase()
    {
        return Name_DB;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setNameDatabase(String Name_DB)
    {
        this.Name_DB = Name_DB;
    }
    public java.sql.Connection getConnection()
    {
        return conn;
    }
    public void openConnection()
    {
        setupConnection();
    }
    public void closeConnection() throws SQLException
    {
        conn.close();
    } 
    public boolean checkedConnection()
    {
        return isConnected;
    }
    private void setupConnection()
    {
        try{
            String URL ="jdbc:mysql://"+host+":"+port+"/"+Name_DB;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL,username,password);
            isConnected = true;
        }
        catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null,"Không thể kết nối đến database " + Name_DB);
            conn = null;
            isConnected = false;
        }
    }
    public abstract ResultSet querySelect(String sql);
    public abstract ResultSet queryUpdate(String sql);
    public abstract ResultSet queryInsert(String sql);
    public abstract ResultSet queryDelete(String sql);
    public abstract String getTableName();
}
