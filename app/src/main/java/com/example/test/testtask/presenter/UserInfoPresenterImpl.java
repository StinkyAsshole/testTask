package com.example.test.testtask.presenter;

import com.example.test.testtask.Tools.Log;
import com.example.test.testtask.model.pojo.Repo;
import com.example.test.testtask.model.service.GitHubService;
import com.example.test.testtask.model.service.Service;
import com.example.test.testtask.view.UserInfoView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserInfoPresenterImpl implements UserInfoPresenter{
    private GitHubService gitHubService;
    private UserInfoView view;
    private boolean isLast = true;
    private int page = 0;
    private int perPage = 10;
    private String userName;

    public UserInfoPresenterImpl(UserInfoView view, String name){
        gitHubService = Service.create(GitHubService.class);
        this.view = view;
        this.userName = name;
    }

    @Override
    public void nextPage() {
        view.showProgress();
        page++;
        gitHubService.getUserRepoList(userName, page, perPage).delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .doOnNext((response) -> {
                    response.headers();
                    /*
                        Я понимаю, что решение неправильное. По хорошему, URL для запроса следующей страницы нужно брать из заголовка.
                        Но т.к. время на задание у меня ограничено - я просто смотрю есть ли ссылка на следующую страницу.
                     */
                    if (response.headers().get("link") != null)
                        isLast = !response.headers().get("link").contains("next");
                })
                .map(response -> response.body())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        view.addToList(repos, isLast);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(e);
                        view.showError(e);
                        view.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        view.hideProgress();
                    }
                });
    }
}
