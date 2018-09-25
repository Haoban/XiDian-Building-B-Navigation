package com.example.navigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectStart extends AppCompatActivity {

    private String[] data = {
            "竹园公寓1号楼","竹园公寓2号楼","竹园公寓3号楼","竹园公寓4号楼","海棠公寓5号楼",
            "海棠公寓6号楼","海棠公寓7号楼","海棠公寓8号楼","海棠公寓9号楼","海棠公寓10号楼",
            "丁香公寓11号楼","丁香公寓12号楼","丁香公寓13号楼","丁香公寓14号楼","丁香公寓15号楼"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_start);
        setTitle("请选择起点");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                SelectStart.this,android.R.layout.simple_list_item_1,data);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("name",data[position]);
                intent.putExtra("number",position);
                //Toast.makeText(SelectStart.this,position,Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
