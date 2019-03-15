package sinsenia.miguelbeltran.com.sinsenia;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import sinsenia.miguelbeltran.com.sinsenia.models.MessageSend;
import sinsenia.miguelbeltran.com.sinsenia.models.SinSenaApp;


public class
LessonsActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private static final int RECOGNIZER_CODE = 100;

    @BindView(R.id.textView8)
    TextView textVoice;
    @BindView(R.id.editText2)
    EditText textSend;
    @BindView(R.id.textViewLesson)
    TextView titleText;

    Manager manager = null;
    String keySubject;
    String infoClase="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
        manager = new Manager();
        manager.init(this);
        int option = getIntent().getExtras().getInt("option");
        Bundle bundle = getIntent().getExtras();

        if (!bundle.isEmpty()) {
            keySubject = getIntent().getExtras().getString("key_subject");
            databaseReference = database.getReference("Contenido"+"/"+keySubject);
        }else{
            finish();
        }

        if (option == 1) {
            titleText.setText("Dialogar");
        }


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (titleText.getText().toString() != "Dialogar") {

                        String message = (String) dataSnapshot.getValue();
                        infoClase = infoClase + " " + message;
                        textVoice.setText(infoClase);
                }
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


    public void viewMenu(View button) {
        Intent viewDialog = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(viewDialog);
    }

    public void onBack(View button) {
        onBackPressed();
        finish();
    }

    public void voiceToText(View button) {
        if (titleText.getText().toString() == "Dialogar") {
            Intent recibirVoice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            recibirVoice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            recibirVoice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            recibirVoice.putExtra(RecognizerIntent.EXTRA_PROMPT, "Escuchando...");
            try {
                startActivityForResult(recibirVoice, RECOGNIZER_CODE);
            } catch (ActivityNotFoundException e) {

            }
        }

    }


    public void sendMessage(View button) {
        String message = textSend.getText().toString();
        if (titleText.getText().toString() == "Dialogar") {
            manager.initQueue(message);
            textSend.setText("");
        } else {
            MessageSend send= new MessageSend();
            send.setMessageSend(message);
            databaseReference.push().setValue(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.shutDown();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RECOGNIZER_CODE: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> info = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textVoice.setText(info.get(0));
                }
                break;
            }
        }
    }

    public static void shareInfo(Context ctx,String informacion,String title,String description,String errorShare,String titleChooser){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, title);
            String sAux = "\n"+description+"\n\n";
            sAux = sAux + informacion;
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            ctx.startActivity(Intent.createChooser(i, titleChooser));
        } catch(Exception e) {
            Toast.makeText(ctx,errorShare,Toast.LENGTH_SHORT);
        }
    }

    public void alertCompartir(View button){
        View v = LayoutInflater.from(this).inflate(R.layout.alert_view, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();


        TextView btnVale =  v.findViewById(R.id.btnVale);
        ImageView btnCancel =  v.findViewById(R.id.btnCancel);

        TextView titleAlert = v.findViewById(R.id.titleAlert);
        titleAlert.setText("INFORMACION");
        TextView description =  v.findViewById(R.id.descriptionAlert);
        description.setText("Desea enviar esta clase \n a su correo o compartirla.");

        btnVale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                shareInfo(v.getContext(),infoClase,"Clase",
                        "Resumen","Error al compartir","Compartir");
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(v);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
