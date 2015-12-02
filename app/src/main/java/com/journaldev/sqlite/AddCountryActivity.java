package com.journaldev.sqlite;

/**
 * Created by anupamchugh on 19/10/15.
 */
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddCountryActivity extends Activity implements OnClickListener {

    private Button addTodoBtn;
    private EditText addEditText1;
    private EditText addEditText2;
    private EditText dateEditText;

    private DBManager dbManager;
    private TextView textDate;
    private Button doSetDate;
    private DatePickerDialog datePickerDialog;
    public int mYear;
    public int mMonth;
    public int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("新增支出");

        setContentView(R.layout.activity_add_record);

        addEditText1 = (EditText) findViewById(R.id.add_edittext1);
        addEditText2 = (EditText) findViewById(R.id.add_edittext2);
        dateEditText = (EditText) findViewById(R.id.add_date_edittext);
        addTodoBtn = (Button) findViewById(R.id.add_record);

        GregorianCalendar calendar = new GregorianCalendar();
        mYear = calendar.get (Calendar.YEAR);
        mMonth = calendar.get (Calendar.MONTH)+1;
        mDay = calendar.get (Calendar.DAY_OF_MONTH);
        dateEditText.setText("日期: "+ mYear + "/" + mMonth + "/" + mDay);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                dateEditText.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }
        }, calendar.get (Calendar.YEAR), calendar.get (Calendar.MONTH), calendar.get (Calendar.DAY_OF_MONTH));

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);
    }
    public void setDate(View v){
        datePickerDialog.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record:

                final String item1 = addEditText1.getText().toString();
                final String item2 = addEditText2.getText().toString();
                final String date = dateEditText.getText().toString();

                dbManager.insert(item1, item2, date);

                Intent main = new Intent(AddCountryActivity.this, CountryListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
    }

}