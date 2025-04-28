package com.app.config;

import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import software.amazon.awssdk.http.TlsTrustManagersProvider;

public class TrustAllTlsTrustManagerProvider implements TlsTrustManagersProvider {

    public static TrustAllTlsTrustManagerProvider create() {
        return new TrustAllTlsTrustManagerProvider();
    }

    @Override
    public TrustManager[] trustManagers() {
        return new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
    }
}
