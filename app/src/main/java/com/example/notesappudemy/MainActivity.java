package com.example.notesappudemy;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
public class MainActivity extends AppCompatActivity {
    ListView listView;
    SharedPreferences sharedPreferences;
    static ArrayList<String> notes;
    static ArrayAdapter arrayAdapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Notes");
        listView = (ListView) findViewById(R.id.listView);
        notes = new ArrayList<>();
        sharedPreferences= getSharedPreferences("com.example.notesappudemy", Context.MODE_PRIVATE);
        HashSet<String> set =(HashSet<String>)sharedPreferences.getStringSet("notes",null);
        if(set==null){
            notes.add("Add a note");
        }else{
            notes=new ArrayList<String>(set);
        }
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                intent.putExtra("noteId", position);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int itemToDelete=position;
                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_delete)
                            .setTitle("Are you sure?")
                            .setMessage("Do you really want to delete this?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    notes.remove(itemToDelete);
                                    arrayAdapter.notifyDataSetChanged();
                                    HashSet<String> set =new HashSet<>(MainActivity.notes);
                                    sharedPreferences.edit().putStringSet("notes",set).apply();
                                }
                            })
                            .setNegativeButton("No",null)
                            .show();
                    return  true;
                }
        });
    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.addNote){
             Intent intent=new Intent(MainActivity.this,SecondActivity.class);
             startActivity(intent);
             return true;

         }
         return false;

    }
}
