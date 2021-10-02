package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.widget.TextView;

public class ExpiredItemsDisplay extends AppCompatActivity {

    ItemDatabaseHelper mItemDatabaseHelper;
    ArrayList<String> expiredItems = new ArrayList<>();
    ListView l;

    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expired_items);

        mItemDatabaseHelper = new ItemDatabaseHelper(this);

        msg = (TextView) findViewById(R.id.message);
        l = (ListView) findViewById(R.id.expired);
        populateListView();
    }

    public void populateListView()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currDate = dateFormat.format(calendar.getTime());

        if(currDate.startsWith("0"))
            currDate = currDate.substring(1);


        String temp4 = "";
        if(currDate.length()==10) {
            temp4 = currDate.substring(0,2);
        }
        else if(currDate.length()==9) {
            temp4 = currDate.substring(0,1);
        }


        Cursor data = mItemDatabaseHelper.notification();
        expiredItems = new ArrayList<>();
        String temp,temp2="";
        while (data.moveToNext()) {
            temp = data.getString(5);
            if(temp.length()==10) {
                temp2 = temp.substring(0,2);
            }
            else if(temp.length()==9) {
                temp2 = temp.substring(0,1);
            }


            if (temp.equals(currDate)) {
                expiredItems.add(data.getString(2));
            }
            else if(Integer.parseInt(temp2) - Integer.parseInt(temp4) < 1)
            {
                expiredItems.add(data.getString(2));
            }
        }
        if(expiredItems.size()==0)
            msg.setText("No items expired yet");
        else
            msg.setText("Items Expired!");

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,expiredItems);
        l.setAdapter(adapter);
    }

}