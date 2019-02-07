package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sinsenia.miguelbeltran.com.sinsenia.models.User;
import sinsenia.miguelbeltran.com.sinsenia.network.Responses;
import sinsenia.miguelbeltran.com.sinsenia.network.RestAPI;

public class RegisterUserActivity extends AppCompatActivity {

    @BindView(R.id.nameUser)
    TextView name;
    @BindView(R.id.correoUser)
    TextView email;
    @BindView(R.id.Estudiante)
    Switch rolEstudent;
    @BindView(R.id.switch2)
    Switch rolTeacher;


    private ArrayList<User> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

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

    public void loadProfile(){

        RestAPI.getInstance().getUser().enqueue(new Callback<Responses.User>() {
            @Override
            public void onResponse(Call<Responses.User> call, Response<Responses.User> response) {

                if(response.isSuccessful()){
                    user = response.body().getUser();
                    name.setText(user.get(0).getNameUser());
                    name.setEnabled(false);
                    email.setText(user.get(0).getEmail());
                    email.setEnabled(false);
                    if (user.get(0).getRol().equals("Estudiante")){

                        rolEstudent.setChecked(true);
                        rolEstudent.setClickable(false);
                        rolTeacher.setClickable(false);
                    }
                    Toast.makeText(getApplicationContext(),user.get(0).getRol(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Responses.User> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"fallo.........",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onBackPresed(View button){
      onBackPressed();
    }

    public void showAlert(View button){
        alertLogin();
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
