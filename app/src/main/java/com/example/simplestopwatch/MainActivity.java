package com.example.simplestopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Thread timeThread = null;
    boolean isRunning = true;
    int buttonCount = 0;
    int i = 0;

    TextView timeTv;
    Button startBtn;
    LinearLayout layout, layout2;

    Button subBtn, mainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTv = (TextView)findViewById(R.id.timeTv);

        subBtn = (Button)findViewById(R.id.subBtn);

        mainBtn = (Button)findViewById(R.id.mainBtn);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonCount == 0) { // 버튼을 누르지 않은 상태면 스레드 실행
                    startTimer();
                } else { // 버튼을 누른 상태면 스레드 일시정지
                    pauseTimer();
                }
            }
        });
    }

    public void startTimer() {
        subBtn.setVisibility(View.VISIBLE);
        subBtn.setText("기록");
        mainBtn.setText("일시정지");

        // 스레드를 생성해서 실행함
        timeThread = new Thread(new TimeThread());
        timeThread.start();
        isRunning = true; // 실행중인가? -> Yes
        buttonCount++; // 버튼을 누른 상태 1
    }

    public void pauseTimer() {
        subBtn.setText("초기화");
        mainBtn.setText("계속");

        isRunning = !isRunning; // 스레드를 멈춤
        buttonCount--; // 버튼을 누르지 않은 상태 0
    }

    // 핸들러를 이용해서 UI를 변경할 수 있음
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            int mSec = msg.arg1 % 100;
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            int hour = (msg.arg1 / 100) / 360;

           String result = String.format("%02d:%02d:%02d.%02d", hour, min, sec, mSec);
           timeTv.setText(result);
        }
    };

    public class TimeThread implements Runnable {
        @Override
        public void run() {
            while(isRunning) {
                Message msg = new Message();
                msg.arg1 = i++;
                handler.sendMessage(msg);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}