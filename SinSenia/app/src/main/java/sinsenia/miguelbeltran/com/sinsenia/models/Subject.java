package sinsenia.miguelbeltran.com.sinsenia.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Subject implements Parcelable {
    @SerializedName("ID_MATERIA")
    private String idSubject;
    @SerializedName("NOMBRE_MATERIA")
    private String nameSubject;
    @SerializedName("CORREO_PROFESOR")
    private String correoProfesro;
    @SerializedName("COLOR")
    private String color;


    protected Subject(Parcel in) {
        idSubject = in.readString();
        nameSubject = in.readString();
        correoProfesro = in.readString();
        color = in.readString();
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public String getCorreoProfesro() {
        return correoProfesro;
    }

    public void setCorreoProfesro(String correoProfesro) {
        this.correoProfesro = correoProfesro;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idSubject);
        dest.writeString(nameSubject);
        dest.writeString(correoProfesro);
        dest.writeString(color);
    }

    public Subject() {
    }
}
