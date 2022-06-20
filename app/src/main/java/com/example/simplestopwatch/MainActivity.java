package com.example.simplestopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button startBtn;
    LinearLayout layout, layout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView timeTv = (TextView)findViewById(R.id.timeTv);

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
}