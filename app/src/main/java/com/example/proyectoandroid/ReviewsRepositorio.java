package com.example.proyectoandroid;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ReviewsRepositorio {

    Executor executor = Executors.newSingleThreadExecutor();

    ReviewsBaseDeDatos.ReviewsDao reviewsDao;

    ReviewsRepositorio(Application application){
        reviewsDao = ReviewsBaseDeDatos.obtenerInstancia(application).obtenerReviewsDao();
    }


    ReviewsRepositorio(){
    }

    LiveData<List<Review>> obtener(){
        return reviewsDao.obtener();
    }

    void insertar(Review review, RoomDatabase.Callback callback){
    }

    void eliminar(Review review, RoomDatabase.Callback callback) {

    }

    void actualizar(Review review, float valoracion, RoomDatabase.Callback callback) {

    }

    void insertar(Review review){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                reviewsDao.insertar(review);
            }
        });
    }

    void eliminar(Review review) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                reviewsDao.eliminar(review);
            }
        });
    }

    public void actualizar(Review review, float valoracion) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                review.valoracion = valoracion;
                reviewsDao.actualizar(review);
            }
        });
    }
}
