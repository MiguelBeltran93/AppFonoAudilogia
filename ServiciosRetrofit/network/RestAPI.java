package libertadores.appsolution.com.libertadores.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import libertadores.appsolution.com.libertadores.BuildConfig;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPI implements Serializable {
    private static String HOST_URL = BuildConfig.HOST;
    public String tokenAuth = "";
    public static RestAPI instance;

    private LibertadoresRestAPI api;
    private Retrofit retrofit;

    public RestAPI(){

        Gson gsonSettings = getGsonSettings();
        OkHttpClient clientHttp = getOkHttpClientSettings();
        retrofit = getRetrofitSettings(gsonSettings,clientHttp);
        api = retrofit.create(LibertadoresRestAPI.class);

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

    public Call<Responses.Login> login(String email, String password){
        return api.login(email,password);
    }
    public static MultipartBody.Part getMultiPartBodyFromFile(String name,File file){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData(name, "image.jpg", requestFile);
        return body;
    }

    public Call<Responses.Login> registerUser(String dni,String name, String email
    ,String password, String phone,String city,String address,File fileImage, String urlImage){
        if(fileImage != null){
            MultipartBody.Part bodyImage = getMultiPartBodyFromFile("photo",fileImage);
            return api.registerUser(dni, name, email, password, phone, city, address, bodyImage);
        }
        else{
            return api.registerUser(dni, name, email, password, phone, city, address, urlImage);
        }
    }

    public Call<Responses.User> getUser(String token){
        return api.getUser(token);
    }

    public Call<Responses.Login> updateUser(String dni,String name, String email
           , String phone,String city,String address,File fileImage, String urlImage){
        if(fileImage != null){
            MultipartBody.Part bodyImage = getMultiPartBodyFromFile("photo",fileImage);
            return api.updateUser(tokenAuth,dni, name, email, phone, city, address, bodyImage);
        }
        else{
            return api.updateUser(tokenAuth,dni, name, email, phone, city, address, urlImage);
        }
    }


    public Call<Responses.News> getNews(){
        return api.getNews();
    }

    public Call<Responses.Services> getServices(){
        return api.getServices();
    }
    public Call<Responses.City> getCity(){
        return api.getCity();
    }

    public Call<Responses.Message> logout(){
        return api.logout(tokenAuth);
    }

    public Call<Responses.Message> changePassword(String oldPassword,String newPassword){
        return api.changePassword(tokenAuth,oldPassword, newPassword);
    }
    public Call<Responses.Message> recoveryPassword(String email){
        return api.recoveryPassword(email);
    }

}
