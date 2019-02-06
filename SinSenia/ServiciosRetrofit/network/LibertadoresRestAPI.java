package libertadores.appsolution.com.libertadores.network;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LibertadoresRestAPI {
    @FormUrlEncoded
    @POST("api/auth/login")
    Call<Responses.Login> login(@Field("email") String email, @Field("password") String password);

    @Multipart
    @POST("api/auth/register")
    Call<Responses.Login> registerUser(@Query("dni") String dni,
                                       @Query("name") String name,
                                       @Query("email") String email,
                                       @Query("password") String password,
                                       @Query("phone") String phone,
                                       @Query("city") String city,
                                       @Query("address") String address,
                                       @Part MultipartBody.Part image);
    @FormUrlEncoded
    @POST("api/auth/register")
    Call<Responses.Login> registerUser(@Field("dni") String dni,
                                       @Field("name") String name,
                                       @Field("email") String email,
                                       @Field("password") String password,
                                       @Field("phone") String phone,
                                       @Field("city") String city,
                                       @Field("address") String address,
                                       @Field("photo") String urlImage);

    @GET("api/auth/get_user")
    Call<Responses.User> getUser(@Query("token") String token);

    @Multipart
    @POST("api/users/update")
    Call<Responses.Login> updateUser(@Query("token")String token,
                                       @Query("dni") String dni,
                                       @Query("name") String name,
                                       @Query("email") String email,
                                       @Query("phone") String phone,
                                       @Query("city") String city,
                                       @Query("address") String address,
                                       @Part MultipartBody.Part image);
    @FormUrlEncoded
    @POST("api/users/update")
    Call<Responses.Login> updateUser(@Query("token")String token,
                                       @Field("dni") String dni,
                                       @Field("name") String name,
                                       @Field("email") String email,
                                       @Field("phone") String phone,
                                       @Field("city") String city,
                                       @Field("address") String address,
                                       @Field("photo") String urlImage);

    @GET("api/newses")
    Call<Responses.News> getNews();

    @GET("api/services")
    Call<Responses.Services> getServices();

    @GET("api/get_towns")
    Call<Responses.City> getCity();

    @POST("api/logout")
    Call<Responses.Message> logout(@Query("token") String token);

    @FormUrlEncoded
    @POST("api/users/resetpassword")
    Call<Responses.Message> changePassword(@Query("token") String token,
                                         @Field("old_password") String oldPassword,
                                           @Field("password") String newPassword);
    @FormUrlEncoded
    @POST("api/password/create")
    Call<Responses.Message> recoveryPassword(@Field("email") String email);
}
