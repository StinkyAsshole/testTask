package com.example.test.testtask.Tools;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.test.testtask.view.ProgressView;
import com.example.test.testtask.view.UpdatebleView;

public abstract class SwipeListFragment extends Fragment implements ProgressView, UpdatebleView{
    private SwipeRefreshLayout swipeRefresh;

    protected void initSwipeRefresh(View view, int id){
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(id);
        swipeRefresh.setOnRefreshListener(() -> update());
    }

    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void update() {
        hideProgress();
    }
}
