package com.example.scaffold.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.scaffold.R
import com.example.scaffold.databinding.FragmentScaffoldBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ScaffoldFragment : Fragment()
{
    private lateinit var binding: FragmentScaffoldBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        binding = FragmentScaffoldBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.FragmentContainerScaffold) as NavHostFragment
        val navController = navHostFragment.navController

        /* TOOLBAR */
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {

                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater)
                {
                    menuInflater.inflate(R.menu.toolbar, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_search -> {
                            // Manejar la selección del item1
                            true
                        }
                        R.id.action_settings -> {
                            // Manejar la selección del item2
                            true
                        }
                        else -> false
                    }
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        /* DRAWERLAYOUT */

        val toggle = ActionBarDrawerToggle(
            requireActivity(), binding.drawerLayout, binding.toolbar,
            R.string.user, R.string.registrar
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener {

            item -> when(item.itemId)
                    {
                            R.id.drawer_pokes -> {
                                true
                            }

                            R.id.drawer_favs -> {

                                true
                            }

                            R.id.drawer_contacto -> {
                                true
                            }

                            else -> false
                    }
        }

        /* BOTTOM NAVIGATION MENU */

        binding.bottomNavigation.setOnItemSelectedListener {
            item ->
                        when (item.itemId) {
                            R.id.bnm_pokes -> {
                                binding.FragmentContainerScaffold.findNavController().navigate(R.id.ListaFragment)
                                true
                            }
                            R.id.bnm_favs -> {
                                binding.FragmentContainerScaffold.findNavController().navigate(R.id.FavoritosFragment)
                                true
                            }
                            R.id.bnm_contacto -> {
                                binding.FragmentContainerScaffold.findNavController().navigate(R.id.ContactoFragment)
                                true
                            }
                            else -> false
            }
        }
    }

}