package com.example.retrofitmvplibrary.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;


import androidx.annotation.NonNull;

import com.example.retrofitmvplibrary.BuildConfig;
import com.example.retrofitmvplibrary.MyApplication;
import com.example.retrofitmvplibrary.utils.NetWorkConstants;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 使用Retrofit实现的网络请求。
 * Created by huanzhang on 2016/4/11.
 */
public class RetrofitSource {
    private static final int CONNECT_TIME_OUT = 60;
    private static final int WRITE_TIME_OUT = 120;
    private static final int READ_TIME_OUT = 60;

    private static OkHttpClient okHttpClient;

    public static OkHttpClient getInstance(Context context) {
        if (okHttpClient == null) {
            synchronized (RetrofitSource.class) {
                if (okHttpClient == null) {

                    ClearableCookieJar cookieJar =
                            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
                    okHttpClient = new OkHttpClient().newBuilder()
                            .addNetworkInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(@NonNull Chain chain) throws IOException {
                                    Request request = chain.request();
                                    HttpUrl.Builder builder = request.url().newBuilder()
                                            .addQueryParameter("device", Build.MODEL);
                                    request = request.newBuilder().url(builder.build()).build();
                                    return chain.proceed(request);
                                }
                            })
                            .cookieJar(cookieJar)
                            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            .sslSocketFactory(createSSLSocketFactory())
                            .hostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String hostname, SSLSession session) {
                                    return true;
                                }
                            })
                            .build();


//                    if (BuildConfig.DEBUG) {
//                        okHttpClient.addNetworkInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//                            @Override
//                            public void log(@NonNull String message) {
//                                Log.e("Retrofit", message);
//                            }
//                        }).setLevel(HttpLoggingInterceptor.Level.BODY));
//                    }
                }
            }
        }

        return okHttpClient;
    }

    public static <T> T createApi(Class<T> clazz, Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkConstants.MURL)
                .client(getInstance(context))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    public static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        //创建管理器
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        }};
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            //为OkHttpClient设置sslSocketFactory
            return sslContext.getSocketFactory();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


}
