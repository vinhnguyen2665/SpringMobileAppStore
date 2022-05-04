package com.vinhnq.common;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwLHzfPpMtTmYGwy58hzj6VNNfPXQ1ZmzrkvKuI63jD7NQ3tUUzNfi07tAXq8akbv+qrF75wMqiqJ9gMMpacTU3lc9pF1yivYVXCeLnSXJMA/PIiF332zD4OROx0o0DWpcMES9l4GA/s0XjfG/U0t/X4S3O/LfZL+wRmcdtFDnDyTLM9BDuRCRCcpjuYlUVitzk8xFIV0yy8hItoxjzH+v1ZJxVkdxRrMdeChbaB2b2+JlIw7ogMCmITIfxXxGASNjnCIBNyQFRpqd8tkAyrWtA24+ixFW1DrdtGJHrjyErTqr5uodSfC1sUi6Uq2gFbLF+4i6XEeOtNzyZstjG8s/wIDAQAB";
    private static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDAsfN8+ky1OZgbDLnyHOPpU0189dDVmbOuS8q4jreMPs1De1RTM1+LTu0BerxqRu/6qsXvnAyqKon2AwylpxNTeVz2kXXKK9hVcJ4udJckwD88iIXffbMPg5E7HSjQNalwwRL2XgYD+zReN8b9TS39fhLc78t9kv7BGZx20UOcPJMsz0EO5EJEJymO5iVRWK3OTzEUhXTLLyEi2jGPMf6/VknFWR3FGsx14KFtoHZvb4mUjDuiAwKYhMh/FfEYBI2OcIgE3JAVGmp3y2QDKta0Dbj6LEVbUOt20YkeuPIStOqvm6h1J8LWxSLpSraAVssX7iLpcR4603PJmy2Mbyz/AgMBAAECggEBAKK4IrMQ8qG+hvGgp40KVHN9wtuZAMa/SnoOypta+ShXCCLKDIVNxorvFTjAmkbsrE3y077o+dDgpyu1m4JvOeStgZE9eEhHCiHsPcdg8Rk5tqy5ZMM7dG/PXF6c2xAnoHP0tryQ3WeTQ7+kPkc8W2/KfMdRP3dFIBeUNwom4H8XNT2kbr4gGbH/bTjNHaHUMX6oT1r5N93P8Q2nO1lA+PKHJTkeUUnOU4lK9xRxy2a1XeVPNKkdcRHx/b6mFpPSAibEGVCJv76/lscy4dDa77TxVgqzHw9aHXy5pPo7fx2tZSXintfG45at2fUn/oeZmcQfuohKsFlZSf7bsd8VuqECgYEA/y5FF15wC+xmmwARU2lbQx3XVwpUTKVLaQNgGR+ohZ+Zb6wOJXPsGfeT66aPPYPzLoC4K7ozHQxwnCNJaPTjg6nFHrxPj6qginnvBA9nvu2LBKOxOwuGd4NLr7m0zJY7injG9YkwCIGhsGhz5WhMGoauns+R0EfW7Q0TG3yZHkcCgYEAwVBTNO/Ei1+Z//2D9ZNsE+yarw0VyWz9GfzkrZZPDs6s0fTJzpnrg/NUYL1urq5BX8+mBGnhHOut9qQcJSL80aPhNW9IO8Msg/2OnrdMtQ/TClvSmSKXbTsRtzhKvSaiwRczab1zusA6V4ogv1nuR/YS8F88H1lPddoG5Zhhv4kCgYAW7BIawknwWEDzRXNI+drP0ZtpXy0pYWO/wCTevOBHeImw5VSz+pDH2Fx6O5D8ZqDumXNxwh0bS+XFsMnEWtMhHZvuYd4Hur0HE2LsC3I3TWFCfm3KLwPaMnoXA+iAxDH/A1Bj9/nPmc1rakaW4eKMM3L2IxEnE3zz+EuyGWQruwKBgEFAxfV2gdex+5/HiYWZr7/B/Czes938JithjCd0N1d+OCPfB0fv+Onfdzn4d6YLOBIJW7bdrpk+jMhWeRGoD8MMGHcbCzzxEtOzATNeGEx9/PTgLeR3IrpCWCaKn9/dbM7U8AvfYXNGc+scuxUcXCx6ScZNv1Otp/7WC8Hwf/5RAoGBAOZArtCfBvkd5qmyB9fLioYqdHxgSYv//30pQsMY9JVQ5tCYeCNXXDpDOZnX9OcUA6vBDehRKufxjJDxY+s8MXrsfid4fzWWqlPdUDfqoWGr6GnxMDcUipoU6Ff469GUIMWZmm+piAnHHw6pPyDIdY7a5e5e+fcLzmDDfPP1NJLU";
    private static PublicKey pubKey = null;
    private static PrivateKey priKey = null;

    public static PublicKey getPublicKey(String base64PublicKey) {
        if (null == pubKey) {
            try {
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                pubKey = keyFactory.generatePublic(keySpec);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return pubKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey) {
        if (null == priKey) {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
            KeyFactory keyFactory = null;
            try {
                keyFactory = KeyFactory.getInstance("RSA");
                priKey = keyFactory.generatePrivate(keySpec);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return priKey;
    }

    public static byte[] encrypt(String data, String publicKey) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            return cipher.doFinal(data.getBytes());
        } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException
                | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String encrypt(String data) {
        String encryptedString = Base64.getEncoder().encodeToString(encrypt(data, publicKey));
        return encryptedString;
    }


    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data) {
        return RSAUtil.decrypt(data, privateKey);
    }

    public static String decrypt(String data, String base64PrivateKey) {
        String decrypt = "";
        try {
            decrypt = decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
        } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException
                | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return decrypt;
    }

    public static void main(String[] args) {
        String encryptedString = Base64.getEncoder().encodeToString(encrypt("Nguyễn Quang Vinh", publicKey));
        System.out.println(encryptedString);
        String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
        System.out.println(decryptedString);
    }
}