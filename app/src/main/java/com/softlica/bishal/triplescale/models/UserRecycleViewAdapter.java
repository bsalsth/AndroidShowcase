package com.softlica.bishal.triplescale.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softlica.bishal.triplescale.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;


/**
 * Created by bishal on 7/6/2017.
 */

public class UserRecycleViewAdapter extends RecyclerView.Adapter<UserRecycleViewAdapter.ViewHolder> {
    private List<User> data = Collections.emptyList();

    private static Context context;
    private OnCustomClickListener listener;

    public void setListener(OnCustomClickListener listener) {
        this.listener = listener;
    }

    public UserRecycleViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<User> data) {
        this.data = Collections.EMPTY_LIST;
        this.data = data;
        notifyItemRangeChanged(0, data.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        User user = data.get(position);
        User.Name userName = user.getName();
        holder.user_name.setText(user.getFullName());
        holder.age.setText(user.getDob() + " years");
        // Thumbnail quality is too low thats why I have to use the larger one
        Picasso.with(context).load(user.getPicture().getLarge()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.profile_image);

        holder.v.setOnClickListener(view -> {
            listener.onSuccess(user);
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView profile_image;
        TextView user_name, age;
        View v;

        public ViewHolder(View v) {
            super(v);
            this.v = v;
            user_name = (TextView) v.findViewById(R.id.user_name);
            age = (TextView) v.findViewById(R.id.age);
            profile_image = (ImageView) v.findViewById(R.id.profile_image);
        }
    }

    public interface OnCustomClickListener {
        public void onSuccess(User user);
    }

}
