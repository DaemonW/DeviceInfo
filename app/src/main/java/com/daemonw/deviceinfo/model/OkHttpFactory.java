package com.daemonw.deviceinfo.model;

import com.daemonw.deviceinfo.App;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by daemonw on 16-8-2.
 */

public class OkHttpFactory {

    public static final MediaType MIME_JSON = MediaType.parse("application/json");
    public static final MediaType MIME_XML = MediaType.parse("application/xml");
    public static final MediaType MIME_STREAM = MediaType.parse("application/octet-stream");
    public static final MediaType MIME_TEXT = MediaType.parse("text/plain");
    public static final MediaType MIME_HTML = MediaType.parse("text/html");
    public static final MediaType MIME_FORM = MediaType.parse("application/x-www-form-urlencoded");
    public static final MediaType MIME_MULTIPART = MediaType.parse("multipart/form-data");

    private static OkHttpClient mBaseClient;
    private static OkHttpClient mBaseHttpsClient;

    private OkHttpFactory() {

    }

    public static synchronized void init() {
        mBaseHttpsClient = getTlsClient("root.crt", true);
        mBaseClient = getPlainClient();
    }

    public static synchronized OkHttpClient client(boolean tls) {
        return tls ? mBaseHttpsClient : mBaseClient;
    }

    private static OkHttpClient getPlainClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);
        return builder.build();
    }

    //只信任指定证书
    private static OkHttpClient getTlsClient(String certAsset, boolean trustAll) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);
        try {
            X509TrustManager[] trustManagers = trustAll ? getTrustAllManagers() : getPinTrustManagers(certAsset);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory(), trustManagers[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    private static X509TrustManager[] getPinTrustManagers(String certAsset) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        InputStream inputStream = App.get().getAssets().open(certAsset);
        Certificate ca = certificateFactory.generateCertificate(inputStream);

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        inputStream.close();

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);
        TrustManager[] tm = tmf.getTrustManagers();
        return (X509TrustManager[]) tm;
    }

    private static X509TrustManager[] getTrustAllManagers() throws Exception {
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        return new X509TrustManager[]{tm};
    }


    private static X509TrustManager getTrustManager(InputStream in) throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certs = certificateFactory.generateCertificates(in);
        if (certs.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }
        char[] password = "".toCharArray();
        KeyStore keyStore = emptyKeyStore(password);
        int index = 0;
        for (Certificate cert : certs) {
            String certicateAlias = String.valueOf(index);
            keyStore.setCertificateEntry(certicateAlias, cert);
            index++;
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers: " + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private static KeyStore emptyKeyStore(char[] password) throws GeneralSecurityException {
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null;
            keyStore.load(null, password);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return keyStore;
    }


    public static Request newRequest(String urlPath, HashMap<String, String> headers, boolean tls) {

        Request.Builder builder = new Request.Builder();
        boolean isTLSUrl = urlPath.startsWith("https");
        if (tls && !isTLSUrl) {
            urlPath = urlPath.replaceFirst("http", "https");
        } else if (!tls && isTLSUrl) {
            urlPath = urlPath.replaceFirst("https", "http");
        }
        if (headers != null) {
            Set<Map.Entry<String, String>> h = headers.entrySet();
            for (Map.Entry<String, String> entry : h) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return builder.url(urlPath).build();
    }


    public static Request newRequest(String host,
                                     int port,
                                     String relativeUrlPath,
                                     HashMap<String, String> queryParams,
                                     HashMap<String, String> headers,
                                     boolean tls) {

        Request.Builder builder = new Request.Builder();
        String scheme = tls ? "https" : "http";
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .port(port)
                .addPathSegment(relativeUrlPath);

        if (queryParams != null) {
            Set<Map.Entry<String, String>> params = queryParams.entrySet();
            for (Map.Entry<String, String> entry : params) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        if (headers != null) {
            Set<Map.Entry<String, String>> h = headers.entrySet();
            for (Map.Entry<String, String> entry : h) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return builder
                .url(urlBuilder.build().toString())
                .build();
    }
}
