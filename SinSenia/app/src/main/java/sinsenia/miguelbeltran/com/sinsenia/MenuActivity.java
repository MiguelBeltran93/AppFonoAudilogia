package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sinsenia.miguelbeltran.com.sinsenia.adapter.ListSubjectAdapter;
import sinsenia.miguelbeltran.com.sinsenia.models.SinSenaApp;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void viewClass(View button){
        String rol=SinSenaApp.getInstance().getROL();
        if (rol.equals("Estudiante")) {
            Intent viewClass = new Intent(getApplicationContext(), ListSubjectActivity.class);
            startActivity(viewClass);
        }else if(rol.equals("Profesor")){
            Intent viewProfesor = new Intent(getApplicationContext(), LessonTeacherActivity.class);
            startActivity(viewProfesor);
        }

    }

    public void viewConversar(View button){
        String rol=SinSenaApp.getInstance().getROL();
        if (rol.equals("Estudiante")) {
            Intent viewDialog = new Intent(getApplicationContext(), LessonsActivity.class);
            viewDialog.putExtra("option", 1);
            startActivity(viewDialog);
        }else if(rol.equals("Profesor")) {
            Intent viewDialog = new Intent(getApplicationContext(), TeacherFeedbackActivity.class);
            viewDialog.putExtra("option", 1);
            startActivity(viewDialog);
        }

    }

    public void viewProfile(View button){
        Intent viewProfile = new Intent(getApplicationContext(), RegisterUserActivity.class);
        viewProfile.putExtra("option", 1);
        startActivity(viewProfile);

    }

    public void closeSesion(View button){
       alertCloseSesion();
    }

   public void viewSetting(View button){
        alert();
    }

    public void alert(){
        View v = LayoutInflater.from(this).inflate(R.layout.alert_view, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();


        TextView btnVale =  v.findViewById(R.id.btnVale);
        ImageView btnCancel =  v.findViewById(R.id.btnCancel);

        TextView titleAlert = v.findViewById(R.id.titleAlert);
        titleAlert.setText("Acerca de");
        TextView description =  v.findViewById(R.id.descriptionAlert);
        description.setText(getText(R.string.acercaDe));

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

    public void alertCloseSesion(){
        View v = LayoutInflater.from(this).inflate(R.layout.alert_view, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();


        TextView btnVale =  v.findViewById(R.id.btnVale);
        ImageView btnCancel =  v.findViewById(R.id.btnCancel);

        TextView titleAlert = v.findViewById(R.id.titleAlert);
        titleAlert.setText("Cerrar Sesion");
        TextView description =  v.findViewById(R.id.descriptionAlert);
        description.setText("Deseas cerrar la sesion?");

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
