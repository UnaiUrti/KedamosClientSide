package kedamosclientside.security;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Esta clase representa la parte de encriptacion de la contraseña del usuario.
 *
 * @author Steven Arce
 */
public class Crypt {

    private static ResourceBundle rbp = ResourceBundle.getBundle("kedamosclientside.security.Public");

    /**
     * Este metodo encripta asimetricamente la contraseña.
     *
     * @param passwd Contraseña para encriptar.
     * @return Devuelve la contraseña hexadecimalmente.
     */
    public static String encryptAsimetric(String passwd) {

        byte[] encodedMessage = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            //
            cipher.init(Cipher.ENCRYPT_MODE, readPublicKey());
            //
            encodedMessage = cipher.doFinal(passwd.getBytes());

        } catch (IllegalBlockSizeException | BadPaddingException
                | InvalidKeyException | NoSuchAlgorithmException
                | NoSuchPaddingException ex) {
            Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
        }

        return byteArrayToHexString(encodedMessage);

    }

    /**
     * Este metodo convierte un array de bytes a un string de hexadecimal.
     *
     * @param bytes Array de bytes para parsear a hexadecimal.
     * @return Devuelve el array de bytes en forma de string.
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Este metodo convierte un string en hexadecimal a un array de bytes.
     *
     * @param s String que se pasa para convertirlo a bytes.
     * @return Devuelve el string en forma de array de bytes
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Este metodo genera una nueva contraseña para el cliente.
     *
     * @return Devuelve un string que sera la contraseña generada.
     */
    public static String generatePassword() {

        int length = 16;
        String symbol = ".*_";
        String cap_letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String small_letter = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String finalString = cap_letter + small_letter + numbers + symbol;

        Random random = new Random();

        char[] password = new char[length];

        for (int i = 0; i < length; i++) {
            password[i] = finalString.charAt(random.nextInt(finalString.length()));
        }

        return String.valueOf(password);

    }

    /**
     * Este metodo lee la llave publica para la encriptacion asimetrica.
     *
     * @return Devuelve la llave publica.
     */
    public static PublicKey readPublicKey() {
        PublicKey pubKey = null;
        try {
            // Obtener los bytes del archivo donde este guardado la llave publica
            byte[] pubKeyBytes = hexStringToByteArray(rbp.getString("publicKey"));
            //
            X509EncodedKeySpec encPubKeySpec = new X509EncodedKeySpec(pubKeyBytes);
            //
            pubKey = KeyFactory.getInstance("RSA").generatePublic(encPubKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pubKey;
    }

}
