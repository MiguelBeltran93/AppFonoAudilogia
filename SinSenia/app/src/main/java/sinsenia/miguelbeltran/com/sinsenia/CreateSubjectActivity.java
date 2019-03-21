package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sinsenia.miguelbeltran.com.sinsenia.models.SalaSubject;
import sinsenia.miguelbeltran.com.sinsenia.models.SinSenaApp;
import sinsenia.miguelbeltran.com.sinsenia.models.Subject;
import sinsenia.miguelbeltran.com.sinsenia.network.RestAPI;

public class CreateSubjectActivity extends AppCompatActivity {

    @BindView(R.id.textView4)
    EditText nameSUbject;
    @BindView(R.id.nombreProfesor)
    EditText nameTeacher;

    private FirebaseDatabase database;
    private DatabaseReference databaseReferenceSubjects;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_subject);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        databaseReferenceSubjects = database.getReference("Materias");
    }

    public void viewListSubject(){
        onBackPressed();
    }

    public void CreateSubject(View button){
        String nombre=nameSUbject.getText().toString();
        String nombreProfesor=nameTeacher.getText().toString();
        SalaSubject sala= new SalaSubject();
        sala.setNameSubject(nombre);
        sala.setNameTearcher(nombreProfesor);
        databaseReferenceSubjects.push().setValue(sala);
        alert();

/*
        RestAPI.getInstance().createSubject(SinSenaApp.getInstance().getID(),nombre,colorMateria,emailP).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    alert();

                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"fallo Registrado",Toast.LENGTH_LONG).show();
            }
        });*/
    }

    public void alert(){
        View v = LayoutInflater.from(this).inflate(R.layout.alert_view, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();


        TextView btnVale =  v.findViewById(R.id.btnVale);
        ImageView btnCancel =  v.findViewById(R.id.btnCancel);

        TextView titleAlert = v.findViewById(R.id.titleAlert);
        titleAlert.setText("Registro Exitoso");
        TextView description =  v.findViewById(R.id.descriptionAlert);
        description.setText("Materia creada\n exitosamente.");

        btnVale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewListSubject();
                alertDialog.dismiss();
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

    public void alertIncorrectInfo(){
        View v = LayoutInflater.from(this).inflate(R.layout.alert_view, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();


        TextView btnVale =  v.findViewById(R.id.btnVale);
        ImageView btnCancel =  v.findViewById(R.id.btnCancel);

        TextView titleAlert = v.findViewById(R.id.titleAlert);
        titleAlert.setText("Error");
        TextView description =  v.findViewById(R.id.descriptionAlert);
        description.setText("Error\n datos incorrectos.");

        btnVale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alertDialog.dismiss();
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

    public void onBack(View button){
        onBackPressed();

    }
}
