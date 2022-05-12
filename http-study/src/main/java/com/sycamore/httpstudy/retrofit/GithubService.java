package com.sycamore.httpstudy.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author dingyx
 * @description:
 * @date: 2022/5/12
 */
public interface GithubService {

    @GET("users/dingyx/repos")
    Call<List<Repo>> getRepos();

}
