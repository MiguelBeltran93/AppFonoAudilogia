package sinsenia.miguelbeltran.com.sinsenia.models;

import android.content.Context;

import sinsenia.miguelbeltran.com.sinsenia.network.RestAPI;

public class SinSenaApp {

    public static SinSenaApp instance;

    private String ID = null;
    private String ROL = null;
    private User user;



    public User getUser() {

        return user;
    }

    public boolean isLogged(){
        return user!=null;
    }

    public void setToken(Context context, String id,String rol) {
        this.ID = id;
        this.ROL=rol;
        RestAPI.getInstance().ID= id;

    }

    public void setUserToken(Context context, User user, String id,String rol){
        setUser(user);
        setToken(context,id,rol);
    }

    public void logout(Context context){
        setUserToken(context,null,null,null);

    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return ID;
    }

    public static SinSenaApp getInstance(){
        if(instance==null){
            instance = new SinSenaApp();

        }
        return instance;
    }

    public static void setInstance(SinSenaApp appObject){
        instance = appObject;
        if(instance!=null && instance.getToken()!=null){
            RestAPI.getInstance().ID = instance.getToken();
        }
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getROL() {
        return ROL;
    }

    public void setROL(String ROL) {
        this.ROL = ROL;
    }
}
