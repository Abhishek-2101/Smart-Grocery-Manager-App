package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lists extends AppCompatActivity implements DialogBox.ListDialogListener {

    DatabaseHelper mDatabaseHelper;
    Button add;
    ListView list;
    String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        add= (Button) findViewById(R.id.add);
        list = (ListView) findViewById(R.id.ListItem);
        mDatabaseHelper=new DatabaseHelper(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });

        populateListView();
    }

    public void AddData(String newEntry)
    {
        boolean insertData = mDatabaseHelper.addData(newEntry);
        if(insertData) {
            toastMessage("List added successfully");
            Intent i = new Intent(Lists.this, Lists.class);
            finish();
            overridePendingTransition(0, 0);
            startActivity(i);
            overridePendingTransition(0, 0);
        }
        else
            toastMessage("Something went wrong, try again");
    }

    public void openDialog()
    {
        DialogBox dialog = new DialogBox();
        dialog.show(getSupportFragmentManager(),"Dialog");
    }

    @Override
    public void getText(String name) {
        item=name;
        if(item.length()!=0)
            AddData(item);
        else
            toastMessage("Please enter list name!");
    }

    public void populateListView()
    {
        Cursor data = mDatabaseHelper.getData();
        ArrayList <String> items = new ArrayList<>();
        while(data.moveToNext())
        {
            items.add(data.getString(1));
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,items);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();

                Cursor data = mDatabaseHelper.getItemID(name);
                int itemID = -1;
                while(data.moveToNext())
                {
                    itemID = data.getInt(0);
                }
                if(itemID>-1)
                {
                    Intent listitemintent = new Intent(Lists.this , ListItems.class);
                    listitemintent.putExtra("id",itemID);
                    listitemintent.putExtra("name",name);
                    startActivity(listitemintent);
                }
                else
                    toastMessage("No ID associated with that list");
            }
        });
    }

    private  void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}