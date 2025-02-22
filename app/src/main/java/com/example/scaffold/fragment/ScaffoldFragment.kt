package com.example.scaffold.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.scaffold.R
import com.example.scaffold.databinding.FragmentScaffoldBinding

class ScaffoldFragment : Fragment() {

    private lateinit var binding: FragmentScaffoldBinding
    private var currentFragment: Fragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScaffoldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.FragmentContainerScaffold) as NavHostFragment
        val navController = navHostFragment.navController

        /* TOOLBAR */
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            requireActivity(), binding.drawerLayout, binding.toolbar,
            R.string.user, R.string.registrar
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        /* CONFIGURAR EL MENU */
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar, menu)

                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

                searchView.queryHint = "Buscar Pokémon"
                searchView.setOnQueryTextListener(object :
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let { searchInCurrentFragment(it) }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let { searchInCurrentFragment(it) }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_settings -> {
                        // Manejar la selección del item de configuración
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        /* CONFIGURAR DRAWER MENU */
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.drawer_pokes -> {
                    binding.FragmentContainerScaffold.findNavController()
                        .navigate(R.id.ListaFragment)
                    true
                }

                R.id.drawer_favs -> {
                    binding.FragmentContainerScaffold.findNavController()
                        .navigate(R.id.FavoritosFragment)
                    true
                }

                R.id.drawer_contacto -> {
                    binding.FragmentContainerScaffold.findNavController()
                        .navigate(R.id.ContactoFragment)
                    true
                }

                else -> false
            }
        }

        /* CONFIGURAR BOTTOM NAVIGATION */
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnm_pokes -> {
                    binding.FragmentContainerScaffold.findNavController()
                        .navigate(R.id.ListaFragment)
                    true
                }

                R.id.bnm_favs -> {
                    binding.FragmentContainerScaffold.findNavController()
                        .navigate(R.id.FavoritosFragment)
                    true
                }

                R.id.bnm_contacto -> {
                    binding.FragmentContainerScaffold.findNavController()
                        .navigate(R.id.ContactoFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun navigateToFragment(fragmentId: Int) {
        val navController = findNavController()
        navController.navigate(fragmentId)

        // Actualizar el fragmento actual
        currentFragment =
            childFragmentManager.findFragmentById(R.id.FragmentContainerScaffold)
    }

    private fun searchInCurrentFragment(query: String) {
        val currentFragment =
            childFragmentManager.findFragmentById(R.id.FragmentContainerScaffold)

        Log.d("SearchDebug", "Fragment actual: ${currentFragment?.javaClass?.simpleName}")

        if (currentFragment is SearchableFragment) {
            Log.d("SearchDebug", "Llamando a onSearch en ${currentFragment::class.java.simpleName}")
            currentFragment.onSearch(query)
        } else {
            Log.d("SearchDebug", "No es un fragmento buscable")
        }
    }
}
