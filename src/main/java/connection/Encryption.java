package connection;
import java.nio.charset.StandardCharsets;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
/**
 * @author Loan (^._.^)ﾉ
 */
public class Encryption {
    public static PublicKey publicKey;
    private static PrivateKey privateKey;
    public static String encryptData(SecretKey secretKey, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] byteEncrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        String encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);
        return encrypted;
    }

    public static String decryptData(SecretKey secretKey, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] byteDecrypted = cipher.doFinal(Base64.getDecoder().decode(data));
        String decrypted = new String(byteDecrypted, StandardCharsets.UTF_8);
        return decrypted;
    }


    public static void generateKey() throws NoSuchAlgorithmException {
        SecureRandom sr = new SecureRandom();
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024, sr);
        KeyPair kp = kpg.genKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();
    }

    public static PublicKey getPublicKey() {
        return publicKey;
    }

    public static void setPublicKey(PublicKey publicKey) {
        Encryption.publicKey = publicKey;
    }

    public static PrivateKey getPrivateKey() {
        return privateKey;
    }

    public static void setPrivateKey(PrivateKey privateKey) {
        Encryption.privateKey = privateKey;
    }
    public static SecretKey generateSessionKey() throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        SecretKey aesKey = kgen.generateKey();
        return aesKey;
    }

    public static String encryptSessionKey(SecretKey sessionKey, PublicKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String strSessionKey = Encryption.getSesssionKeyToString(sessionKey);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] byteEncrypted = cipher.doFinal(strSessionKey.getBytes());
        String encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);
        return encrypted;
    }

    public static String decryptSessionKey(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] byteDecrypted = cipher.doFinal(Base64.getDecoder().decode(data));
        String decrypted = new String(byteDecrypted);
        return decrypted;
    }


    public static String getStringPublicKey(){
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static String getSesssionKeyToString(SecretKey secretKey){
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static PublicKey getPublicKeyFromString(String strPublicKey) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] byte_pubkey  = Base64.getDecoder().decode(strPublicKey);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey key =  factory.generatePublic(new X509EncodedKeySpec(byte_pubkey));
        return key;
    }

    public static SecretKey getSecretKeyFromString(String strSecretKey) throws NoSuchAlgorithmException {
        byte[] decodedKey = Base64.getDecoder().decode(strSecretKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, "AES");
        return originalKey;
    }

    public static void main(String[] args) {
        try{
            SecretKey sessionKey = Encryption.generateSessionKey();
            String en = Encryption.encryptData(sessionKey,"123456");
            System.out.println(en);
            System.out.println(Encryption.decryptData(sessionKey,en));
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
