package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class GetData extends AppCompatActivity {

    private TextView dateData,trial;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String mdate,add,qty,prc;
    private Button done;
    private EditText item,quantity,price;
    private int selectedID;
    ItemDatabaseHelper mItemDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);


        item = (EditText) findViewById(R.id.item);
        quantity = (EditText) findViewById(R.id.quantity);
        price = (EditText) findViewById(R.id.price);
        done = (Button) findViewById(R.id.done);
        dateData = (TextView) findViewById(R.id.date);
        mItemDatabaseHelper = new ItemDatabaseHelper(this);
        dateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(GetData.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        mdate = dayOfMonth + "/" + month + "/" + year;
                        dateData.setText(mdate);
                    }
                },year,month,day);
                dialog.show();
            }
        });

        Intent intent = getIntent();
        selectedID = intent.getIntExtra("id",-1);



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add = item.getText().toString();
                qty = quantity.getText().toString();
                prc = price.getText().toString();
                /*int prc2 = Integer.parseInt(prc);
                int qty2 = Integer.parseInt(qty);
                prc2 *= qty2;
                String prc3 = Integer.toString(prc2);*/
                addCheckedData();
                Intent intent = new Intent(GetData.this,ListItems.class);
                intent.putExtra("id",selectedID);
                startActivity(intent);
            }
        });
    }
    public void addCheckedData()
    {
        boolean insertData = mItemDatabaseHelper.addItemData(add,Integer.parseInt(prc),Integer.parseInt(qty),mdate,selectedID);
        if(insertData) {
            toastMessage("Item added successfully");
        }
        else
            toastMessage("Something went wrong, try again");
    }
    private  void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}