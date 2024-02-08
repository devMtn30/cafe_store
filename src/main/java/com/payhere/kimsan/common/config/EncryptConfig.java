package com.payhere.kimsan.common.config;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;

@Configuration
public class EncryptConfig {

    @Value("${encrypt.secret}")
    private String secret;


    @Value("${encrypt.salt}")
    private String salt;

    @Bean
    public AesBytesEncryptor aesBytesEncryptor() {
        return new AesBytesEncryptor(secret, salt);
    }

    private static String byteArrayToString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        for (byte abyte : bytes) {
            sb.append(abyte);
            sb.append(" ");
        }
        return sb.toString();
    }

    private static byte[] stringToByteArray(String byteString) {
        String[] split = byteString.split("\\s");
        ByteBuffer buffer = ByteBuffer.allocate(split.length);
        for (String s : split) {
            buffer.put((byte) Integer.parseInt(s));
        }
        return buffer.array();
    }


    public static String encrypt(AesBytesEncryptor encryptor, String message) {
        if (encryptor == null) {
            return "";
        }
        byte[] encrypt = encryptor.encrypt(message.getBytes(StandardCharsets.UTF_8));
        return byteArrayToString(encrypt);
    }

    public static String decrypt(AesBytesEncryptor encryptor, String message) {
        byte[] decryptBytes = stringToByteArray(message);
        byte[] decrypt = encryptor.decrypt(decryptBytes);
        return new String(decrypt, StandardCharsets.UTF_8);
    }
}
