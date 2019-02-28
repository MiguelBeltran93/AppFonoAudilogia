package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if(BuildConfig.DEBUG){
            email.setText("miguel.beltran01");
            password.setText("pruebanuevo");
        }

    }

    public void inicioSesion(View button){

        String correo = email.getText().toString();
        String contrasena= password.getText().toString();

        RestAPI.getInstance().login(correo,contrasena).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {

                if (response.isSuccessful()) {
                   Message idUser=response.body();
                    Toast.makeText(getApplicationContext(),idUser.getMessage(),Toast.LENGTH_SHORT).show();
                        Intent viewMenu = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(viewMenu);
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


}
