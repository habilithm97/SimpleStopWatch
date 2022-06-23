package com.example.simplestopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    int mainButtonCount = 0;
    int i = 0;

    TextView timeTv;

    Button subBtn, mainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTv = (TextView)findViewById(R.id.timeTv);

        subBtn = (Button)findViewById(R.id.subBtn);
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subBtn.getText().toString().equals("기록")) {
                    recordingTime();
                } else if(subBtn.getText().toString().equals("초기화")) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("초기화");
                    builder.setMessage("시간을 초기화하시겠습니까?");
                    builder.setIcon(R.drawable.timer);
                    builder.setPositiveButton("초기화", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clearTime();
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }
        });

        mainBtn = (Button)findViewById(R.id.mainBtn);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainButtonCount == 0) { // 버튼을 누르지 않은 상태면 스레드 실행
                    startTimer();
                } else { // 버튼을 누른 상태면 스레드 일시정지
                    PauseAndRestartTimer();
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
        mainButtonCount++; // 버튼을 누른 상태 1
    }

    public void PauseAndRestartTimer() {
        subBtn.setText("초기화");
        mainBtn.setText("계속");

        isRunning = !isRunning; // 스레드를 멈춤
        mainButtonCount--; // 버튼을 누르지 않은 상태 0
    }

    public void recordingTime() {
        Toast.makeText(getApplicationContext(), "기록", Toast.LENGTH_SHORT).show();
    }

    public void clearTime() {
        i = 0;
        timeTv.setText("00:00:00.00");
        subBtn.setVisibility(View.GONE);
        mainBtn.setText("시작");
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