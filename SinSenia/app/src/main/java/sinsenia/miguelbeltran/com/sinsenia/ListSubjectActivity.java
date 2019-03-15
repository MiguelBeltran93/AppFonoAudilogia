package sinsenia.miguelbeltran.com.sinsenia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sinsenia.miguelbeltran.com.sinsenia.adapter.ListSubjectAdapter;
import sinsenia.miguelbeltran.com.sinsenia.holder.SubjectViewHolder;
import sinsenia.miguelbeltran.com.sinsenia.models.SalaSubject;
import sinsenia.miguelbeltran.com.sinsenia.models.SinSenaApp;
import sinsenia.miguelbeltran.com.sinsenia.models.Subject;
import sinsenia.miguelbeltran.com.sinsenia.models.User;
import sinsenia.miguelbeltran.com.sinsenia.network.Responses;
import sinsenia.miguelbeltran.com.sinsenia.network.RestAPI;

public class ListSubjectActivity extends AppCompatActivity {

    ArrayList<Subject> listSubject;
    @BindView(R.id.recyclerViewListas)
    RecyclerView rvListSubject;

    @BindView(R.id.addSubjectIcon)
    ImageView addSubjectIcon;

    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subject);
        ButterKnife.bind(this);
        int rol = getIntent().getExtras().getInt("rol");
        if (rol == 1) {
            addSubjectIcon.setVisibility(View.GONE);
        }

        loadRecicler();

    }

    public void loadSubject() {

        RestAPI.getInstance().getMaterias(SinSenaApp.getInstance().getID()).enqueue(new Callback<Responses.Materia>() {
            @Override
            public void onResponse(Call<Responses.Materia> call, Response<Responses.Materia> response) {

                if (response.isSuccessful()) {
                    listSubject = response.body().getMaterias();
                    loadRecicler();
                }
            }

            @Override
            public void onFailure(Call<Responses.Materia> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "fallo.........", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void loadRecicler() {

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Materias");
        FirebaseRecyclerOptions<SalaSubject> subjects =
                new FirebaseRecyclerOptions.Builder<SalaSubject>()
                        .setQuery(query, SalaSubject.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<SalaSubject, SubjectViewHolder>(subjects) {
            @Override
            public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_subject, parent, false);
                return new SubjectViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(SubjectViewHolder holder, int position, SalaSubject model) {
                holder.getNameSubject().setText(model.getNameSubject());
                holder.getNameTeacher().setText(model.getNameTearcher());
                final String aux = getSnapshots().getSnapshot(position).getKey();
                holder.getCard().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),aux, Toast.LENGTH_SHORT).
                        show();
                        String rol=SinSenaApp.getInstance().getROL();
                        if(rol.equals("Estudiante")){
                            Intent viewClass= new Intent(v.getContext(), LessonsActivity.class);
                            viewClass.putExtra("option",0);
                            viewClass.putExtra("key_subject",aux);
                            startActivity(viewClass);
                        }else if(rol.equals("Profesor")){
                            Intent viewClass= new Intent(v.getContext(), TeacherFeedbackActivity.class);
                            viewClass.putExtra("option",3);
                            viewClass.putExtra("key_subject",aux);
                            startActivity(viewClass);
                        }

                    }
                });


            }
        };
        rvListSubject.setAdapter(adapter);
    }

    public void loadInfo() {


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void addSubject(View button) {
        Intent viewAddSubject = new Intent(getApplicationContext(), CreateSubjectActivity.class);

        startActivity(viewAddSubject);
    }

    public void onBack(View button) {
        onBackPressed();
    }
}
