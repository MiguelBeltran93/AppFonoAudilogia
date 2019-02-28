package sinsenia.miguelbeltran.com.sinsenia.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sinsenia.miguelbeltran.com.sinsenia.BuildConfig;
import sinsenia.miguelbeltran.com.sinsenia.models.Message;
import sinsenia.miguelbeltran.com.sinsenia.models.User;

import static okhttp3.internal.Internal.instance;

public class RestAPI {

    private static String HOST_URL = BuildConfig.HOST;

    private SinSenaRestAPI api;
    private Retrofit retrofit;
    public String ID = "";
    public static RestAPI instance;

    public RestAPI(){

        Gson gsonSettings = getGsonSettings();
        OkHttpClient clientHttp = getOkHttpClientSettings();
        retrofit = getRetrofitSettings(gsonSettings,clientHttp);
        api = retrofit.create(SinSenaRestAPI.class);

    }

    private static Gson getGsonSettings(){
        return new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
    }

    private OkHttpClient getOkHttpClientSettings(){
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    private static Retrofit getRetrofitSettings(Gson gsonSettings, OkHttpClient clientSettings){
        return new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonSettings))
                .client(clientSettings)
                .build();
    }

    public static RestAPI getInstance(){
        if(instance == null){
            instance = new RestAPI();
        }
        return instance;
    }


    public Call<User> getUser(String id){
        return api.getUser(id);
    }

    public Call<Responses.Materia> getMaterias(String id){
        return api.getMateria(id);
    }

    public Call<Message> login(String correo, String  password){
        return api.login(correo,password);
    }


    public Call<String> registerUser(String nombreUsuario,
                                             String contrasena,
                                             String rol,
                                             String correo){
        return api.registerUser(nombreUsuario,contrasena,rol,correo);
    }

    public Call<String> createSubject(String idUser,
                                     String nombreMateria,
                                     String color,
                                     String correo){
        return api.createSubject(idUser,nombreMateria,color,correo);
    }

}
