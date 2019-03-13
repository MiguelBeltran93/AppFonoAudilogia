package sinsenia.miguelbeltran.com.sinsenia;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import sinsenia.miguelbeltran.com.sinsenia.models.Manager;

public class TeacherFeedbackActivity extends AppCompatActivity {

    private static final int RECOGNIZER_CODE=100;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    @BindView(R.id.txtReciveMessage)
    TextView reciveMesagge;
    @BindView(R.id.textViewLesson)
    TextView titleLesson;
    @BindView(R.id.message)
    TextView sendMessge;

    Manager manager= null;
    Handler handler=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_feedback);
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat");//Sala de chat (nombre)
        manager = new Manager();
        manager.init(this);
        int option = getIntent().getExtras().getInt("option");
        if(option==1){

            titleLesson.setText("Dialogar");
        }
        if(option==3){
            titleLesson.setText("Clase");
        }

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                reciveMesagge.setText("");
                String message = (String) dataSnapshot.getValue();
                String aux = s;
                reciveMesagge.setText(message);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void run(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"kkakaka",Toast.LENGTH_LONG).show();
            }
        },5000);

    }

    public void escucharMessage(View button){

        String message = reciveMesagge.getText().toString();

            manager.initQueue(message);
            reciveMesagge.setText("");
        run();
    }

    public void sendMessage(View button){
        String message = sendMessge.getText().toString();
        databaseReference.push().setValue(message);
        sendMessge.setText("");
    }

    public void onBack(View button){
        Intent viewMenu = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(viewMenu);
        finish();
    }

    public void voiceToText(View button){

            Intent recibirVoice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            recibirVoice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            recibirVoice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            recibirVoice.putExtra(RecognizerIntent.EXTRA_PROMPT, "Escuchando...");
            try {
                startActivityForResult(recibirVoice, RECOGNIZER_CODE);
            } catch (ActivityNotFoundException e) {

            }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RECOGNIZER_CODE:{
                if (resultCode==RESULT_OK&&data!=null){
                    ArrayList<String> info=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                 sendMessge.setText(info.get(0));
                }
                break;
            }
        }
    }
}
