package libertadores.appsolution.com.libertadores.activities;

import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gyros.androutils.AndroUI;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import libertadores.appsolution.com.libertadores.BuildConfig;
import libertadores.appsolution.com.libertadores.R;
import libertadores.appsolution.com.libertadores.models.Libertadores;
import libertadores.appsolution.com.libertadores.models.User;
import libertadores.appsolution.com.libertadores.network.Responses;
import libertadores.appsolution.com.libertadores.network.RestAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginUserActivity extends LIbertdoresActivity  {


    private Button btnRegisterUser;
    private Button btnLogin;

    private EditText emailUser;
    private EditText passwordUser;

    @BindView(R.id.btnOlvidoContraseña)
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this);
        btnRegisterUser = (Button) findViewById(R.id.btnRegistrarme);

        initComponents();
        acctionsButtons();
    }


    private void initComponents() {

        emailUser = (EditText) findViewById(R.id.txtEmail);
        passwordUser = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.inicioSesion);

        if(BuildConfig.DEBUG){
            emailUser.setText("linoalfonso22@gmail.com");
            passwordUser.setText("1234567");
        }

    }

    public void acctionsButtons() {

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewRegisterUser = new Intent(getApplicationContext(), RegisterUserActivity.class);
                startActivity(viewRegisterUser);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void requestPassword(View button){
        Intent viewRequestPassword = new Intent(getApplicationContext(), ForgotPasswordDialogActivity.class);
        startActivity(viewRequestPassword);
    }

    public void login() {
        String email = emailUser.getText().toString();
        String password = passwordUser.getText().toString();
        AndroUI.getInstance().startProgressDialog(this);
        RestAPI.getInstance().login(email,password).enqueue(new Callback<Responses.Login>() {
            @Override
            public void onResponse(Call<Responses.Login> call, Response<Responses.Login> response) {
                AndroUI.getInstance().stopProgressDialog();
                if (response.isSuccessful()) {
                    User user = response.body().getUser();
                    String token = response.body().getToken();
                    Libertadores.getInstance().setUserToken(getApplicationContext(),user,token);
                    finish();

                } else {
                    if (response.code() == 403) {
                        Toast.makeText(getApplicationContext(),"datos erroneos",Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<Responses.Login> call, Throwable t) {
                AndroUI.getInstance().stopProgressDialog();
                Log.e("Error",call.toString());
            }
        });
    }

    private void SendUser(){

        Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
        intent.putExtra("user",Libertadores.getInstance().getUser());
        startActivity(intent);

    }

}