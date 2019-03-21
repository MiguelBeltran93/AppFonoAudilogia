package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sinsenia.miguelbeltran.com.sinsenia.models.Message;
import sinsenia.miguelbeltran.com.sinsenia.models.SinSenaApp;
import sinsenia.miguelbeltran.com.sinsenia.models.User;
import sinsenia.miguelbeltran.com.sinsenia.network.RestAPI;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty(messageResId = R.string.email_empty)
    @BindView(R.id.userEmailLogin)
    EditText email;

    @NotEmpty(messageResId = R.string.password_empity)
    @BindView(R.id.passwordUserLogin)
    EditText password;

    private FirebaseAuth mAuth;
    private String correo;
    private String contrasena;


    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    public void login(View button){
        validator.validate();
    }

    public void loginFirebase(){
        mAuth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent viewMenu = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(viewMenu);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void inicioSesion(){

        correo = email.getText().toString();
        contrasena= password.getText().toString();

        RestAPI.getInstance().login(correo,contrasena).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {

                if (response.isSuccessful()) {
                   Message idUser=response.body();
                        loginFirebase();
                        SinSenaApp.getInstance().setToken(getApplicationContext(),idUser.getMessage(),idUser.getRol());

                }
            }
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
               // Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                alertLoginIncorrectInfo();
                Log.e("Error",call.toString());
            }
        });


    }

    public void alertResetPassword(View button){
        View v = LayoutInflater.from(this).inflate(R.layout.alert_reset_password, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();


        TextView btnVale =  v.findViewById(R.id.btnVale);
        ImageView btnCancel =  v.findViewById(R.id.btnCancel);
        final EditText email = v.findViewById(R.id.emailResetPassword);

        btnVale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser=email.getText().toString();
                mAuth.getInstance().sendPasswordResetEmail(emailUser)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    alertDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),
                                            "Se ha enviado un enlace a su cuenta de correo", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),
                                            "Erro al enviar el mensaje", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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

    public void alertLoginIncorrectInfo(){
        View v = LayoutInflater.from(this).inflate(R.layout.alert_view, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();


        TextView btnVale =  v.findViewById(R.id.btnVale);
        ImageView btnCancel =  v.findViewById(R.id.btnCancel);

        TextView titleAlert = v.findViewById(R.id.titleAlert);
        titleAlert.setText("Error Al Ingresar");
        TextView description =  v.findViewById(R.id.descriptionAlert);
        description.setText("Error al iniciar Sesion \n datos incorrectos.");

        btnVale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public void register(View button){
        Intent viewRegister = new Intent(getApplicationContext(), RegisterUserActivity.class);
        viewRegister.putExtra("option", 0);
        startActivity(viewRegister);
    }

    public void close(View button){
        alertClose();
    }

    public void alertClose(){
        View v = LayoutInflater.from(this).inflate(R.layout.alert_view, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();


        TextView btnVale =  v.findViewById(R.id.btnVale);
        ImageView btnCancel =  v.findViewById(R.id.btnCancel);

        TextView titleAlert = v.findViewById(R.id.titleAlert);
        titleAlert.setText("Salir?");
        TextView description =  v.findViewById(R.id.descriptionAlert);
        description.setText("Esta seguro que desea salir?");

        btnVale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onValidationSucceeded() {
        if(password.getText().toString().length() < 6){

            Toast.makeText(getApplicationContext(),"La contraseña debe ser mayor a 6 caracteres",Toast.LENGTH_LONG).show();
        }
        else{
            inicioSesion();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toast.makeText(getApplicationContext(),errors.get(0).getCollatedErrorMessage(this),Toast.LENGTH_LONG).show();
    }
    }

