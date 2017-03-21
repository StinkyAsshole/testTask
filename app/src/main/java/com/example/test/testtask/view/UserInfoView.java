package com.example.test.testtask.view;

import com.example.test.testtask.model.pojo.Repo;
import java.util.List;


public interface UserInfoView extends BaseView, ProgressView, UpdatebleView {
    void addToList(List<Repo> repoList, boolean isLast);
}
