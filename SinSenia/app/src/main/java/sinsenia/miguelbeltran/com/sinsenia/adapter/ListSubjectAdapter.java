package sinsenia.miguelbeltran.com.sinsenia.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sinsenia.miguelbeltran.com.sinsenia.LessonsActivity;
import sinsenia.miguelbeltran.com.sinsenia.R;
import sinsenia.miguelbeltran.com.sinsenia.models.Subject;

public class ListSubjectAdapter extends RecyclerView.Adapter<ListSubjectAdapter.ListaViewHolder> {

    private ArrayList<Subject> listInfoSubject;

    @NonNull
    @Override
    public ListSubjectAdapter.ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewListas = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_subject,parent,false);
        ListSubjectAdapter.ListaViewHolder listasViewHolder = new ListSubjectAdapter.ListaViewHolder(viewListas);
        return listasViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListSubjectAdapter.ListaViewHolder holder, int position) {
        if (listInfoSubject.get(position) instanceof Subject) {

            Subject infoSunject = (Subject) listInfoSubject.get(position);
            holder.nameSubject.setText(infoSunject.getNameSubject());

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent viewClass= new Intent(v.getContext(), LessonsActivity.class);
                    viewClass.putExtra("option",0);
                    v.getContext().startActivity(viewClass);
                }
            });
        }

    }

    @Override
    public int getItemCount() {

            return listInfoSubject.size();
    }

    public static class ListaViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout card;
        TextView nameSubject;

        public ListaViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardSubject);
            nameSubject = itemView.findViewById(R.id.textViewsubject);
        }
    }

   public  ListSubjectAdapter(ArrayList<Subject> listInfoSubject){
        this.listInfoSubject = listInfoSubject;
    }
}
