package com.example.proyectoandroid;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosViewModel extends AndroidViewModel {
    MutableLiveData<Productos.Respuesta> respuestaMutableLiveData = new MutableLiveData<>();

    public ProductosViewModel(@NonNull Application application) {
        super(application);
    }

    public void buscar(String term){
        Productos.api.buscar().enqueue(new Callback<Productos.Respuesta>() {
            @Override
            public void onResponse(@NonNull Call<Productos.Respuesta> call, @NonNull Response<Productos.Respuesta> response) {
                respuestaMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Productos.Respuesta> call, @NonNull Throwable t) {}
        });
    }
}
