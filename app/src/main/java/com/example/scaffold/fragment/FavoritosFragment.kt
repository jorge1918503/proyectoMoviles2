package com.example.scaffold.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scaffold.ItemAdapter
import com.example.scaffold.ItemPokemon
import com.example.scaffold.R
import com.example.scaffold.databinding.FragmentFavoritosBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoritosFragment : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el layout del fragment
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Llamada a la función para mostrar la progressBar
        mainScope()

        // Datos de prueba
        val pokemonList = listOf(
            ItemPokemon("Lucario", R.drawable.lucario, 5, true),
            ItemPokemon("Zoroark", R.drawable.zoroark, 5, true),
            ItemPokemon("Chansey", R.drawable.chansey, 2, false),
            ItemPokemon("Gallade", R.drawable.gallade, 3, false),
            ItemPokemon("Samurott", R.drawable.samurott, 5, true),
            ItemPokemon("Raichu", R.drawable.raichu, 4, false),
            ItemPokemon("Vaporeon", R.drawable.vaporeon, 2, true),
            ItemPokemon("Rayquaza", R.drawable.rayquaza, 4, true),
        )

        // Configuración del RecyclerView
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = ItemAdapter(requireContext(), pokemonList)
    }

    private fun mainScope() {
        // Mostrar la progressBar
        binding.pbfavoritos.visibility = View.VISIBLE
        // Ocultar el RecyclerView
        binding.rv.visibility = View.GONE
        // Lanza una corrutina en el MainScope
        MainScope().launch {
            // Esperar 2 segundos
            delay(2000)
            // Ocultar la progressBar y mostrar el RecyclerView
            binding.pbfavoritos.visibility = View.GONE
            binding.rv.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job.cancel()
    }
}
