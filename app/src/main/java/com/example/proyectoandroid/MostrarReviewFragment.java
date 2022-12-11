package com.example.proyectoandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoandroid.databinding.FragmentMostrarReviewBinding;

public class MostrarReviewFragment extends Fragment {
    private FragmentMostrarReviewBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMostrarReviewBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReviewsViewModel reviewsViewModel = new ViewModelProvider(requireActivity()).get(ReviewsViewModel.class);

        reviewsViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Review>() {
            @Override
            public void onChanged(Review review) {
                binding.nombre.setText(review.nombre);
                binding.descripcion.setText(review.descripcion);
                binding.valoracion.setRating(review.valoracion);

                binding.valoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if(fromUser){
                            reviewsViewModel.actualizar(review, rating);
                        }
                    }
                });
            }
        });
    }
}
