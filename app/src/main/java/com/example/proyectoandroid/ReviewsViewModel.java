package com.example.proyectoandroid;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ReviewsViewModel extends AndroidViewModel {
    ReviewsRepositorio reviewsRepositorio;

    MutableLiveData<List<Review>> listreviewsMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Review> reviewSeleccionado = new MutableLiveData<>();

    public ReviewsViewModel(@NonNull Application application) {
        super(application);

        reviewsRepositorio = new ReviewsRepositorio();

        listreviewsMutableLiveData.setValue(reviewsRepositorio.obtener());
    }

    MutableLiveData<List<Review>> obtener(){
        return listreviewsMutableLiveData;
    }

    void insertar(Review review){
        reviewsRepositorio.insertar(review, new ReviewsRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Review> reviews) {
                listreviewsMutableLiveData.setValue(reviews);
            }
        });
    }

    void eliminar(Review review){
        reviewsRepositorio.eliminar(review, new ReviewsRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Review> reviews) {
                listreviewsMutableLiveData.setValue(reviews);
            }
        });
    }

    void actualizar(Review review, float valoracion){
        reviewsRepositorio.actualizar(review, valoracion, new ReviewsRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Review> review) {
                listreviewsMutableLiveData.setValue(review);
            }
        });
    }

    void seleccionar(Review review){
        reviewSeleccionado.setValue(review);
    }

    MutableLiveData<Review> seleccionado(){
        return reviewSeleccionado;
    }
}
