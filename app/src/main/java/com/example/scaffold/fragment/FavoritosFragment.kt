package com.example.scaffold.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import java.util.Locale

class FavoritosFragment : Fragment(), SearchableFragment {

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var pokemonList: List<ItemPokemon>
    private var filteredList: MutableList<ItemPokemon> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            // Aquí llamamos al método que recarga los datos
            refreshData()
        }

        // Mostrar la progressBar y cargar datos
        mainScope()

        // Datos de prueba
        pokemonList = listOf(
            ItemPokemon("Lucario", R.drawable.lucario, 5, true),
            ItemPokemon("Zoroark", R.drawable.zoroark, 5, true),
            ItemPokemon("Chansey", R.drawable.chansey, 2, false),
            ItemPokemon("Gallade", R.drawable.gallade, 3, false),
            ItemPokemon("Samurott", R.drawable.samurott, 5, true),
            ItemPokemon("Raichu", R.drawable.raichu, 4, false),
            ItemPokemon("Vaporeon", R.drawable.vaporeon, 2, true),
            ItemPokemon("Rayquaza", R.drawable.rayquaza, 4, true),
        )

        filteredList.addAll(pokemonList) // Inicialmente, la lista filtrada es igual a la original

        // Configurar RecyclerView
        recyclerView = binding.rv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ItemAdapter(requireContext(), filteredList)
        recyclerView.adapter = adapter
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

    private fun refreshData() {
        // Simula la recarga de datos
        scope.launch {
            // Simula una espera de 2 segundos para obtener nuevos datos
            delay(2000)

            // Actualiza la lista de Pokémon (por ejemplo, puedes agregar más ítems)
            pokemonList = pokemonList + ItemPokemon("Mewtwo", R.drawable.mewto, 5, true)
            filteredList.clear() // Limpiar la lista filtrada
            filteredList.addAll(pokemonList) // Volver a agregar los nuevos datos

            // Notificar al adaptador que los datos han cambiado
            adapter.notifyDataSetChanged()

            // Detener la animación del SwipeRefreshLayout
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onSearch(query: String) {
        Log.d("SearchDebug", "Buscando: $query") // Verificar si se recibe la búsqueda

        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(pokemonList)
        } else {
            val searchQuery = query.lowercase(Locale.getDefault())
            val results = pokemonList.filter {
                it.titulo.lowercase(Locale.getDefault()).contains(searchQuery)
            }
            filteredList.addAll(results)

            Log.d("SearchDebug", "Resultados: ${results.map { it.titulo }}") // Ver qué se filtra
        }

        adapter.notifyDataSetChanged() // Refrescar RecyclerView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job.cancel()
    }
}
