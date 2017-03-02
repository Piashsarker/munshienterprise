package com.dcastalia.android.job_portal.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.dcastalia.android.job_portal.R;

/**
 * Created by nusrat-pc on 12/15/16.
 */
public class Splash_activity extends AppCompatActivity {

    Button btn_sp;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestFullScreenWindow();
        setContentView(R.layout.splash_layout);



        btn_sp=(Button)findViewById(R.id.btn_sp);

        //  Reg button Click Event
        btn_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Splash_activity.this,MainActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                finish();


            }
        });

    }
    private void requestFullScreenWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
