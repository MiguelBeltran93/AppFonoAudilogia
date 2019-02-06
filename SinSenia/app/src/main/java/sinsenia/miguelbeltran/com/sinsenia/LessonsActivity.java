package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LessonsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

       int option = getIntent().getExtras().getInt("option");
       if(option==1){
           TextView txt= findViewById(R.id.textViewLesson);
           txt.setText("Dialogar");
       }
    }

    public void viewMenu(View button){
        Intent viewDialog = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(viewDialog);
    }
    public void onBack(View button){
       onBackPressed();
       finish();
    }
}
