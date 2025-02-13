package com.example.scaffold.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.scaffold.R
import com.example.scaffold.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Asignar los escuchadores

        // Botón registrar que lleva a la pantalla de registro
        binding.registrar.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_RegistroFragment)
        }

        binding.backButton.setOnClickListener {
            Snackbar.make(view, "Hacia atrás", Snackbar.LENGTH_INDEFINITE)
                .setAction("Cerrar") {
                    // cerrar
                }
                .show()
        }

        binding.btnOlvidar.setOnClickListener {
            Snackbar.make(view, "Recuperando contraseña", Snackbar.LENGTH_INDEFINITE)
                .setAction("Cerrar") {
                    // cerrar
                }
                .show()
        }

        binding.googleButton.setOnClickListener {
            Snackbar.make(view, "Iniciando sesión con Google", Snackbar.LENGTH_INDEFINITE)
                .setAction("Cerrar") {
                    // cerrar
                }
                .show()
        }

        binding.facebookButton.setOnClickListener {
            // Intent para abrir ActivityContacto
            //val intentContacto = Intent(requireContext(), ContactoActivity::class.java)
            //startActivity(intentContacto)
        }

        // Botón iniciar sesión, comprueba que los datos no estén vacíos y lleva a la pantalla de favoritos
        binding.btn1.setOnClickListener {
            val username = binding.et1.text.toString()
            val password = binding.et2.text.toString()

            var isValid = true

            if (username.isEmpty()) {
                binding.tf1.error = "El campo de usuario no puede estar vacío"
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
                findNavController().navigate(R.id.action_LoginFragment_to_ScaffoldFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
