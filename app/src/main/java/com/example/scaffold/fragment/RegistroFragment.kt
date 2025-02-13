package com.example.scaffold.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.scaffold.R
import com.example.scaffold.databinding.FragmentRegistroBinding
import com.google.android.material.snackbar.Snackbar

class RegistroFragment : Fragment() {

    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar los botones
        binding.facebookButton.setOnClickListener {
            Snackbar.make(view, "Registrando con Facebook", Snackbar.LENGTH_INDEFINITE)
                .setAction("Cerrar") {
                    // cerrar
                }
                .show()
        }

        binding.googleButton.setOnClickListener {
            Snackbar.make(view, "Registrando con Google", Snackbar.LENGTH_INDEFINITE)
                .setAction("Cerrar") {
                    // cerrar
                }
                .show()
        }

        binding.btnregistro.setOnClickListener {
            val email = binding.et1.text.toString()
            val password = binding.et2.text.toString()

            var isValid = true

            if (email.isEmpty()) {
                binding.tf1.error = "El campo de email no puede estar vacío"
                isValid = false
            } else {
                binding.tf1.error = null
            }

            if (password.isEmpty()) {
                binding.tf2.error = "El campo de contraseña no puede estar vacío"
                isValid = false
            } else {
                binding.tf2.error = null
            }

            if (isValid) {
                findNavController().navigate(R.id.action_RegistroFragment_to_ScaffoldFragment)
            }
        }

        // Cargar imágenes con Glide
        Glide
            .with(requireContext())
            .load(R.drawable.rojoavatar)
            .into(binding.imagenRed)

        Glide
            .with(requireContext())
            .load(R.drawable.chicaavatar)
            .into(binding.imagenChica)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
