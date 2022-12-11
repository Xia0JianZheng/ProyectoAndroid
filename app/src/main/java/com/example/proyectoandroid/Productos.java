package com.example.proyectoandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Productos {

    public static Api api = new Retrofit.Builder()
            .baseUrl("https://firestore.googleapis.com/v1/projects/proyectoandroid-9cdc7/databases/(default)/documents/Productos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api.class);

    public interface Api {
        @GET("DeLoQueQuieras/")
        Call<Respuesta> buscar();
    }

    class Respuesta {
        List<Document> documents;

    }

    class Document {
        Fields fields;
    }

    class Fields {
        Field nombre;
        Field descripcion;
        Field precio;
        Field image;
    }

    class Field {
        String stringValue;
    }
}
