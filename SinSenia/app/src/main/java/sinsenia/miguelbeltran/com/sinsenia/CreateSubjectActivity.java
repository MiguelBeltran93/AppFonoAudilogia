package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateSubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_subject);
    }

    public void viewListSubject(View button){
        Intent viewList = new Intent(getApplicationContext(), ListSubjectActivity.class);
        startActivity(viewList);
        finish();
    }

    public void onBack(View button){
        onBackPressed();

    }
}
