package sinsenia.miguelbeltran.com.sinsenia.network;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import sinsenia.miguelbeltran.com.sinsenia.models.Subject;

public class Responses {

    public static class User{

        private sinsenia.miguelbeltran.com.sinsenia.models.User user;

        public sinsenia.miguelbeltran.com.sinsenia.models.User getUser(){
            return user;
        }
    }

    public static  class Materia{
        @SerializedName("materias")
        public ArrayList<Subject> materia;

        public ArrayList<Subject> getMaterias(){
            return materia;
        }
    }
}
