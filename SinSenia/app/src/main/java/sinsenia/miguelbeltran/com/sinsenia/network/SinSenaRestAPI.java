package sinsenia.miguelbeltran.com.sinsenia.network;


import retrofit2.Call;
import retrofit2.http.GET;

public interface SinSenaRestAPI {


    @GET("wsUSUARIO.php")
    Call<Responses.User> getUser();
}
