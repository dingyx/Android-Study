package com.sycamore.viewstudy.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sycamore.view_study.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingyx
 * @description: RecyclerView Adapter
 * @date: 2022/4/22
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoViewHolder> {

    private List<User> userList;

    DemoAdapter() {
        userList = new ArrayList<>(UserRepo.USER_LIST);
    }

    @NonNull
    @Override
    public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoViewHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.name);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Bundle payload = (Bundle) payloads.get(0);
            for (String key : payload.keySet()) {
                switch (key) {
                    case User.KEY_NAME:
                        holder.name.setText(payload.getString(key));
                        break;
                    // ......
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class DemoViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public DemoViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
        }
    }

}
