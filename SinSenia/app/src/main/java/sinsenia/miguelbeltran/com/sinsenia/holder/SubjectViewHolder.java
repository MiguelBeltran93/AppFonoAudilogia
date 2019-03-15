package sinsenia.miguelbeltran.com.sinsenia.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sinsenia.miguelbeltran.com.sinsenia.R;

public class SubjectViewHolder extends ViewHolder {

    @BindView(R.id.textViewsubject)
    TextView nameSubject;

    @BindView(R.id.nameteacher)
    TextView nameTeacher;

    ConstraintLayout card;

    public SubjectViewHolder(View itemView) {
        super(itemView);
        nameSubject = itemView.findViewById(R.id.textViewsubject);
        nameTeacher = itemView.findViewById(R.id.nameteacher);
        card = itemView.findViewById(R.id.cardSubject);

    }

    public TextView getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(TextView nameSubject) {
        this.nameSubject = nameSubject;
    }

    public TextView getNameTeacher() {
        return nameTeacher;
    }

    public void setNameTeacher(TextView nameTeacher) {
        this.nameTeacher = nameTeacher;
    }

    public ConstraintLayout getCard() {
        return card;
    }

    public void setCard(ConstraintLayout card) {
        this.card = card;
    }
}
