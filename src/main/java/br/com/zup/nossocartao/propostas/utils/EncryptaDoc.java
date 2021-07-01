package br.com.zup.nossocartao.propostas.utils;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public abstract class EncryptaDoc {

    private final static int DEFAULT_SALT = 16;

    public static String secHash(String documento) {
        TextEncryptor encryptor = Encryptors.queryableText("document", "5c0744940b5c369b");
        return encryptor.encrypt(documento);
    }
}
