package com.evv.java.testrycycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvOut;
    RecyclerView recyclerView;
    MyAdapter adapter;
    LinkedList<Exemplar> list;
    ImageView btnBack, btnShar, btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOut = findViewById(R.id.tvOut);

        list = new LinkedList<>();
        for(int i = 0; i != 20; i++)
            list.add(new Exemplar("Number of line :" + i));

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdapter.Listener1 listener1 = new MyAdapter.Listener1() {
            @Override
            public void onClick(int index) {
                tvOut.setText(list.get(index).text);
            }

            @Override
            public void onCheck() {
                tvOut.setText("new length is : " + list.size());
            }
        };

        adapter = new MyAdapter(list, listener1);
        recyclerView.setAdapter(adapter);

        btnBack = findViewById(R.id.image_view_back);
        btnShar = findViewById(R.id.image_view_shar);
        btnMenu = findViewById(R.id.image_view_menu);

        btnBack.setOnClickListener(this);
        btnShar.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_view_back:
            case R.id.image_view_shar:
            case R.id.image_view_menu:
                break;
        }
    }
}