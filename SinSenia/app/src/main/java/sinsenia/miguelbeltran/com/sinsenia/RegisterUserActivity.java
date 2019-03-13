package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sinsenia.miguelbeltran.com.sinsenia.models.SinSenaApp;
import sinsenia.miguelbeltran.com.sinsenia.models.User;
import sinsenia.miguelbeltran.com.sinsenia.network.Responses;
import sinsenia.miguelbeltran.com.sinsenia.network.RestAPI;

public class RegisterUserActivity extends AppCompatActivity {

    @BindView(R.id.nameUser)
    EditText name;
    @BindView(R.id.correoUser)
    EditText email;
    @BindView(R.id.passwordUser)
    EditText password;
    @BindView(R.id.Estudiante)
    Switch rolEstudent;
    @BindView(R.id.switch2)
    Switch rolTeacher;

    private FirebaseAuth mAuth;


    private User user;
    private String emailUSer;
    private String passwordUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);

        int option = getIntent().getExtras().getInt("option");
        if(option==1){
            TextView btnRegistro= findViewById(R.id.buttonRegistro);
            btnRegistro.setVisibility(View.GONE);

            TextView txtPerfil= findViewById(R.id.textPerfil);
            txtPerfil.setText("Perfil");

            TextInputLayout password =findViewById(R.id.textLayoutPasswordRegistro);
            password.setVisibility(View.GONE);

            TextInputLayout passwordCon =findViewById(R.id.textInputLayoutRegistro);
            passwordCon.setVisibility(View.GONE);
            loadProfile();
        }
    }

    public void registerUserFirebase(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            alertLogin();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),"Error al Registrarse",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }



    public void resgisterUser(View button){
        String n=name.getText().toString();
        passwordUser=password.getText().toString();
       String r=null;
        emailUSer=email.getText().toString();

        if (rolEstudent.isChecked()){
            r="Estudiante";
        }else if(rolTeacher.isChecked()){
            r="Profesor";
        }
        RestAPI.getInstance().registerUser(n,passwordUser,r,emailUSer).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                   registerUserFirebase(emailUSer,passwordUser);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"fallo Registrado",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadProfile(){

        RestAPI.getInstance().getUser(SinSenaApp.getInstance().getID()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    user = response.body();
                    name.setText(user.getNameUser());
                    email.setText(user.getEmail());
                    name.setEnabled(false);
                    email.setEnabled(false);
                    if (user.getRol().equals("Estudiante")){

                        rolEstudent.setChecked(true);
                        rolEstudent.setClickable(false);
                        rolTeacher.setClickable(false);
                    }else if (user.getRol().equals("Profesor")){
                        rolTeacher.setChecked(true);
                        rolEstudent.setClickable(false);
                        rolTeacher.setClickable(false);
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"fallo.........",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onBackPresed(View button){
      onBackPressed();
    }


    public void alertLogin(){
        View v = LayoutInflater.from(this).inflate(R.layout.alert_view, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();


         TextView btnVale =  v.findViewById(R.id.btnVale);
         ImageView btnCancel =  v.findViewById(R.id.btnCancel);

         TextView titleAlert = v.findViewById(R.id.titleAlert);
         titleAlert.setText("Bienvenido");
        TextView description =  v.findViewById(R.id.descriptionAlert);
        description.setText("Su registro ha sido exitoso");

        btnVale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
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
