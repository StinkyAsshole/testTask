package com.example.test.testtask.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.test.testtask.R;
import com.example.test.testtask.Tools.SwipeListFragment;
import com.example.test.testtask.model.pojo.User;
import com.example.test.testtask.presenter.UserListPresenter;
import com.example.test.testtask.presenter.UserListPresenterImpl;
import com.example.test.testtask.ui.adapter.UserListAdapter;
import com.example.test.testtask.view.UserListView;

import java.util.List;

public class UserListFragment extends SwipeListFragment implements UserListView{
    final public static String FRAGMENT_TAG = "UserListFragmentTag";

    private UserListPresenter userListPresenter;
    private RecyclerView userListView;
    private UserListAdapter adapter;

    public UserListFragment(){
        userListPresenter = new UserListPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_list_fragment, null, false);
        initSwipeRefresh(view, R.id.swipeRefresh);

        userListView = (RecyclerView) view.findViewById(R.id.list);
        userListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UserListAdapter();
        adapter.setOnClickListener((v, user)-> showInfo(user));
        userListView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.user_list);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userListPresenter.nextPage();
    }

    public static UserListFragment getInstance(){
        UserListFragment fragment = new UserListFragment();
        return fragment;
    }

    @Override
    public void showInfo(User user) {
        UserInfoFragment fragment = UserInfoFragment.getInstance(user);
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment, UserInfoFragment.FRAGMENT_TAG)
                .addToBackStack(MainActivity.BACKSTACK_NAME)
                .commit();
    }

    @Override
    public void addToList(List<User> users) {
        adapter.addItems(users);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable e) {
        Snackbar.make(getView(), R.string.unknown_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void update() {
        userListPresenter.update();
    }
}
