package com.example.whan_sizebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SizeBook extends Activity {

    private static final String FILENAME = "file.sav";
    private EditText bodyText;
    private ListView recordList;

    private ArrayList<People> peopleList;
    private ArrayAdapter<People> adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_book);

        Button addButton = (Button) findViewById(R.id.add);
        recordList = (ListView) findViewById(R.id.recordList);

        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {



            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<People>(this,
                R.layout.list_item, peopleList);
        recordList.setAdapter(adapter);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson =new Gson();

            //Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-01-24 18:19
            Type listType = new TypeToken<ArrayList<People>>(){}.getType();
            peopleList = gson.fromJson(in,listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            peopleList =new ArrayList<People>();

        } catch (IOException e) {
            // TODO Auto-generated catch block

            throw new RuntimeException();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_APPEND);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(peopleList, out);
            out.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    /**
     * This method is to add new people to the record
     * @param view
     */
    public void addPeople(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Addpeople.class);
        startActivity(intent);
    }

}