package com.journaldev.sqlite;

/**
 * Created by anupamchugh on 19/10/15.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ModifyCountryActivity extends Activity implements OnClickListener {

    private EditText modifyEditText1;
    private EditText modifyEditText2;
    private EditText modifyEditText3;
    private EditText modifyEditText4;
    private EditText modifyEditText5;
    private EditText modifyDateEditText;
    private Button updateBtn, cancelBtn, deleteBtn;

    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("更新/刪除資料?");

        setContentView(R.layout.activity_modify_record);

        dbManager = new DBManager(this);
        dbManager.open();

        modifyEditText1 = (EditText) findViewById(R.id.modify_edittext1);
        modifyEditText2 = (EditText) findViewById(R.id.modify_edittext2);
        modifyEditText3 = (EditText) findViewById(R.id.modify_edittext3);
        modifyDateEditText = (EditText) findViewById(R.id.modify_date_edittext);
        updateBtn = (Button) findViewById(R.id.btn_update);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String item1 = intent.getStringExtra("item1");
        String item2 = intent.getStringExtra("item2");
        String item3 = intent.getStringExtra("item3");
        String date = intent.getStringExtra("date");

        _id = Long.parseLong(id);

        modifyEditText1.setText(item1);
        modifyEditText2.setText(item2);
        modifyEditText3.setText(item3);
        modifyDateEditText.setText(date);
        updateBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                String item1 = modifyEditText1.getText().toString();
                String item2 = modifyEditText2.getText().toString();
                String item3 = modifyEditText3.getText().toString();
                String date = modifyDateEditText.getText().toString();

                dbManager.update(_id, item1, item2, item3, date);
                this.returnHome();
                break;
            case R.id.btn_cancel:
                this.returnHome();
                break;
            case R.id.btn_delete:
                dbManager.delete(_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), CountryListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
