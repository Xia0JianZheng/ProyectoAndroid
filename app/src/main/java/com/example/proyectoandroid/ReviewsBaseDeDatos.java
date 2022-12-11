package com.example.proyectoandroid;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.List;

@Database(entities = { Review.class }, version = 1, exportSchema = false)
public abstract class ReviewsBaseDeDatos extends RoomDatabase {

    public abstract ReviewsDao obtenerReviewsDao();

    private static volatile ReviewsBaseDeDatos INSTANCIA;

    static ReviewsBaseDeDatos obtenerInstancia(final Context context) {
        if (INSTANCIA == null) {
            synchronized (ReviewsBaseDeDatos.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context,
                                    ReviewsBaseDeDatos.class, "elementos.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCIA;
    }

    @Dao
    interface ReviewsDao {
        @Query("SELECT * FROM Review")
        LiveData<List<Review>> obtener();

        @Insert
        void insertar(Review review);

        @Update
        void actualizar(Review review);

        @Delete
        void eliminar(Review review);
    }
}
