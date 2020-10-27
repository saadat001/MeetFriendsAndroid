package com.websoftquality.meetfriends.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.websoftquality.meetfriends.Adapters.FriendsSuggestionAdapter;
import com.websoftquality.meetfriends.R;

public class FriendsSuggestionActivity extends AppCompatActivity {
    RecyclerView rvSuggestFriends;
    TextView tvSkip;
    GridLayoutManager gridLayoutManager;
    FriendsSuggestionAdapter friendsSuggestionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_suggestion);
        tvSkip=findViewById(R.id.tvSkip);
        rvSuggestFriends=findViewById(R.id.rvSuggestFriends);
        gridLayoutManager=new GridLayoutManager(this,2);
        rvSuggestFriends.setLayoutManager(gridLayoutManager);
        rvSuggestFriends.setAdapter(friendsSuggestionAdapter);

    }
}