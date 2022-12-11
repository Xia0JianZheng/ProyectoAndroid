package com.example.proyectoandroid;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ReviewsViewModel extends AndroidViewModel {
    ReviewsRepositorio reviewsRepositorio;

    MutableLiveData<Review> reviewSeleccionado = new MutableLiveData<>();

    public ReviewsViewModel(@NonNull Application application) {
        super(application);

        reviewsRepositorio = new ReviewsRepositorio();

        reviewsRepositorio = new ReviewsRepositorio(application);
    }

    LiveData<List<Review>> obtener(){
        return reviewsRepositorio.obtener();
    }

    void insertar(Review review){
        reviewsRepositorio.insertar(review);
    }

    void eliminar(Review review){
        reviewsRepositorio.eliminar(review);
    }

    void actualizar(Review review, float valoracion){
        reviewsRepositorio.actualizar(review, valoracion);
    }

    void seleccionar(Review review){
        reviewSeleccionado.setValue(review);
    }

    MutableLiveData<Review> seleccionado(){
        return reviewSeleccionado;
    }







}
