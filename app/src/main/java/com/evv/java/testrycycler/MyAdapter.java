package com.evv.java.testrycycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    interface Listener1{
        void onClick( int index );
        void onCheck(boolean isChecked);

        void setText(int val);
    }

    private LinkedList<Exemplar> list;
    private Listener1 listener1;
    private int checked[];
    private int tot;
    private boolean isLongChecked;

    public MyAdapter(LinkedList<Exemplar> list, Listener1 listener1){
        this.list = list;
        this.listener1 = listener1;
        checked = new int[list.size()];
        tot = 0;
        isLongChecked = false;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        MyHolder holder = new MyHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLongChecked){
                    int id = holder.getAdapterPosition();

                    if(checked[id] == 0){
                        tot++;
                        checked[id] = 1;
                    }else{
                        tot--;
                        checked[id] = 0;
                    }

                    listener1.setText(tot);
                    holder.item_image_view_checked.setImageResource(checked[id] == 0 ?
                            R.drawable.ic_baseline_check_box_outline_blank_24 : R.drawable.ic_baseline_check_box_24);
                }
                else listener1.onClick(holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!isLongChecked){
                    isLongChecked = true;
                    listener1.onCheck(true);
                    notifyDataSetChanged();
                }

                return true;
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.item_image_view.setImageResource(R.drawable.ic_launcher_foreground);
        holder.item_text_view.setText(list.get(position).text);
        holder.item_image_view_checked.setImageResource(checked[position] == 0 ?
          R.drawable.ic_baseline_check_box_outline_blank_24 : R.drawable.ic_baseline_check_box_24);
        holder.item_image_view_checked.setVisibility(isLongChecked ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView item_text_view;
        ImageView item_image_view;
        ImageView item_image_view_checked;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            item_text_view = itemView.findViewById(R.id.item_text_view);
            item_image_view = itemView.findViewById(R.id.item_image_view);
            item_image_view_checked = itemView.findViewById(R.id.item_image_view_checked);
        }
    }

    public void Delete() {
        if(tot != 0){
            tot = 0;
            for(int i = checked.length - 1; i >= 0; i--)
                if(checked[i] == 1) list.remove(i);

            checked = new int[list.size()];
            //listener1.onCheck();
            notifyDataSetChanged();
        }
    }

    public void Undo() {
        isLongChecked = false;

        tot = 0;
        for(int i = checked.length - 1; i >= 0; i--) checked[i] = 0;

        listener1.onCheck(false);
        notifyDataSetChanged();
    }

    public void Swap(int a, int b){
        int x = checked[a];
        checked[a] = checked[b];
        checked[b] = x;
    }
}
