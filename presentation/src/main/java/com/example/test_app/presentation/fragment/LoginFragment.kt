package com.example.test_app.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.test_app.presentation.R
import com.example.test_app.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editText = view.findViewById<EditText>(R.id.steamIdEditText)
        val button = view.findViewById<Button>(R.id.btnLogin)

        button.setOnClickListener {
            val steamId = editText.text.toString().trim()
            if (steamId.isNotEmpty()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.login(steamId)
                    findNavController().navigate(R.id.action_login_to_list)
                }
            } else {
                Toast.makeText(requireContext(), "Введите Steam ID", Toast.LENGTH_SHORT).show()
            }
        }
    }
}