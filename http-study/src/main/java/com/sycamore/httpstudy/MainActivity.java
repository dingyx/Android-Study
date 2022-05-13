package com.sycamore.httpstudy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.sycamore.httpstudy.retrofit.GithubService;
import com.sycamore.httpstudy.retrofit.Repo;

import java.io.IOException;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  //      useRetrofit();

  //      useOkHttp();

  //      useCertificatePinner();

  //      useAuthorization();

        useIntercept();
    }

    /**
     * Retrofit 使用
     */
    private void useRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GithubService api = retrofit.create(GithubService.class);

        Call<List<Repo>> repos = api.getRepos();

        // 异步执行网络请求
        // enqueue 不是线性执行、而是将所有请求放在一起执行。 作用：如果同时执行的太多(64),考虑性能问题会按队列顺序来。
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                Log.d("retrofit response", "response:" + gson.toJson(response.body()));
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });

        // 同步
        // repos.execute();
    }


    /**
     * OkHttp 使用
     */
    private void useOkHttp(){

        // 创建一个 OkHttp 的实例
        OkHttpClient client = new OkHttpClient.Builder().build();

        // 创建 Request
        Request request = new Request.Builder()
                .url("https://api.github.com/")
                .build();

        // 创建 Call 并发起网络请求
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.d("okhttp response", response.body().string());
            }
        });

    }

    /**
     * 不使用本地根证书验证
     *
     * 使用自签名证书时，使用这种方式
     */
    private void useCertificatePinner(){
        String hostname = "nas.dingyx.tech:5001";
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add(hostname, "sha256/MdzzbDrPK5vxYAdhrQzTpDzydbwLf40Rxx09h0Tw7H",
                        "sha256/GI75anSEdkuHj05mreE0Sd9jE6dVqUIzzXRHHlZBVbI",
                        "sha256/r/mIkG3eEpVdm+u/ko/cwxzOMo1bk4TyHIlByibiA5E")
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .build();

        Request request = new Request.Builder()
                .url("https://" + hostname)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
               Log.e("useCertificatePinner","onFailure");
               Log.e("useCertificatePinner",e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.d("useCertificatePinner","onSuccess");
                Log.d("useCertificatePinner",response.body().toString());
            }
        });
    }


    /**
     *  OkHttpClient authenticator 使用
     *  刷新 token 重新请求接口
     */
    private void useAuthorization(){

        // 创建一个 OkHttp 的实例
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {
                    @Nullable
                    @Override
                    public Request authenticate(@Nullable Route route, okhttp3.Response response) throws IOException {
                        // 刷新token ...
                        return response.request()
                                .newBuilder()
                                .addHeader("Authorization","Basic ZGluZ3l4JTNBMTIzNDU2")
                                .build();
                    }
                })
                .build();

        // 创建 Request
        Request request = new Request.Builder()
                .url("https://api.github.com/")
                .build();

        // 创建 Call 并发起网络请求
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.d("okhttp response", response.body().string());
            }
        });

    }


    /**
     * 自定义 Intercept
     */
    public void useIntercept(){

        OkHttpClient client = new OkHttpClient.Builder()
                // intercept 在最开始
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        // ... 添加中间节点
                        // 前置工作
                        // ...
                        okhttp3.Response response = chain.proceed(chain.request());
                        // 后置工作
                        // ...
                        return response;
                    }
                })
                // intercept 在最后 （直接缓存返回 不会触发）
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        // 前置工作
                        // ...
                        okhttp3.Response response = chain.proceed(chain.request());
                        // 后置工作
                        // ...
                        return response;
                    }
                })
                .build();

        Request request = new Request.Builder()
                .url("https://api.github.com/")
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.d("okhttp response", response.body().string());
            }
        });

    }

}