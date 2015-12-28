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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
    }

    public static List<Information> getData(){
        List<Information> data = new ArrayList<>();
        int[] icons ={R.drawable.ic_number1, R.drawable.ic_number2, R.drawable.ic_number3, R.drawable.ic_number4};
        String[] titles = {"item1","item2","item3","item4"};
        for (int i=0; i<(titles.length)+30 && i<(icons.length)+30; i++){
            Information current = new Information();
            //current.sqlid=icons[i%4];
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