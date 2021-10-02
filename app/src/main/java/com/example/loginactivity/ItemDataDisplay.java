package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDataDisplay extends AppCompatActivity {
    TextView n,d,p,q;
    String name;
    int quantity;
    int price;
    String date;
    int id,selectedID;
    Button del,nvm;
    ItemDatabaseHelper mItemDatabaseHelper;
    Notifications notifs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_data_display);
        n = (TextView) findViewById(R.id.iName);
        d = (TextView) findViewById(R.id.iDate);
        p = (TextView) findViewById(R.id.prc);
        q = (TextView) findViewById(R.id.qty);
        mItemDatabaseHelper = new ItemDatabaseHelper(this);
        notifs = new Notifications();

        Intent intent = getIntent();
        id = intent.getIntExtra("itemId",-1);
        selectedID = intent.getIntExtra("id" , -1);

        Cursor data = mItemDatabaseHelper.getItemDataForDisplay(id);
        while(data.moveToNext())
        {
            name=(data.getString(2));
            quantity=(data.getInt(3));
            price=(data.getInt(4));
            date=(data.getString(5));
        }







        n.setText("Item : "+name);
        d.setText("Date of Expiry : "+date);
        p.setText("Price : "+price);
        q.setText("Quantity : "+quantity);

        nvm = (Button) findViewById(R.id.nvm);
        nvm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextintent = new Intent(ItemDataDisplay.this,ListItems.class);
                nextintent.putExtra("id",selectedID);
                startActivity(nextintent);
            }
        });

        del=(Button) findViewById(R.id.delete);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemDatabaseHelper.deleteItem(id);
                toastMessage("Item Deleted!");
                Intent intent = new Intent(ItemDataDisplay.this,ListItems.class);
                intent.putExtra("id",selectedID);
                startActivity(intent);
            }
        });

    }
    private  void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}