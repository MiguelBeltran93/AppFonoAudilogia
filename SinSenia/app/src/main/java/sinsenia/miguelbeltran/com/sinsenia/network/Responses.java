package sinsenia.miguelbeltran.com.sinsenia.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import sinsenia.miguelbeltran.com.sinsenia.models.User;

public class Responses {

    public static class User{
        @SerializedName("usuarios")
        private ArrayList<sinsenia.miguelbeltran.com.sinsenia.models.User> user;

        public ArrayList<sinsenia.miguelbeltran.com.sinsenia.models.User> getUser(){
            return user;
        }
    }
}
