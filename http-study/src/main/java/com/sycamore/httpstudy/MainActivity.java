package com.sycamore.httpstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.sycamore.httpstudy.retrofit.GithubService;
import com.sycamore.httpstudy.retrofit.Repo;

import java.util.List;

import okhttp3.ResponseBody;
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
                Log.d("MainActivity", "response:" + gson.toJson(response.body()));
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });


        // 同步
        // repos.execute();

        Log.d("MainActivity", "repos:" + repos.toString());


    }
}