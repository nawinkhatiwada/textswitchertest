package com.androidbolts.textanim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.earnView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EarnView) findViewById(R.id.earnView)).reset();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((EarnView)findViewById(R.id.earnView)).setText("Earn $0.5","Earned");
        ((EarnView)findViewById(R.id.earnView)).start();
    }
}
