package br.com.andresgois.FeignApplication;

import java.security.*;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Geração do par de chaves RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // Tamanho da chave, em bits

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Chave pública
        PublicKey publicKey = keyPair.getPublic();
        System.out.println("Chave Pública: " + publicKey);

        // Chave privada
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("Chave Privada: " + privateKey);
    }
}
