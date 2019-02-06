package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import sinsenia.miguelbeltran.com.sinsenia.adapter.ListSubjectAdapter;
import sinsenia.miguelbeltran.com.sinsenia.models.Subject;

public class ListSubjectActivity extends AppCompatActivity {

    private ArrayList<Object> listSubject;
    private RecyclerView rvListSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subject);

        loadSubject();
        loadRecicler();
        loadInfo();
    }

    public  void loadSubject(){
        listSubject = new ArrayList<>();
        listSubject.add(new Subject("1","Calculo"));
        listSubject.add(new Subject("1" ,"Calculo"));
        listSubject.add(new Subject("1","Calculo"));
        listSubject.add(new Subject("1","Calculo"));
        listSubject.add(new Subject("1","Calculo"));
        listSubject.add(new Subject("1","Calculo"));
        listSubject.add(new Subject("1","Calculo"));

    }

    public void loadRecicler(){
        rvListSubject = (RecyclerView) findViewById(R.id.recyclerViewListas);
        rvListSubject.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListSubject.setLayoutManager(linearLayoutManager);
    }

    public void loadInfo(){

        ListSubjectAdapter adapterListasBuses = new ListSubjectAdapter(listSubject);
        rvListSubject.setAdapter(adapterListasBuses);
    }

    public void addSubject(View button){
        Intent viewAddSubject = new Intent(getApplicationContext(), CreateSubjectActivity.class);

        startActivity(viewAddSubject);
    }

    public void onBack(View button){
        onBackPressed();
    }
}
