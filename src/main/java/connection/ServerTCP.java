package connection;
import DAO.Xampp_Connection;
import Model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @author Loan (^._.^)ﾉ
 */
public class ServerTCP implements Runnable {
    private static Xampp_Connection conn;
    private Socket socket = null;
    private String myName = "";
    private BufferedReader in;
    private BufferedWriter out;
    private SecretKey sessionKey;
    private String strSessionKey;
    private String original;
    public ServerTCP(Socket socket, String name) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        this.myName = name;
        ServerTCP.conn = new Xampp_Connection();
        conn.getConnectionDB();
    }
    @Override
    public void run() {
        System.out.println("Client "+socket.toString()+"accepted");
        String input = "";
        try {
            for(ServerTCP server: ServerExecute.servers){
                if(myName.equals(server.myName)){
                    String strPublicKey = Encryption.getStringPublicKey();
                    System.out.println("Public key: "+strPublicKey);
                    server.out.write(strPublicKey+'\n');
                    server.out.flush();
                    System.out.println("Server write: " + strPublicKey + " to " + server.myName);
                    input = in.readLine();
                    System.out.println("Server received session key: " + input + " from " + socket.toString() + " # Client " + myName);
                    strSessionKey = Encryption.decryptSessionKey(input);
                    sessionKey = Encryption.getSecretKeyFromString(strSessionKey);
                    System.out.println("Session key: "+strSessionKey);
                    break;
                }
            }

            while (true) {
                input = in.readLine();
                input = Encryption.decryptData(sessionKey, input);
                System.out.println("Server received: " + input + " from " + socket.toString() + " # Client " + myName);
                for(ServerTCP server: ServerExecute.servers){
                    if(myName.equals(server.myName)){
                        String response = handle(input); //response upper string input
                        System.out.println("response = "+response);
                        response = Encryption.encryptData(sessionKey, response); //encrypt data before send
                        server.out.write(response+'\n');
                        server.out.flush();
                        System.out.println("Server write: " + response + " to " + server.myName);
                        break;
                    }
                }
            }
        } catch (IOException | NullPointerException e){
            System.out.println("Client "+myName+" exit");
            ServerExecute.servers.remove(this);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }
    private static String handle(String input) {
       int index =  input.indexOf(';');
       String command = input.substring(0, index);
        System.out.println(command);
        String data = input.substring(index+1 );
        System.out.println(data);
        JSONObject obj = new JSONObject();
        boolean status = false;
        switch (command){

            case "REGISTERCOURSE":{
                try{
                    int mamh = Integer.parseInt(data);
                    HocPhan hp = conn.findHocPhan(mamh);
                    System.out.println(hp);
                    if(hp != null){
                        status = true;
                        JSONObject hocphan = new JSONObject();
                        hocphan.put("mamh", hp.getMamh());
                        hocphan.put("tenmh", hp.getTenmh());
                        obj.put("hocphan", hocphan);
                    } else obj.put("message", "Không tìm thấy mã môn tương ứng");
                    obj.put("status", status);
                    String output = obj.toString();
                    System.out.println(output);
                    return output;
                } catch (NumberFormatException e){

                }
                obj.put("status", status);
                obj.put("message", "Không tìm thấy mã môn tương ứng");
                return obj.toString();

            }
            case "OPTION1": {
                int[] mamh = handleOption(data);
                Calender c = new Calender(mamh);
                c.XepLich(1);
                ArrayList<HocPhan_CT> hp = new ArrayList<>();
                hp.addAll(c.outCalender());
                return outputStringCTMH(hp);
            }
            case "OPTION2": {
                int[] mamh = handleOption(data);
                Calender c = new Calender(mamh);
                c.XepLich(2);
                ArrayList<HocPhan_CT> hp = new ArrayList<>();
                hp.addAll(c.outCalender());
                return outputStringCTMH(hp);
            }
            case "OPTION3": {
                int[] mamh = handleOption(data);
                Calender c = new Calender(mamh);
                c.XepLich(3);
                ArrayList<HocPhan_CT> hp = new ArrayList<>();
                hp.addAll(c.outCalender());
                return outputStringCTMH(hp);
            }
            case "OPTION4": {
                int[] mamh = handleOption(data);
                Calender c = new Calender(mamh);
                c.XepLich(4);
                ArrayList<HocPhan_CT> hp = new ArrayList<>();
                hp.addAll(c.outCalender());
                return outputStringCTMH(hp);
            }
        }

        return null;
    }
    private static int[] handleOption(String data){
        String[] strMaMH = data.split("/");
        int[] mamh = new int[strMaMH.length];
        for (int i = 0; i < strMaMH.length; i++) {
            mamh[i] = Integer.parseInt(strMaMH[i]);
        }
        return mamh;

    }
    private static String outputStringCTMH(ArrayList<HocPhan_CT> hp){
        JSONArray arrCTMH = new JSONArray();
        for (int i = 0; i < hp.size(); i++) {
            //System.out.println(hp.get(i));
            HocPhan_CT hocPhan = hp.get(i);
            for (int j=0;j<hp.get(i).getCtmh().size();j++){
                CTMonhoc ct = hp.get(i).getCtmh().get(j);
                System.out.println(ct);
                JSONObject objCTMH = new JSONObject();
                objCTMH.put("mamh", hocPhan.getTenmh());
                objCTMH.put("nhommh",hocPhan.getManhom());
                objCTMH.put("thu",ct.getThu());
                objCTMH.put("tietbd", ct.getTietbd());
                objCTMH.put("sotiet",ct.getSotiet());
                objCTMH.put("soPhong", ct.getMaphong());
                objCTMH.put("magv",ct.getMagv());
                objCTMH.put("thuchanh", ct.getThuchanh());
                arrCTMH.put(objCTMH);
            }

        }
        return arrCTMH.toString();
    }

}
