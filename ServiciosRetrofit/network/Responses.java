package libertadores.appsolution.com.libertadores.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import libertadores.appsolution.com.libertadores.models.User;

public class Responses {
    public static class Response{
        @SerializedName("status")
        private String success;

        public String getSuccess() {
            return success;
        }
    }

    public static class Login{

        @SerializedName("data")
        private libertadores.appsolution.com.libertadores.models.User user;

        @SerializedName("token")
        private String token;

        public libertadores.appsolution.com.libertadores.models.User getUser() {
            return user;
        }

        public String getToken() {
            return token;
        }
    }


    public static class User{

        @SerializedName("data")
        private libertadores.appsolution.com.libertadores.models.User user;

        public libertadores.appsolution.com.libertadores.models.User getUser() {
            return user;
        }
    }

    public static class News{

        @SerializedName("data")
        private ArrayList<libertadores.appsolution.com.libertadores.models.News> news;

        public ArrayList<libertadores.appsolution.com.libertadores.models.News> getNews() {
            return news;
        }
    }

    public static class Services{

        @SerializedName("data")
        private ArrayList<libertadores.appsolution.com.libertadores.models.Servicio> service;

        public ArrayList<libertadores.appsolution.com.libertadores.models.Servicio> getService() {
            return service;
        }
    }

    public static class City {

        @SerializedName("data")
        private libertadores.appsolution.com.libertadores.models.City citys;

        public libertadores.appsolution.com.libertadores.models.City getCity() {
            return citys;
        }
    }

    public static class Message extends Response {

        @SerializedName("message")
        private String message;

        public String getMessage() {
            return message;
        }
    }




}
