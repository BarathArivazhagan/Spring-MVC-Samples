package com.barath.app;



import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import org.apache.log4j.Logger;

public class SSLContextHelper {
	
	private static final String KEY_STORE_TYPE="JKS";	
	private static final String CLASS_NAME=SSLContextHelper.class.getName();
	private static final String TRANSPORT_SECURITY_PROTOCOL="TLS";
	private static final Logger logger=Logger.getLogger(SSLContextHelper.class);	
	
	
	public static void enable(String keystoreFileLocation,String keyStorePassword){
		
		String keystoreType = KEY_STORE_TYPE;
	    InputStream inputStream;
		try {
				inputStream	 = new FileInputStream(keystoreFileLocation);
				inputStream=new BufferedInputStream(inputStream);
				char [] keystorePassword = keyStorePassword.toCharArray();
				char [] keyPassword = keyStorePassword.toCharArray();	  	
				KeyStore keystore = KeyStore.getInstance(keystoreType);		
				keystore.load(inputStream, keystorePassword);
				KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
				kmfactory.init(keystore, keyPassword);				
				KeyStore truststore = KeyStore.getInstance(KEY_STORE_TYPE);
				truststore.load(inputStream, keystorePassword);
				TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
				tmfactory.init(keystore);
				KeyManager [] keymanagers = kmfactory.getKeyManagers();
				TrustManager [] trustmanagers =  tmfactory.getTrustManagers();
				SSLContext sslContext = SSLContext.getInstance(TRANSPORT_SECURITY_PROTOCOL);
				sslContext.init(keymanagers, trustmanagers, null);	    	
				SSLContext.setDefault(sslContext);
				HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
				HttpsURLConnection.setDefaultHostnameVerifier(new NullHostnameVerifier());
			} catch (Exception e) {
				logger.error(CLASS_NAME+"Exception in SSL "+e.getMessage());
				e.printStackTrace();
			}
	}
	
	public static void enable(){
		
		enable(System.getProperty("javax.net.ssl.keyStore"),System.getProperty("javax.net.ssl.keyStorePassword"));
	
	}
	
	
	public static SSLContext getSSLContext(String sslFile, String credentials){		
		
		SSLContext sslContext = loadKeyStoreFromFile(sslFile, credentials);		
		return sslContext;
	}

	private static SSLContext loadKeyStoreFromFile(String sslFile, String credentials){
		TrustManagerFactory trustManagerFactory =null;
		KeyStore keystore = null;		
		
		try {
			InputStream inputStream = null;
			logger.debug("loading SSL file path "+sslFile);
			inputStream=new FileInputStream(sslFile);
			
			inputStream = new BufferedInputStream(inputStream);			
			trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			String ssoTrustStorePwd = credentials;
			logger.debug(" Loading key store....");
			keystore.load(inputStream, ssoTrustStorePwd.toCharArray());
			trustManagerFactory.init(keystore);			
			String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
			tmf.init(keystore);			
			
			String tmfAlgorithm1 = KeyManagerFactory.getDefaultAlgorithm();
			KeyManagerFactory  kmf = KeyManagerFactory.getInstance(tmfAlgorithm1);
			kmf.init(keystore,ssoTrustStorePwd.toCharArray());
			
			logger.debug(" trustManagerFactory >>> "+tmf.getTrustManagers());
			logger.debug( " keyManagerFactory >>> "+kmf.getKeyManagers());
//			TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
			
			SSLContext ctx = SSLContext.getInstance(TRANSPORT_SECURITY_PROTOCOL); 
	        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
	        logger.debug(" trustManagerFactory ctx "+ctx);

			return ctx;
		} catch (Exception e) {
			logger.error("ERROR IN LOAD KEY STORE FROM FILE "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	
	
	public static void disable() {
        try {
            SSLContext sslc = SSLContext.getInstance(TRANSPORT_SECURITY_PROTOCOL);
            TrustManager[] trustManagerArray = { (TrustManager) new NullX509TrustManager() };
            sslc.init(null, trustManagerArray, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostnameVerifier());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
 
    private static class NullX509TrustManager implements X509TrustManager {
   
                  
		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {			
			
		}
		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {			
			
		}
		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {			
			return null;
		}
       
    }
 
    private static class NullHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


}
