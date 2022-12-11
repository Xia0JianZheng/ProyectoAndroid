package com.example.proyectoandroid;

import java.util.ArrayList;
import java.util.List;

public class ReviewsRepositorio {
    List<Review> reviews = new ArrayList<>();

    interface Callback {
        void cuandoFinalice(List<Review> elementos);
    }

    ReviewsRepositorio(){

    }

    List<Review> obtener() {
        return reviews;
    }

    void insertar(Review review, Callback callback){
        reviews.add(review);
        callback.cuandoFinalice(reviews);
    }

    void eliminar(Review review, Callback callback) {
        reviews.remove(review);
        callback.cuandoFinalice(reviews);
    }

    void actualizar(Review review, float valoracion, Callback callback) {
        review.valoracion = valoracion;
        callback.cuandoFinalice(reviews);
    }
}
