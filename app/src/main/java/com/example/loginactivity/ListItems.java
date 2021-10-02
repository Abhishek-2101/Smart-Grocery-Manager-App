package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListItems extends AppCompatActivity implements DialogBox.ListDialogListener {

    private String selectedName;
    private int selectedID;
    Button add;
    DatabaseHelper mDatabaseHelper;
    ItemDatabaseHelper mItemDatabaseHelper;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListItems.this,Navigation.class);
                startActivity(intent);
            }
        });

        add=findViewById(R.id.checkitemadd);


        Intent intent = getIntent();
        selectedID = intent.getIntExtra("id",-1);
        selectedName = intent.getStringExtra("name");
        mDatabaseHelper=new DatabaseHelper(this);
        mItemDatabaseHelper = new ItemDatabaseHelper(this);
        addItem();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListItems.this , GetData.class);
                intent.putExtra("id",selectedID);
                startActivity(intent);
            }
        });

    }



    public void addItem()
    {
        ListView l = findViewById(R.id.CheckableList);
        l.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final Cursor data = mItemDatabaseHelper.getItemData(selectedID);

        ArrayList<String> checkable = new ArrayList<>();
        while(data.moveToNext())
        {
            checkable.add(data.getString(2));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.checked_listview,R.id.checkeditem,checkable);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemname = parent.getItemAtPosition(position).toString();

                Cursor data = mItemDatabaseHelper.getListItemID(itemname);
                int itemID = -1;
                while(data.moveToNext())
                {
                    itemID = data.getInt(0);
                }
                if(itemID>-1) {
                    Intent intent = new Intent(ListItems.this, ItemDataDisplay.class);
                    intent.putExtra("itemId",itemID);
                    intent.putExtra("id",selectedID);
                    startActivity(intent);
                }
                else
                    toastMessage("No ID associated with that Item");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void editList(String newName)
    {
        if(newName.length()==0)
            Toast.makeText(this,"Please enter list name!",Toast.LENGTH_SHORT).show();
        else {
            mDatabaseHelper.updateList(newName, selectedID, selectedName);
            Intent intent = new Intent(ListItems.this, Lists.class);
            startActivity(intent);
        }
    }

    @Override
    public void getText(String name) {
        editList(name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.menu_edit:
                openDialog();
                break;

            case R.id.menu_delete:
                mDatabaseHelper.deleteList(selectedID,selectedName);
                Toast.makeText(this, "List Deleted from Database!", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(ListItems.this,Lists.class);
                startActivity(intent1);
                break;
        }

        return true;
    }

    public void openDialog()
    {
        DialogBox dialog = new DialogBox();
        dialog.show(getSupportFragmentManager(),"Dialog");
    }

    private  void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}