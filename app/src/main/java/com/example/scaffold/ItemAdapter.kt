package com.example.scaffold

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scaffold.R
import com.example.scaffold.databinding.LayoutItemBinding

// Adapter para RecyclerView
class ItemAdapter(private val context: Context, private val items: List<ItemPokemon>) : RecyclerView.Adapter<ItemAdapter.ItemPokemonViewHolder>() {

    // Infla el layout para cada ítem
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPokemonViewHolder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemPokemonViewHolder(binding)
    }

    // Asigna los valores de los datos a cada vista
    override fun onBindViewHolder(holder: ItemPokemonViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // Devuelve el tamaño de la lista
    override fun getItemCount(): Int {
        return items.size
    }

    class ItemPokemonViewHolder(private val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemPokemon) {
            // Acceder a las vistas directamente a través del binding
            binding.tituloItem.text = data.titulo
            // poner imagen
            Glide
                .with(binding.root.context)
                .load(data.imagen)
                .into(binding.imagenPokemon);

            binding.nEstrellas.text = data.nEstrellas.toString()

            /* Cambiar la imagen del icono de favorito según el valor de fav */
            binding.fabFav.setImageResource(
                if (data.fav) R.drawable.fav_selected else R.drawable.fav_unselected
            )

            /* Evento OnClick */
            binding.fabFav.setOnClickListener {
                data.fav = !data.fav
                binding.fabFav.setImageResource(
                    if (data.fav) R.drawable.fav_selected else R.drawable.fav_unselected
                )
            }
        }
    }
}
