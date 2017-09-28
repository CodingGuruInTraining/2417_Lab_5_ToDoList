package com.example.hl4350hb.todolistapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoListItems = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addNewButton = (Button) findViewById(R.id.new_todo_item_button);
        final EditText newToDoEditText = (EditText) findViewById(R.id.new_todo_item_edittext);

        ListView todoListView = (ListView) findViewById(R.id.todo_listview);

        // Create ArrayAdapter with todoListItems ArrayList as the data source
        final ArrayAdapter<String> todoListAdapter = new ArrayAdapter<String>(this, R.layout.todo_list_item, R.id.todo_item_text, todoListItems);

        todoListView.setAdapter(todoListAdapter);

        // Add listener to button to add items to the ListView
        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read whatever user has typed into EditText
                String newItem = newToDoEditText.getText().toString();
                // Make sure some data was entered
                if (newItem.length() == 0) {
                    Toast.makeText(MainActivity.this, "Enter a ToDo item", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add the string to the ArrayList
                todoListItems.add(newItem);
                // Notify the ArrayAdapter
                todoListAdapter.notifyDataSetChanged();
                // Clear EditText
                newToDoEditText.getText().clear();
            }
        });

        // listener responds to long presses on individual list items
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("TAG", "On long click listener");
                final int indexPosition = position;
                final String todoItemText = todoListItems.get(position);

                AlertDialog areYouSureDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete this item?")
                        .setMessage(todoItemText)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Remove item from ArrayList
                                todoListItems.remove(indexPosition);
                                // Notify Adapter of change
                                todoListAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("no", null)
                        .create();

                areYouSureDialog.show();

                return true;
            }
        });
    }
}
