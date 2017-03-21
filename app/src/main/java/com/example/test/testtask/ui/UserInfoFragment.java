package com.example.test.testtask.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.testtask.R;
import com.example.test.testtask.Tools.SwipeListFragment;
import com.example.test.testtask.model.pojo.Repo;
import com.example.test.testtask.model.pojo.User;
import com.example.test.testtask.presenter.UserInfoPresenter;
import com.example.test.testtask.presenter.UserInfoPresenterImpl;
import com.example.test.testtask.ui.adapter.UserRepoListAdapter;
import com.example.test.testtask.view.UserInfoView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class UserInfoFragment extends SwipeListFragment implements UserInfoView{
    final public static String FRAGMENT_TAG = "UserInfoFragmentTag";
    final private static String ARGS_USER = "args_user";

    private RecyclerView repoListView;
    private UserInfoPresenter userInfoPresenter;
    private UserRepoListAdapter adapter;
    private User user;

    public UserInfoFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         user = (User) getArguments().getSerializable(ARGS_USER);
        if (user == null){
            throw new RuntimeException("argument is missing");
        }
        userInfoPresenter = new UserInfoPresenterImpl(this, user.getLogin());

        View view = inflater.inflate(R.layout.user_info_fragment, null, false);
        initSwipeRefresh(view, R.id.swipeRefresh);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(user.getLogin());
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        repoListView = (RecyclerView) view.findViewById(R.id.repoList);
        repoListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UserRepoListAdapter(userInfoPresenter);
        repoListView.setAdapter(adapter);

        SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.image);
        image.setImageURI(Uri.parse(user.getAvatarUrl()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInfoPresenter.nextPage();
    }

    public static UserInfoFragment getInstance(User user){
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER, user);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void addToList(List<Repo> repoList, boolean isLast) {
        adapter.addItems(repoList, isLast);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable e) {
        Snackbar.make(getView(), R.string.unknown_error, Snackbar.LENGTH_LONG).show();
    }
}
