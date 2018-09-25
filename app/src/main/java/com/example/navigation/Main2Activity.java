package com.example.navigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navigation.Classroom.*;

public class Main2Activity extends AppCompatActivity {
/*
    class MyView extends View {
        Paint paint;
        public MyView(Context context) {
            super(context);

            paint = new Paint();
            paint.setStrokeWidth((float) 2.0);
            paint.setColor(Color.BLACK);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawLine(10,10,100,100,paint);

        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("B楼内导航");
        //ImageView imageView = findViewById(R.id.image_view);
        TextView out = findViewById(R.id.out);
        TextPaint tp = out.getPaint();
        tp.setFakeBoldText(true);
        Intent intent = getIntent();
        int dormitory = intent.getIntExtra("dormitory",100);
        String classroom = intent.getStringExtra("classroom");
        int doorNumber = Classroom.getDoor(classroom);
        int i = Classroom.getFloor(classroom);
        int j = Classroom.getNumber(classroom);
        String s;
        /*
        imageView.setImageResource(R.drawable.main);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.main).copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setStrokeWidth((float) 2.0);
        paint.setColor(Color.BLACK);
        canvas.drawLine(10,10,100,100,paint);
*/
        if(i > 7){//辅楼
            String s1 = "上"+(i-7)+"层楼梯到第"+(i-4)+"层楼\n";
            if(dormitory < 4){
                if(j == 5)
                    s = s1 + "请您前往东(左)侧的第1个教室";
                else
                    s = s1 + "请您前往西(右)侧的第"+(5-j)+"个教室";
            }
            else{
                if(j == 1)
                    s = s1 + "请您前往西(右)侧的第1个教室";
                else
                    s = s1 + "请您前往东(左)侧的第"+(j-1)+"个教室";
            }
            out.setText(s);
        }
        else {


            /*Paint paint = new Paint();
            paint.setStrokeWidth((float) 2.0);
            paint.setColor(Color.BLACK);
*/
            if(dormitory >= 4 && dormitory <= 9 && doorNumber == 1){

                if(i==1){
                    if(j < 4)
                        s = "请您前往东(左)侧的第"+(4-j)+"个教室";
                    else
                        s = "请您前往西(右)侧的第1个教室";
                }
                else if(i == 2){
                    if(j < 4)
                        s = "上"+(i-1)+"层楼梯到第"+i+"层楼\n" +
                                "请您前往东侧的第"+(4-j)+"个教室";
                    else
                        s = "上"+(i-1)+"层楼梯到第"+i+"层楼\n" +
                                "请您前往西侧的第1个教室";
                }
                else {
                    if(j < 3)
                        s = "请先坐电梯到第"+i+"层\n" +
                                "请您前往东(左)侧的第"+(3-j)+"个教室";
                    else if(j==3)
                        s = "请先坐电梯到第"+i+"层\n" +
                                "目的地为您面前的教室";
                    else
                        s = "请先坐电梯到第"+i+"层\n" +
                                "请您前往西(右)侧的第1个教室";
                }
                out.setText(s);
            }
            else{

                switch (doorNumber){
                    case 1:
                        if(i==1)
                            s = "请您前往西(左)侧的第"+j+"个教室";
                        else
                            s = "上"+(i-1)+"层楼梯到第"+i+"层楼\n" +
                                    "请您前往西(左)侧的第"+j+"个教室";
                        out.setText(s);
                        break;
                    case 2:
                        if(i==1){
                            if(j==3)
                                s = "请您前往东(左)侧的第1个教室";
                            else
                                s = "请您前往西(右)侧的第1个教室";
                        }
                        else if(i == 2){
                            if(j==3)
                                s = "上"+(i-1)+"层楼梯到第"+i+"层楼\n" +
                                        "请您前往东侧的第1个教室";
                            else
                                s = "上"+(i-1)+"层楼梯到第"+i+"层楼\n" +
                                        "请您前往西侧的第1个教室";
                        }
                        else {
                            if(j==2)
                                s = "请先坐电梯到第"+i+"层\n" +
                                        "请您前往东(左)侧的第1个教室";
                            else if(j==3)
                                s = "请先坐电梯到第"+i+"层\n" +
                                        "目的地为您面前的教室";
                            else
                                s = "请先坐电梯到第"+i+"层\n" +
                                        "请您前往西(右)侧的第1个教室";
                        }
                        out.setText(s);
                        break;
                    case 3:
                        if(i==2){
                            if(j < 7)
                                s = "请先走东侧楼梯进入B楼\n" +
                                        "请您前往东(前)侧的第"+(7-j)+"个教室";
                            else
                                s = "请先从西侧入口进入B楼\n" +
                                        "请您前往西(前)侧的第"+(j-7)+"个教室";
                        }
                        else {
                            if(j < 7)
                                s = "请先走东侧楼梯进入B楼\n然后从右侧最近楼梯走"+(i-2)+"层到第"+i+"层楼\n" +
                                        "请您前往东(右)侧的第"+(7-j)+"个教室";
                            else if(j==7)
                                s = "请先从西侧楼梯走"+(i-2)+"层到第"+i+"层楼\n" +
                                        "请您前往东(左)侧的第1个教室";
                            else
                                s = "请先从西侧楼梯走"+(i-2)+"层到第"+i+"层楼\n" +
                                        "请您前往西(右)侧的第"+(j-7)+"个教室";
                        }
                        out.setText(s);
                        break;
                    case 4:
                        if(i==3){
                            if(j == 11)
                                s = "请先走东侧楼梯进入B楼，" +
                                        "目的地为您面前的教室";
                            else if(j==10)
                                s = "请先走东侧楼梯进入B楼，" +
                                        "请您前往东(左)侧第一个教室";
                            else
                                s = "请先从西侧入口进入B楼，" +
                                        "请您前往西(前)侧的第"+(j-12)+"个教室";
                        }
                        else {
                            String s1 = "请先从西侧入口进入B楼\n然后坐电梯，按第"+(i-2)+"层按钮到达第"+i+"层楼\n";
                            if(j < 13)
                                s = s1 + "请您前往东(左)侧的第"+(13-j)+"个教室";
                            else if(j == 13)
                                s = s1 + "目的地为您面前的教室";
                            else
                                s = s1 + "请您前往西(右)侧的第1个教室";
                        }
                        out.setText(s);
                        break;
                    case 5:
                        if(i==4)
                            s = "请您前往西向东数第"+(17-j)+"间教室";
                        else
                            s = "请先走"+(i-4)+"层楼梯到第"+i+"层楼\n" +
                                    "请您前往西向东数第"+(17-j)+"间教室";
                        out.setText(s);
                        break;
                }
            }
        }
    }
}
