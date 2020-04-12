package com.example.recyclerviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements CustomRecyclerViewAdapter.ListItemClickListener {

    CustomRecyclerViewAdapter mCustomRecyclerViewAdapter;
    RecyclerView mRecyclerView;

    Toast mToast;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) this.findViewById(R.id.rv_container);    //since MainActivity is the UI/View
        mCustomRecyclerViewAdapter = new CustomRecyclerViewAdapter(100,this);
        // since this class implements the ListItemClickListener interface, the class by itself can be
        // used as a instance of the ListItemClickListener interface used for registration in CustomRecyclerViewAdapter.

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mCustomRecyclerViewAdapter);
    }

    @Override
    public void ListItemOnClickHandler(int currentItemIndex) {
        if(mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(this,"Item# "+ ++currentItemIndex + " clicked",Toast.LENGTH_SHORT);
        mToast.show();
    }
}
