package connection;
import DAO.Xampp_Connection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Loan (^._.^)ﾉ
 */
public class ServerExecute {
    private static int port = 5000;
    private static int buffsize = 1024;
    private static ServerSocket serverSocket = null;
    public static Vector<ServerTCP> servers = new Vector<>();
    public static PublicKey publicKey;
    public static String strPublicKey;
    public static PrivateKey privateKey;
    public static String strPrivateKey;
    public static Xampp_Connection conn;
    public static void main(String[] args) {
        Xampp_Connection conn = new Xampp_Connection();
        conn.getConnectionDB();

        int i = 0;
        ExecutorService excutor = Executors.newCachedThreadPool();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port " + port);
            Encryption.generateKey();
            publicKey = Encryption.getPublicKey();
            strPublicKey = Encryption.getStringPublicKey();
            privateKey = Encryption.getPrivateKey();
            while (true){
                Socket socket = serverSocket.accept();
                i++;
                ServerTCP client = new ServerTCP(socket, Integer.toString(i));
                servers.add(client);
                excutor.execute(client);
            }
        } catch (SocketException e){
            System.out.println("Client exit");
        } catch (NoSuchAlgorithmException | IOException e){
            e.printStackTrace();
        }
    }
}
