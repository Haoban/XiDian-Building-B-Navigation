package com.example.navigation;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Collator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.navigation.Classroom.*;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    textView.setText(data.getStringExtra("name"));
                    number = data.getIntExtra("number",100);
                    break;
                }
            default:
        }
    }

    private TextView textView;
    private int number=100;
    private String classroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        setTitle("B楼导航系统");
        Button startButton = (Button) findViewById(R.id.start_button);
        textView = (TextView) findViewById(R.id.start_text);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this,SelectStart.class);
                startActivityForResult(intent,1);
            }
        });
        final EditText endText = (EditText) findViewById(R.id.end_text);
        Button endButton = (Button) findViewById(R.id.end_button);
        final Button one = (Button) findViewById(R.id.one);
        final Button two = (Button) findViewById(R.id.two);

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number==100){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FirstActivity.this);
                    builder.setTitle("Alert");
                    builder.setMessage("尚未选择起点");
                    builder.setCancelable(true);
                    builder.show();
                }
                else if (endText.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FirstActivity.this);
                    builder.setTitle("Alert");
                    builder.setMessage("请输入目的教室");
                    builder.setCancelable(true);
                    builder.show();
                }else{
                    classroom = endText.getText().toString();
                    if(Classroom.isClassroom(classroom)){
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(endText.getWindowToken(),0);
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.VISIBLE);
                        //classNumber = Integer.valueOf(classroom);
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FirstActivity.this);
                        builder.setTitle("Alert");
                        builder.setMessage("这不是一间教室");
                        builder.setCancelable(true);
                        builder.show();
                    }
                }
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(endText.getWindowToken(),0);
                Intent intent = new Intent(FirstActivity.this,MainActivity.class);
                switch (number){
                    case 0:
                    case 1:
                        intent.putExtra("start1",34.13310419528587);
                        intent.putExtra("start2",108.84698692896613);
                        break;
                    case 2:
                    case 3:
                        intent.putExtra("start1",34.133858694028284);
                        intent.putExtra("start2",108.84525319933188);
                        break;
                    case 4:
                    case 5:
                        intent.putExtra("start1",34.135180918002966);
                        intent.putExtra("start2",108.84169590951237);
                        break;
                    case 6:
                    case 7:
                        intent.putExtra("start1",34.13634625063717);
                        intent.putExtra("start2",108.83909082353343);
                        break;
                    case 8:
                    case 9:
                        intent.putExtra("start1",34.13532285040909);
                        intent.putExtra("start2",108.83846200967645);
                        break;
                    case 10:
                        intent.putExtra("start1",34.13012348526628);
                        intent.putExtra("start2",108.83529099122622);
                        break;
                    case 11:
                    case 12:
                        intent.putExtra("start1",34.129301716981495);
                        intent.putExtra("start2",108.83485980458143);
                        break;
                    case 13:
                    case 14:
                        intent.putExtra("start1",34.12840523329314);
                        intent.putExtra("start2",108.8365935342157);
                        break;
                    default:
                }
                classroom = endText.getText().toString();
                int doorNumber = Classroom.getDoor(classroom);
                /*
                switch (doorNumber){
                    case 1:
                        intent.putExtra("end1",);
                        intent.putExtra("end2",);
                        break;
                    case 2:
                        intent.putExtra("end1",);
                        intent.putExtra("end2",);
                        break;
                    case 3:
                        intent.putExtra("end1",);
                        intent.putExtra("end2",);
                        break;
                    case 4:
                        intent.putExtra("end1",);
                        intent.putExtra("end2",);
                        break;
                    case 5:
                        intent.putExtra("end1",);
                        intent.putExtra("end2",);
                        break;
                }*/
                intent.putExtra("end",doorNumber);
                startActivity(intent);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this,Main2Activity.class);
                intent.putExtra("dormitory",number);
                classroom = endText.getText().toString();
                intent.putExtra("classroom",classroom);
                startActivity(intent);
            }
        });
    }
}
