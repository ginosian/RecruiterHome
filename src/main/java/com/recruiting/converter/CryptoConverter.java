package com.recruiting.converter;


import com.recruiting.utils.StringUtils;
import org.springframework.security.crypto.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.Key;
import java.util.Properties;

/**
 * Created by Martha on 5/4/2017.
 */

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

    public static final String algorithm_property_key = "encryption.algorithm";
    public static final String secret_property_key = "encryption.key";

    private static String ALGORITHM = null;
    private static byte[] KEY = null;

    static final Properties properties = new Properties();

    static {
        try {
            properties.load(CryptoConverter.class.getClassLoader()
                    .getResourceAsStream("persistence.properties"));
        } catch (Exception e) {
            properties.put(algorithm_property_key, "AES/ECB/PKCS5Padding");
            properties.put(secret_property_key, "MySuperSecretKey");
        }
        ALGORITHM = (String) properties.get(algorithm_property_key);
        KEY = ((String) properties.get(secret_property_key)).getBytes();
    }

    @Override
    public String convertToDatabaseColumn(String sensitive) {
        if (StringUtils.isNullOrEmpty(sensitive)) return sensitive;
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            final String encrypted = new String(Base64.encode(c
                    .doFinal(sensitive.getBytes())), "UTF-8");
            return encrypted;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String sensitive) {
        if (StringUtils.isNullOrEmpty(sensitive)) return sensitive;
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            final String decrypted = new String(c.doFinal(Base64
                    .decode(sensitive.getBytes("UTF-8"))));
            return decrypted;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
