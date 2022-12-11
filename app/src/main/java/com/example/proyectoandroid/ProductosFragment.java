package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.example.proyectoandroid.databinding.FragmentProductosBinding;
import com.example.proyectoandroid.databinding.ViewholderProductoBinding;


import java.util.List;


public class ProductosFragment extends Fragment {
    private FragmentProductosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentProductosBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProductosViewModel productosViewModel = new ViewModelProvider(this).get(ProductosViewModel.class);

        ProductosAdapter productosAdapter = new ProductosAdapter();
        binding.productos.setAdapter(productosAdapter);

        binding.texto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { return false; }

            @Override
            public boolean onQueryTextChange(String s) {
                productosViewModel.buscar(s);
                return false;
            }
        });

        productosViewModel.respuestaMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Productos.Respuesta>() {
            @Override
            public void onChanged(Productos.Respuesta respuesta) {
//                respuesta.results.forEach(contenido -> Log.e("ABCD", contenido.artistName + ", " + contenido.trackName + ", " + contenido.artworkUrl100));
                productosAdapter.establecerListaProducto(respuesta.documents); // if != null
            }
        });
    }

    class ProductosAdapter extends RecyclerView.Adapter<ProductoViewHolder>{


        List<Productos.Document> productoList;

        @NonNull
        @Override
        public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProductoViewHolder(ViewholderProductoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
            Productos.Document contenido = productoList.get(position);

            holder.binding.nombre.setText(contenido.fields.nombre.stringValue);
            holder.binding.decripcion.setText(contenido.fields.descripcion.stringValue);
            holder.binding.precio.setText(contenido.fields.precio.stringValue);
            Glide.with(requireActivity()).load(contenido.fields.image.stringValue).into(holder.binding.image);
        }

        @Override
        public int getItemCount() {
            return productoList == null ? 0 : productoList.size();
        }

        void establecerListaProducto(List<Productos.Document> productoList){
            this.productoList = productoList;
            notifyDataSetChanged();
        }
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        ViewholderProductoBinding binding;

        public ProductoViewHolder(@NonNull ViewholderProductoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}