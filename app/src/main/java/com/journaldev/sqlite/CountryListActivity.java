package com.journaldev.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CountryListActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private MyAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Information> getAllData;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.ITEM1, DatabaseHelper.ITEM2, DatabaseHelper.DATE };

    final int[] to = new int[] { R.id.id, R.id.textView1, R.id.textView2, R.id.date };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_emp_list);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        getAllData = dbManager.getAll();

        recyclerView = (RecyclerView) findViewById(R.id.drawerList);
        recyclerViewAdapter = new MyAdapter(this, getAllData);
        recyclerView.setAdapter(recyclerViewAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

//        listView = (ListView) findViewById(R.id.list_view);
//        listView.setEmptyView(findViewById(R.id.empty));
//        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
//        adapter.notifyDataSetChanged();
//        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
//                TextView idTextView = (TextView) view.findViewById(R.id.id);
//                TextView textView1 = (TextView) view.findViewById(R.id.textView1);
//                TextView textView2 = (TextView) view.findViewById(R.id.textView2);
//                TextView dateTextView = (TextView) view.findViewById(R.id.date);
//
//                String id = idTextView.getText().toString();
//                String item1 = textView1.getText().toString();
//                String item2 = textView2.getText().toString();
//                String date = dateTextView.getText().toString();
//
//                Intent modify_intent = new Intent(getApplicationContext(), ModifyCountryActivity.class);
//                modify_intent.putExtra("id", id);
//                modify_intent.putExtra("item1", item1);
//                modify_intent.putExtra("item2", item2);
//                modify_intent.putExtra("date", date);
//
//                startActivity(modify_intent);
//            }
//        });
    }

    public static List<Information> getData(){
        List<Information> data = new ArrayList<>();
        int[] icons ={R.drawable.ic_number1, R.drawable.ic_number2, R.drawable.ic_number3, R.drawable.ic_number4};
        String[] titles = {"item1","item2","item3","item4"};
        for (int i=0; i<(titles.length)+30 && i<(icons.length)+30; i++){
            Information current = new Information();
            current.iconId=icons[i%4];
            //current.title=titles[i%4];
            current.item1= String.format("item%d", i);
            data.add(current);
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_record) {

            Intent add_mem = new Intent(this, AddCountryActivity.class);
            startActivity(add_mem);

        }
        return super.onOptionsItemSelected(item);
    }

}