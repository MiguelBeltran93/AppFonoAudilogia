package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        executeSplash();
    }

    private void executeSplash(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToMainActivity();

            }
        },1000);

    }

    private void goToMainActivity(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish ();
    }
}
