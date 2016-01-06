package com.journaldev.sqlite;

/**
 * Created by anupamchugh on 19/10/15.
 */
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddCountryActivity extends Activity implements OnClickListener {

    private EditText addEditText1;
    private EditText addEditText2;
    private EditText addEditText3;
    private EditText addEditText4;
    private EditText addEditText5;
    private EditText dateEditText;
    private Button cancelBtn;
    private Button addTodoBtn;

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

        setTitle("輸入商品資訊");
        setContentView(R.layout.activity_add_record);

        addEditText1 = (EditText) findViewById(R.id.add_edittext1);
        addEditText2 = (EditText) findViewById(R.id.add_edittext2);
        addEditText3 = (EditText) findViewById(R.id.add_edittext3);
        addEditText4 = (EditText) findViewById(R.id.add_edittext4);
        addEditText5 = (EditText) findViewById(R.id.add_edittext5);
        dateEditText = (EditText) findViewById(R.id.add_date_edittext);
        cancelBtn = (Button) findViewById(R.id.cancel_record);
        addTodoBtn = (Button) findViewById(R.id.add_record);

        GregorianCalendar calendar = new GregorianCalendar();
        mYear = calendar.get (Calendar.YEAR);
        mMonth = calendar.get (Calendar.MONTH)+1;
        mDay = calendar.get (Calendar.DAY_OF_MONTH);
        //dateEditText.setText("促銷截止日期: "+ mYear + "/" + mMonth + "/" + mDay);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                dateEditText.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }
        }, calendar.get (Calendar.YEAR), calendar.get (Calendar.MONTH), calendar.get (Calendar.DAY_OF_MONTH));

        dbManager = new DBManager(this);
        dbManager.open();
        cancelBtn.setOnClickListener(this);
        addTodoBtn.setOnClickListener(this);
    }

    public void setDate(View v){
        datePickerDialog.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record:

                if (TextUtils.isEmpty(addEditText1.getText()) || TextUtils.isEmpty(addEditText2.getText()) || TextUtils.isEmpty(addEditText3.getText())){
                    Toast.makeText(this, "前三欄必填哦!", Toast.LENGTH_LONG).show();
                    break;
                }
                int price = Integer.parseInt (addEditText1.getText().toString());
                int pack = Integer.parseInt(addEditText2.getText().toString());
                int sheet = Integer.parseInt(addEditText3.getText().toString());

                if (pack == 0 || sheet ==0) {
                    Toast.makeText(this, "來亂的厚?不可為0哦!", Toast.LENGTH_LONG).show();
                    break;
                } else {
                    float value = (float) price / ( pack * sheet );
                    int scale = 4;//设置位数
                    int roundingMode = 4;//表示四捨五入
                    BigDecimal unitPrice = new BigDecimal((double) value).setScale(scale,roundingMode);

                    if (TextUtils.isEmpty( dateEditText.getText())){
                        GregorianCalendar calendar = new GregorianCalendar();
                        mYear = calendar.get (Calendar.YEAR);
                        mMonth = calendar.get (Calendar.MONTH)+1;
                        mDay = calendar.get (Calendar.DAY_OF_MONTH);
                        dateEditText.setText(""+ mYear + "/" + mMonth + "/" + mDay);
                    }

                    final String item1 = "每抽" + String.valueOf(unitPrice) + "元";
                    final String item2 = addEditText4.getText().toString();
                    final String item3 = addEditText5.getText().toString();
                    final String date = dateEditText.getText().toString();

                    dbManager.insert(item1, item2, item3, date);

                    Intent main = new Intent(AddCountryActivity.this, CountryListActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                    break;
                }
            case R.id.cancel_record:
                Intent main1 = new Intent(AddCountryActivity.this, CountryListActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main1);
                break;

        }
    }

}