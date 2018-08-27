package com.tsdj.crypto;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

public class RSACrypter {

    public AsymmetricCipherKeyPair KeyPair;
    private SecureRandom random;

    public RSACrypter(BigInteger exponent,int strength) throws NoSuchAlgorithmException {
        RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
        random = SecureRandom.getInstance("SHA1PRNG");
        RSAKeyGenerationParameters params = new RSAKeyGenerationParameters(
                exponent,
                random,
                strength,
                80);
        generator.init(params);
        KeyPair = generator.generateKeyPair();
    }

    public  String Encrypt(byte[] data) throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        AsymmetricKeyParameter publicKey = KeyPair.getPublic();
        RSAEngine engine = new RSAEngine();
        engine.init(true, publicKey); //true if encrypt
        byte[] hexEncodedCipher = engine.processBlock(data, 0, data.length);
        return getHexString(hexEncodedCipher);
    }

    public  String Decrypt(String encrypted) throws InvalidCipherTextException{
        Security.addProvider(new BouncyCastleProvider());
        AsymmetricKeyParameter privateKey = KeyPair.getPrivate();
        AsymmetricBlockCipher engine = new RSAEngine();
        engine.init(false, privateKey); //false for decryption
        byte[] encryptedBytes =  hexStringToByteArray(encrypted);
        byte[] hexEncodedCipher = engine.processBlock(encryptedBytes, 0, encryptedBytes.length);
        return new String (hexEncodedCipher);
    }

    private String getHexString(byte[] b)  {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
