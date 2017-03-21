package com.example.test.testtask.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.testtask.R;
import com.example.test.testtask.model.pojo.Repo;
import com.example.test.testtask.presenter.UserInfoPresenter;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;


public class UserRepoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_REPO = 1;
    private final static int TYPE_SHOW_MORE = 2;

    private UserInfoPresenter presenter;
    private List<Repo> list;
    private boolean isLastShow = true;

    public UserRepoListAdapter(UserInfoPresenter presenter, List<Repo> list){
        this.presenter = presenter;
        this.list = list;
    }

    public UserRepoListAdapter(UserInfoPresenter presenter){
        this.presenter = presenter;
        this.list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType){
            default:
            case TYPE_REPO:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_repo_list_item, parent, false);
                return new ViewHolder(v);
            case TYPE_SHOW_MORE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.btn_show_more, parent, false);
                return new BtnMoreViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ViewHolder vh = (ViewHolder) holder;
            vh.bind(list.get(position));
        } else {
            BtnMoreViewHolder btnVh = (BtnMoreViewHolder) holder;
            btnVh.text.setOnClickListener(v -> {
                presenter.nextPage();
                isLastShow = true;
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        return isLastShow   ?
                list.size() :
                list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLastShow){
            return TYPE_REPO;
        }

        if (list.size() == position){
            return TYPE_SHOW_MORE;
        }

        return TYPE_REPO;
    }

    public void addItems(List<Repo> list, boolean isLast){
        this.list.addAll(list);
        isLastShow = isLast;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView date;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
        }

        public void bind(Repo repo){
            name.setText(repo.getName());
            if (repo.getCreatedAt() == null){
                date.setText("-");
            } else {
                date.setText(LocalDate.parse(repo.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
        }
    }
    public static class BtnMoreViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public BtnMoreViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.text);
        }
    }


}
