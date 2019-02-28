package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sinsenia.miguelbeltran.com.sinsenia.adapter.ListSubjectAdapter;
import sinsenia.miguelbeltran.com.sinsenia.models.SinSenaApp;
import sinsenia.miguelbeltran.com.sinsenia.models.Subject;
import sinsenia.miguelbeltran.com.sinsenia.models.User;
import sinsenia.miguelbeltran.com.sinsenia.network.Responses;
import sinsenia.miguelbeltran.com.sinsenia.network.RestAPI;

public class ListSubjectActivity extends AppCompatActivity {

    private ArrayList<Subject> listSubject;
    private RecyclerView rvListSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subject);

        loadSubject();

    }

    public  void loadSubject(){

        RestAPI.getInstance().getMaterias(SinSenaApp.getInstance().getID()).enqueue(new Callback<Responses.Materia>() {
            @Override
            public void onResponse(Call<Responses.Materia> call, Response<Responses.Materia> response) {

                if(response.isSuccessful()){
                    listSubject = response.body().getMaterias();
                    loadRecicler();
                }
            }

            @Override
            public void onFailure(Call<Responses.Materia> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"fallo.........",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void loadRecicler(){
        rvListSubject = (RecyclerView) findViewById(R.id.recyclerViewListas);
        rvListSubject.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListSubject.setLayoutManager(linearLayoutManager);

        ListSubjectAdapter adapterListasBuses = new ListSubjectAdapter(listSubject);
        rvListSubject.setAdapter(adapterListasBuses);
    }

    public void loadInfo(){


    }

    public void addSubject(View button){
        Intent viewAddSubject = new Intent(getApplicationContext(), CreateSubjectActivity.class);

        startActivity(viewAddSubject);
    }

    public void onBack(View button){
        onBackPressed();
    }
}
