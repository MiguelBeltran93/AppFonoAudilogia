package sinsenia.miguelbeltran.com.sinsenia.network;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sinsenia.miguelbeltran.com.sinsenia.models.Message;
import sinsenia.miguelbeltran.com.sinsenia.models.User;

public interface SinSenaRestAPI {


    @GET("wsUser.php")
    Call<User> getUser(@Query("ID_USUARIO") String id);


    @GET("wsCrearusuario.php?")
    Call<String> registerUser(@Query("nombre_usuario") String nombre,
                                       @Query("contrasena") String contrasena,
                                       @Query("rol") String rol,
                                       @Query("correo") String correo
    );

    @GET("wsCrearMateria.php")
    Call<String> createSubject(@Query("id_usuario") String idUser,
                              @Query("nombre_materia") String nombre,
                              @Query("color") String color,
                              @Query("correo_profesor") String correoProfesor
    );

    @GET("wsMATERIAS.php")
    Call<Responses.Materia> getMateria(@Query("ID_USUARIO") String id);


    @GET("wsLogin.php")
    Call<Message> login(@Query("CORREO") String email, @Query("CONTRASENA") String password);

}
