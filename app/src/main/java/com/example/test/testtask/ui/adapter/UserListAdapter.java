package com.example.test.testtask.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.testtask.R;
import com.example.test.testtask.model.pojo.User;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    public interface OnClickListener{
        void onClick(View view, User user);
    }

    private List<User> list;
    private OnClickListener onClickListener;


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public UserListAdapter(List<User> list){
        this.list = list;
    }
    public UserListAdapter(){
        this.list = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position));
        if (onClickListener != null){
            holder.itemView.setOnClickListener(view -> onClickListener.onClick(view, list.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<User> list){
        this.list.addAll(list);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView repoCount;
        SimpleDraweeView image;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            repoCount = (TextView) view.findViewById(R.id.repoCount);
            image = (SimpleDraweeView) view. findViewById(R.id.image);
        }

        public void bind(User user){
            name.setText(user.getLogin());
            repoCount.setText(repoCount.getContext().getResources().getString(R.string.repository_count, user.getPublicRepos()));
            image.setImageURI(Uri.parse(user.getAvatarUrl()));
        }
    }

}
