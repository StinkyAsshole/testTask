package com.example.test.testtask.model.service;

import com.example.test.testtask.Tools.BaseUrl;
import com.example.test.testtask.model.pojo.Repo;
import com.example.test.testtask.model.pojo.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@BaseUrl(value = "https://api.github.com/")
public interface GitHubService {

    @GET("/users")
    Observable<Response<List<User>>> getUserList(@Query("per_page") Integer perPage);

    @GET("/users")
    Observable<Response<List<User>>> getUserList(@Query("since") Integer since, @Query("per_page") Integer perPage);

    @GET("/users/{name}")
    Observable<User> getUserInfo(@Path("name") String name);

    @GET("/users/{name}/repos")
    Observable<Response<List<Repo>>> getUserRepoList(@Path("name") String name, @Query("page") Integer page, @Query("per_page") Integer perPage);
}
