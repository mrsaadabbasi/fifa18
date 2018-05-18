package com.example.itx_m.fifaworldcup2018;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.itx_m.fifaworldcup2018.R;

public class CtSplash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    Intent startingPoint = new Intent("android.intent.action.CTMAINMENU");
                    startActivity(startingPoint);
                    overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
