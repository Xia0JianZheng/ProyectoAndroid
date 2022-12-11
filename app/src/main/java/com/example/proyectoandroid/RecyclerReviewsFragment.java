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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoandroid.databinding.FragmentRecyclerReviewsBinding;
import com.example.proyectoandroid.databinding.ViewholderReviewBinding;

import java.util.List;

public class RecyclerReviewsFragment extends Fragment {
    private FragmentRecyclerReviewsBinding binding;
    private ReviewsViewModel reviewsViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRecyclerReviewsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reviewsViewModel = new ViewModelProvider(requireActivity()).get(ReviewsViewModel.class);
        navController = Navigation.findNavController(view);

        binding.irANuevoReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_recyclerReviewsFragment_to_nuevoReviewFragment);
            }
        });

        ReviewsAdapter reviewsAdapter = new ReviewsAdapter();
        binding.recyclerView.setAdapter(reviewsAdapter);

        reviewsViewModel.obtener().observe(getViewLifecycleOwner(), new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewsAdapter.establecerLista(reviews);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT  | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();
                Review review = reviewsAdapter.obtenerReview(posicion);
                reviewsViewModel.eliminar(review);

            }
        }).attachToRecyclerView(binding.recyclerView);
    }
    class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderReviewBinding binding;

        public ReviewViewHolder(ViewholderReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

        List<Review> review;

        @NonNull
        @Override
        public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ReviewViewHolder(ViewholderReviewBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

            Review review = this.review.get(position);

            holder.binding.nombre.setText(review.nombre);
            holder.binding.valoracion.setRating(review.valoracion);

            holder.binding.valoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if(fromUser) {
                        reviewsViewModel.actualizar(review, rating);
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reviewsViewModel.seleccionar(review);
                    navController.navigate(R.id.action_recyclerReviewsFragment_to_mostrarReviewFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return review != null ? review.size() : 0;
        }

        public void establecerLista(List<Review> reviews){
            this.review = reviews;
            notifyDataSetChanged();
        }

        public Review obtenerReview(int posicion){
            return review.get(posicion);
        }
    }
}
