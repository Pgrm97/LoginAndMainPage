package com.pucmm.loginandmainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListOSActivity extends AppCompatActivity {

    String items[] = new String[]{
            "Windows 10",
            "macOS",
            "Ubuntu",
            "Fedora",
            "Linux",
            "Raspbian OS",
            "Android OS",
            "iOS"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_o_s);

        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                //intent.putExtra("Name", items[position]);
                //startActivity(intent);
            }
        });
    }
}