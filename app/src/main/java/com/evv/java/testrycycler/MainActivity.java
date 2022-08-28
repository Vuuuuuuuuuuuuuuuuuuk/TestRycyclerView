package com.evv.java.testrycycler;

import android.graphics.Canvas;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    TextView tvOut;
    RecyclerView recyclerView;
    MyAdapter adapter;
    LinkedList<Exemplar> list;
    ImageView btnBack, btnShar, btnMenu;

    PopupMenu menu1, menu2;

    boolean checked = false;

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
            public void onCheck(boolean isChecked) {
                checked = isChecked;
                if(checked){
                    btnShar.setVisibility(View.VISIBLE);
                    btnMenu.setImageResource(R.drawable.ic_baseline_menu_open_24);
                    tvOut.setText("chosen : 0");
                }else{
                    btnShar.setVisibility(View.INVISIBLE);
                    btnMenu.setImageResource(R.drawable.ic_baseline_menu_24);
                    tvOut.setText("");
                }
            }

            @Override
            public void setText(int val) {
                tvOut.setText("chosen : "+val );
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

        menu1 = new PopupMenu(this, btnMenu);
        menu2 = new PopupMenu(this, btnMenu);

        menu1.inflate(R.menu.menu1);
        menu2.inflate(R.menu.menu2);

        menu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1_1:
                        Toast.makeText(MainActivity.this, "Menu 1 item 1", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        menu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1_2:
                        Toast.makeText(MainActivity.this, "Menu 2 item 1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item2_2:
                        Toast.makeText(MainActivity.this, "Menu 2 item 2", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return checked ? makeMovementFlags(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT)
                  : makeMovementFlags(0,0);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int a = viewHolder.getAdapterPosition();
                int b = target.getAdapterPosition();

                Collections.swap(list, a, b);
                adapter.Swap(a,b);
                adapter.notifyItemMoved(a, b);

                return true;
            }

            @Override
            public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
                return 0.2f;
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    float w = viewHolder.itemView.getWidth();
                    float al = 1.0f - Math.abs(dX)/w * 3;

                    viewHolder.itemView.setAlpha(al);
                    viewHolder.itemView.setTranslationX(dX);
                }
                else super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                list.remove(viewHolder.getAdapterPosition());
                adapter.DeleteOne(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_view_back:
                if(checked) adapter.Undo();
                break;
            case R.id.image_view_shar:
                //TODO
            case R.id.image_view_menu:
                if(checked) menu2.show();
                else menu1.show();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
