package com.websoftquality.meetfriends.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.websoftquality.meetfriends.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendsSuggestionAdapter extends RecyclerView.Adapter {
    Context context;
    JSONObject jsonObject;
    int count;
    public FriendsSuggestionAdapter(Context context, JSONObject jsonObject) {
        this.context=context;
        this.jsonObject=jsonObject;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_suggestion_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            Glide.with(context)
                    .load(jsonObject.getJSONArray("suggestions").getJSONObject(position).getJSONObject("profile_picture")
                            .getString("image_url"))
                    .into(((MyViewHolder)holder).ivProfile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        public MyViewHolder(View view) {
            super(view);
            ivProfile=view.findViewById(R.id.ivProfile);
        }
    }
}
