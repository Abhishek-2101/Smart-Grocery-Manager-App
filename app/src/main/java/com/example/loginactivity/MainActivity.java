package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText name;
    Button login;
    Notifications notif;
    ItemDatabaseHelper mItemDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=(EditText) findViewById(R.id.Username);
        password=(EditText) findViewById(R.id.Password);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        name=(EditText) findViewById(R.id.Name);
        login=(Button) findViewById(R.id.LoginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(username.getText().toString(),password.getText().toString(),name.getText().toString());
            }
        });
    }
    void validate(String uname,String pwd,String name)
    {
        if(uname.equals("admin") && pwd.equals("admin"))
        {
            notification();
            String welcome = "Welcome " + name;
            Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),welcome,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,Navigation.class);
            startActivity(intent);
        }
    }

    public void notification()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currDate = dateFormat.format(calendar.getTime());
        String temp4 = "";

        if(currDate.startsWith("0"))
            currDate = currDate.substring(1);

        String aboutToExpireDate = "";
        if(currDate.length()==10) {
            aboutToExpireDate = currDate.substring(3);
            temp4 = currDate.substring(0,2);
        }
        else if(currDate.length()==9) {
            aboutToExpireDate = currDate.substring(2);
            temp4 = currDate.substring(0,1);
        }

        notif = new Notifications();
        mItemDatabaseHelper=new ItemDatabaseHelper(this);

        Cursor data = mItemDatabaseHelper.notification();


        String temp,temp2 = "",temp3 = "";
        int i=0;
        while (data.moveToNext())
        {
            temp = data.getString(5);
            if(temp.length()==10) {
                temp2 = temp.substring(3);
                temp3 = temp.substring(0,2);
            }
            else if(temp.length()==9) {
                temp2 = temp.substring(2);
                temp3 = temp.substring(0,1);
            }

            if(temp.equals(currDate))
            {
                Intent intent= new Intent (getApplicationContext(), ExpiredItemsDisplay.class);
                notif.showNotification(MainActivity.this,data.getString(2),"Item Expired!",intent,i);
                i++;
            }
            else if(aboutToExpireDate.equals(temp2) && Integer.parseInt(temp3) - Integer.parseInt(temp4) ==1 )
            {
                Intent intent = new Intent(getApplicationContext(),ExpiredItemsDisplay.class);
                notif.showNotification(MainActivity.this,data.getString(2),"Item about to expire!",intent,i);
                i++;
            }
        }
    }
    private  void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}