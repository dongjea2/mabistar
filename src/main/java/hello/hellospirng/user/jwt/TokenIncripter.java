package hello.hellospirng.user.jwt;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;

public class TokenIncripter {
    //노출금지
    private final static String  iv = "0987654321654321";
    //
    public static Key getAESKey() throws UnsupportedEncodingException {
        String iv;
        Key keySpec;

        String key = "1234567890123456";
        iv = key.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");

        int len = b.length;
        if (len > keyBytes.length) {
            len = keyBytes.length;
        }

        System.arraycopy(b, 0, keyBytes, 0, len);
        keySpec = new SecretKeySpec(keyBytes, "AES");

        return keySpec;
    }

    // 암호화
    public static String encAES(String str) throws Exception {
        Key keySpec = getAESKey();
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));

        return enStr;
    }

    // 복호화
    public static String decAES(String enStr) throws Exception {
        Key keySpec = getAESKey();
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
        byte[] byteStr = Base64.decodeBase64(enStr.getBytes("UTF-8"));
        String decStr = new String(c.doFinal(byteStr), "UTF-8");

        return decStr;
    }

    public static void main(String[] args) throws Exception {
        TokenIncripter i = new TokenIncripter();
//        String encoded= i.encAES("fsdfsdfasdfsdfasdfasdfsdafasdfasdfsadfdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff안녕하세요");
//        System.out.println(encoded);
        System.out.println(i.decAES("Be2Kvf+t/tRB13zmcPgICXIjQz6IqXkP9Y7srGkhmRELqwrsFlWfVvc79SBYXf6F6DDx5lBNwIWY023qcameCHWf5XgRwwSCRjuVDLEZ8z9QQz6VF2M7WVo3fY36wesCNUptQR3Tm5+UX5WwLYuZgeJXcIZELyJX/bQVVUdTKsjzBdsP72wJKnY/ZbHscCZrY88X1A7BfBOYrmW9hct8I5B2yGp0uITCrdpaZsjsH9tW0C848YNch3FLJSdSIaft2gZtS+B+ykby//XSBI3hCw=="));
    }

}
