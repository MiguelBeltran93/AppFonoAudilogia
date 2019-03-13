package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LessonTeacherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_teacher);
    }

    public void viewFeedBack(View button){
        Intent viewDialog = new Intent(this, TeacherFeedbackActivity.class);
        viewDialog.putExtra("option",3);
        startActivity(viewDialog);

    }

    public void onBack(View button){
        onBackPressed();
    }
}
