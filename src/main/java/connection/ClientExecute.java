package connection;
import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;

/**
 * @author Loan (^._.^)ﾉ
 */

public class ClientExecute {
    private String host = "localhost";
    private int port = 5000;
    private Socket socket;
    private boolean isConnected = false;
    private BufferedWriter out;
    private BufferedReader in;
    private PublicKey publicKey;
    private String strPublicKey;
    private SecretKey sessionKey;
    private String strSessionKeyEncrypted;
    public ClientExecute()
    {
        startConfig();
    }
    /**
     * Chạy client
     */
    private void startConfig() {
        try {
            socket = new Socket(host,port);
            System.out.println("Client connected");
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
//            ExecutorService executor = Executors.newCachedThreadPool();
            String data = in.readLine(); //read data public key from server
            strPublicKey = data;
            System.out.println("Public key: "+strPublicKey);
            publicKey = Encryption.getPublicKeyFromString(strPublicKey); //create public key
            sessionKey = Encryption.generateSessionKey(); //generate session key
            System.out.println("Session key: "+Encryption.getSesssionKeyToString(sessionKey));
            strSessionKeyEncrypted = Encryption.encryptSessionKey(sessionKey, publicKey); //encrypt session key
            System.out.println("Client send session key encrypted to server: "+ strSessionKeyEncrypted);
            out.write(strSessionKeyEncrypted+'\n'); //send session key encrypted to server
            out.flush();
            isConnected = true;
//            SendMessage send = new SendMessage(socket, out, sessionKey);
//            ReceiveMessage recv = new ReceiveMessage(socket, in, sessionKey);
//            executor.execute(send);
//            executor.execute(recv);
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException | NoSuchProviderException e) {
            System.out.println(e);
            isConnected = false;
        }
    }

    /**
     * Send a line message string to server 
     * @param message message want to send
     * @return true if message was sent successfully. Otherwise a false would be returned
     */
    public boolean sendMessage(String message)
    {
        if(isConnected)
        {
            try {
                String dataEncrypted = Encryption.encryptData(sessionKey, message);
                System.out.println("Input from client: " + dataEncrypted);
                out.write(dataEncrypted+'\n');
                out.flush();
                return true;
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException ex) {
                Logger.getLogger(ClientExecute.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }
    public String getMessage()
    {
        try{
            String cipherData = in.readLine();
            System.out.println("cipher = " + cipherData);
            String original = Encryption.decryptData(sessionKey, cipherData);
            System.out.println(original);
            return original;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public boolean isConnected()
    {
        return isConnected;
    }
    public void close()
    {
        if(isConnected)
        {
            try {
                out.close();
                in.close();
                socket.close();
            } catch(IOException e)
            {
                System.err.println(e);
            }
        }
    }
}
/* Nhận gửi tin của server */
//class SendMessage implements Runnable{
//    private Socket socket;
//    private BufferedWriter out;
//    private SecretKey sessionKey;
//    SendMessage(Socket socket, BufferedWriter out, SecretKey sessionKey){
//        this.socket = socket;
//        this.out = out;
//        this.sessionKey = sessionKey;
//    }
//    @Override
//    public void run() {
//        try {
//            while (true){
//                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//                String data = stdIn.readLine();
//                data = Encryption.encryptData(sessionKey, data);
//                System.out.println("Input from client: " + data);
//                out.write(data+'\n');
//                out.flush();
//            }
//        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e){
//
//        }
//    }
//}
//class ReceiveMessage implements Runnable{
//    private Socket socket;
//    private BufferedReader in;
//    private SecretKey sessionKey;
//    ReceiveMessage(Socket socket, BufferedReader in, SecretKey sessionKey){
//        this.socket = socket;
//        this.in = in;
//        this.sessionKey = sessionKey;
//    }
//
//    @Override
//    public void run() {
//        try{
//            while (true){
//                String data = in.readLine();
//                String original = Encryption.decryptData(sessionKey, data);
//                System.out.println("Receive: " + original);
//            }
//        }catch (IOException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e){
//
//        }
//
//    }
//}
