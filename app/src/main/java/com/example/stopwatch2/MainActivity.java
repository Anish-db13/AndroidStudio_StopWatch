package com.example.stopwatch2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnStart,btnPause,btnReset;
    TextView txtTimer;
    Handler customHandler = new Handler();
    LinearLayout container;
    long tStart=0L,tMill=0L,tBuff=0L,update=0L;
    int sec,min,milliSec;

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMill = SystemClock.uptimeMillis() - tStart;
            update = tBuff + tMill;
            sec = (int) (update / 1000);
            min = sec / 60;
            sec = sec % 60;
            milliSec = (int) (update % 100);
            txtTimer.setText(String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" + String.format("%02d", milliSec));
            customHandler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnReset = (Button) findViewById(R.id.btnReset);
        txtTimer = (TextView) findViewById(R.id.timerValue);
        container = (LinearLayout) findViewById(R.id.container);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tStart = SystemClock.uptimeMillis();
                customHandler.postDelayed(runnable,0);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tBuff += tMill;
                customHandler.removeCallbacks(runnable);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tMill = 0L;
                tStart = 0L;
                tBuff = 0L;
                update = 0L;
                sec = 0;
                milliSec = 0;
                min = 0;

                txtTimer.setText("00:00:00");

            }
        });

    }
}