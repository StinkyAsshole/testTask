package com.example.test.testtask.view;

import com.example.test.testtask.model.pojo.User;

import java.util.List;

public interface UserListView extends BaseView, UpdatebleView, ProgressView{
    void showInfo(User user);
    void addToList(List<User> users);
}
