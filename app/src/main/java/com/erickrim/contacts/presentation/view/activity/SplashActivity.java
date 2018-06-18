package com.erickrim.contacts.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.erickrim.contacts.R;

public class SplashActivity extends AppCompatActivity {

    Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_activity);
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    redirect();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashActivity.this.finish();
                }

            }
        };
        splashTread.start();
    }

    private void redirect() {
        Intent intent = new Intent(SplashActivity.this, ContactsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.overridePendingTransition(R.anim.slide_in_from_left, R.anim.exit_to_right);
        SplashActivity.this.finish(); // end activity
    }
}
