package com.journaldev.sqlite;

/**
 * Created by testtt on 2015/12/3.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Great on 2015/6/20.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static int COUNT_CACHE_VIEW = 0;
    private static int COUNT = 0;
    private LayoutInflater inflater;
    private Context context;
    ArrayList<Information> data;
    public MyAdapter(Context context, ArrayList<Information> data){
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        Log.i("TestLog", "onCreateViewHolder---" + ++COUNT_CACHE_VIEW);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current=data.get(position);
        holder.icon.setImageResource(current.iconId);
        holder.holderItem1.setText(current.item1);
        holder.holderItem2.setText(current.item2);
        holder.holderItem3.setText(current.date);
        Log.i("TestLog", "onBindViewHolder---" + ++COUNT+ "Position" + position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView icon;
        TextView holderItem1;
        TextView holderItem2;
        TextView holderItem3;
        public MyViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            icon.setOnClickListener(this);
            holderItem1 = (TextView) itemView.findViewById(R.id.listText1);
            holderItem2 = (TextView) itemView.findViewById(R.id.listText2);
            holderItem3 = (TextView) itemView.findViewById(R.id.listText3);
        }

        @Override
        public void onClick(View v) {
            delete(getPosition());
            //Toast.makeText(context, "Item clicked at"+getPosition(), Toast.LENGTH_LONG).show();
        }
    }
}
