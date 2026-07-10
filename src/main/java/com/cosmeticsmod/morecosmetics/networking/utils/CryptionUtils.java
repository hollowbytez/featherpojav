/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.networking.utils.CryptionUtils
 */
package com.cosmeticsmod.morecosmetics.networking.utils;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptionUtils {
    public static KeyPair generateRSAKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            return keyPairGenerator.generateKeyPair();
        }
        catch (NoSuchAlgorithmException e) {
            MoreCosmetics.catchThrowable((Throwable)e);
            return null;
        }
    }

    public static byte[] decryptRSA(byte[] cipherTextArray, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(2, privateKey);
        return cipher.doFinal(cipherTextArray);
    }

    public static byte[] encodePublicKey(PublicKey publicKey) {
        return publicKey.getEncoded();
    }

    public static Cipher generateCipher(byte[] secret, int mode, byte[] iv) {
        PBEKeySpec spec = new PBEKeySpec(new String(secret, StandardCharsets.UTF_8).toCharArray(), iv, 65536, 128);
        try {
            byte[] key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec).getEncoded();
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            IvParameterSpec parameterSpec = new IvParameterSpec(secretKey.getEncoded());
            cipher.init(mode, (Key)secretKey, parameterSpec);
            return cipher;
        }
        catch (Exception e) {
            MoreCosmetics.catchThrowable((Throwable)e);
            return null;
        }
    }
}

