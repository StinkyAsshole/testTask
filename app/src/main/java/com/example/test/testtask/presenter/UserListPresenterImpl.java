package com.example.test.testtask.presenter;

import com.example.test.testtask.Tools.Log;
import com.example.test.testtask.model.pojo.User;
import com.example.test.testtask.model.service.GitHubService;
import com.example.test.testtask.model.service.Service;
import com.example.test.testtask.view.UserListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserListPresenterImpl implements UserListPresenter{
    private Integer lastId;
    private Integer perPage = 10;

    private GitHubService gitHubService;
    private UserListView view;

    public UserListPresenterImpl(UserListView view){
        gitHubService = Service.create(GitHubService.class);
        this.view = view;
    }

    @Override
    @SuppressWarnings("all")
    public void nextPage() {
        view.showProgress();
        // задержка для примера прогрессбара
        gitHubService.getUserList(lastId, perPage).delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .flatMap(list -> Observable.fromIterable(list.body()))
                .flatMap(user -> gitHubService.getUserInfo(user.getLogin()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    List<User> userList = new ArrayList<>();

                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(User user) {
                        userList.add(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(e);
                        view.showError(e);
                        view.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        view.addToList(userList);
                        lastId = userList.get(userList.size()-1).getId();
                        view.hideProgress();
                    }
                });
    }

    @Override
    public void update() {
        view.showProgress();
        // обновление списка опущу, чтобы не делать лишних запросов т.к. количество запросов к серверу ограниченно
        view.hideProgress();
    }
}
