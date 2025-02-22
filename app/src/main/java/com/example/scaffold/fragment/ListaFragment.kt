package com.example.scaffold.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scaffold.ItemAdapter
import com.example.scaffold.ItemPokemon
import com.example.scaffold.databinding.FragmentListaBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ListaFragment : Fragment() {

    private lateinit var binding: FragmentListaBinding
    private lateinit var adapter: ItemAdapter
    private var pokemonsLista = mutableListOf<ItemPokemon>()
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.visibility = View.GONE

        lifecycleScope.launch {
            //delay(2500)


            async { obtenerPokemons() }.await()


            binding.rv.visibility = View.VISIBLE

            //setupSearchView()

        }
    }

    private fun obtenerPokemons() {
        val pokemonsLista = mutableListOf<ItemPokemon>()
        db.collection("pokemons")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val pokemon = ItemPokemon(
                        document.getString("titulo") ?: "",
                        document.getLong("imagen")?.toInt() ?: 0,
                        document.getLong("nEstrellas")?.toInt() ?: 0,
                        document.getBoolean("fav") ?: false
                    )
                    pokemonsLista.add(pokemon)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                setupRecyclerView(pokemonsLista)
                binding.rv.visibility = View.VISIBLE
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }



    }
    private fun setupRecyclerView(pokemons: List<ItemPokemon>) {
        binding.rv.layoutManager =
            LinearLayoutManager(requireContext())
        adapter = ItemAdapter(requireContext(), pokemons)
        binding.rv.adapter = adapter
    }

//    private fun setupSearchView() {
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                filtrarPokemons(newText.orEmpty())
//                return true
//            }
//        })
//    }
//
//    private fun filtrarPokemons(query: String) {
//        val listaFiltrada = pokemonsLista.filter { it.titulo.contains(query, ignoreCase = true) }
//        adapter.actualizarLista(listaFiltrada)
//    }
}