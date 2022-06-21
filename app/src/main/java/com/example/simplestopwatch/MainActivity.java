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

    private Thread timeThread = null;
    private Boolean isRunning = true;

    TextView timeTv;
    Button startBtn;
    LinearLayout layout, layout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTv = (TextView)findViewById(R.id.timeTv);

        startBtn = (Button)findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        layout = (LinearLayout)findViewById(R.id.layout);

        Button recordBtn = (Button)findViewById(R.id.recordBtn);

        Button stopBtn = (Button)findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        layout2 = (LinearLayout)findViewById(R.id.layout2);

        Button resetBtn = (Button)findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        Button restartBtn = (Button)findViewById(R.id.restartBtn);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartTimer();
            }
        });
    }

    public void startTimer() {
        startBtn.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);

        // 스레드를 생성해서 실행함
        timeThread = new Thread(new TimeThread());
        timeThread.start();
    }

    public void stopTimer() {
        layout.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);
    }

    public void restartTimer() {
        layout2.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
    }

    public void resetTimer() {
        layout2.setVisibility(View.GONE);
        startBtn.setVisibility(View.VISIBLE);
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
            int i = 0;

            while(true) {
                while(isRunning) {
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // runOnUiThread() : Runnable 객체에 구현된 코드를 반드시 메인 스레드에서 실행해야할 때 사용하는 메서드
                        runOnUiThread(new Runnable() { // Runnable 객체를 메인 스레드에서 실행되도록 만드는 메서드로서, 현재 스레드가
                            // 메인 스레드인지 여부를 검사하여 메인 스레드가 아니라면 post()를 실행하고, 메인 스레드라면 Runnable의 run()을 실행함
                            @Override
                            public void run() {
                                timeTv.setText("");
                                timeTv.setText("00:00:00.00");
                            }
                        });
                        return;
                    }
                }
            }
        }
    }
}