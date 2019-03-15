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

import java.io.IOException;

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

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.userEmailLogin)
    EditText email;
    @BindView(R.id.passwordUserLogin)
    EditText password;

    private FirebaseAuth mAuth;
    private String correo;
    private String contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        if(BuildConfig.DEBUG){
            email.setText("miguel@gmail.com");
            password.setText("123456");
        }

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

    public void inicioSesion(View button){

        correo = email.getText().toString();
        contrasena= password.getText().toString();

        RestAPI.getInstance().login(correo,contrasena).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {

                if (response.isSuccessful()) {
                   Message idUser=response.body();
                    Toast.makeText(getApplicationContext(),idUser.getMessage(),Toast.LENGTH_SHORT).show();
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
}
