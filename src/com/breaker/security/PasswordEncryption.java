package com.breaker.security;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;

/**
 * This class is used to encrpyt and decrypt passwords. I got most of this code
 * off the internet somewhere, but I forget where. :)
 * 
 * @author Mike Pennington
 */
public class PasswordEncryption {

    private static final String KEY_STRING = "9-12-20-7-4-2-82-100";

    public static String encrypt(String source) {
        String encryptedText = null;
        Key key = getKey();
        try {
            Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            desCipher.init(1, key);
            byte cleartext[] = source.getBytes();
            byte ciphertext[] = desCipher.doFinal(cleartext);
            encryptedText = getString(ciphertext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    public static String decrypt(String source) {
        String decryptedText = null;
        Key key = getKey();
        try {
            Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            byte ciphertext[] = getBytes(source);
            desCipher.init(2, key);
            byte cleartext[] = desCipher.doFinal(ciphertext);
            decryptedText = new String(cleartext);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }

    public static boolean isEncrypted(String text) {
        if (text.indexOf('-') == -1)
            return false;
        for (StringTokenizer st = new StringTokenizer(text, "-", false); st.hasMoreTokens();) {
            String token = st.nextToken();
            if (token.length() > 3)
                return false;
            for (int i = 0; i < token.length(); i++)
                if (!Character.isDigit(token.charAt(i)))
                    return false;

        }

        return true;
    }

    private static Key getKey() {
        javax.crypto.SecretKey s = null;
        byte bytes[] = getBytes(KEY_STRING);
        try {
            DESKeySpec pass = new DESKeySpec(bytes);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            s = skf.generateSecret(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    private static String getString(byte bytes[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            sb.append(0xff & b);
            if (i + 1 < bytes.length)
                sb.append("-");
        }

        return sb.toString();
    }

    private static byte[] getBytes(String str) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int i;
        for (StringTokenizer st = new StringTokenizer(str, "-", false); st.hasMoreTokens(); bos
                .write((byte) i))
            i = Integer.parseInt(st.nextToken());

        return bos.toByteArray();
    }
}