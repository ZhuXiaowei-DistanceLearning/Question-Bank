package com.zxw.utils;

import org.bouncycastle.tsp.TimeStampToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class DigitalSignatureExample {
    
    // 证书路径
    private static final String CERTIFICATE_PATH = "/path/to/certificate.crt";
    // 证书密码
    private static final String CERTIFICATE_PASSWORD = "password";
    
    public static void main(String[] args) throws Exception {
        // 读取证书
        X509Certificate certificate = readCertificate(CERTIFICATE_PATH, CERTIFICATE_PASSWORD);
        // 获取私钥
        PrivateKey privateKey = getPrivateKey();
        // 待签署的数据
        byte[] dataToSign = "Hello, world!".getBytes();
        // 创建签名实例
        Signature signature = Signature.getInstance("SHA256withRSA");
        // 初始化签名实例
        signature.initSign(privateKey);
        // 添加待签署数据
        signature.update(dataToSign);
        // 生成签名
        byte[] signatureBytes = signature.sign();
        // 验证签名
        boolean isVerified = verifySignature(dataToSign, signatureBytes, certificate.getPublicKey());
        System.out.println("Signature verified: " + isVerified);
        // 创建时间戳
        TimeStampToken timeStampToken = createTimeStampToken(signatureBytes);
        // 验证时间戳
        boolean isTimestampValid = verifyTimestamp(timeStampToken, certificate);
        System.out.println("Timestamp verified: " + isTimestampValid);
    }
    
    // 读取证书
    private static X509Certificate readCertificate(String certificatePath, String password) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {
        FileInputStream inputStream = new FileInputStream(certificatePath);
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, password.toCharArray());
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(inputStream);
        return (X509Certificate) certificate;
    }
    
    // 获取私钥
    private static PrivateKey getPrivateKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        Key key = keyStore.getKey("mykey", "password".toCharArray());
        return (PrivateKey) key;
    }
    
    // 验证数字签名
    private static boolean verifySignature(byte[] data, byte[] signature, PublicKey publicKey) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(data);
        return sig.verify(signature);
    }
    
    // 创建时间戳
    private static TimeStampToken createTimeStampToken(byte[] signature) throws Exception {
//        TimeStampResponse timeStampResponse = TimestampClient.requestTimeStamp(signature);
//        return timeStampResponse.getTimeStampToken();
        return null;
    }
    
    // 验证时间戳
    private static boolean verifyTimestamp(TimeStampToken timeStampToken, X509Certificate certificate) throws Exception {
//        TimeStampTokenValidator validator = new TimeStampTokenValidator(certificate);
//        validator.validate(timeStampToken);
        return true;
    }
}