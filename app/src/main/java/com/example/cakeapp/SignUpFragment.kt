package com.example.cakeapp

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.cakeapp.databinding.FragmentLoginBinding
import com.example.cakeapp.databinding.FragmentSignUpBinding
import com.example.cakeapp.models.LoginModel
import com.example.cakeapp.models.SignUpModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    fun validateEmail() {
        binding.editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.text.toString()).matches()) {
                    binding.button.isEnabled = true
                } else {
                    binding.button.isEnabled = false
                    binding.editTextEmail.setError("Invalid Email")
                }
            }

        }
        )
    }

    fun validateEmptyFields() {

        val signUpTextWatcher = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val fullNameInput = binding.editTextName.text.toString().trim()
                val emailInput = binding.editTextEmail.text.toString().trim()
                val passwordInput = binding.editTextPassword.text.toString().trim()

                binding.button.isEnabled = (!fullNameInput.isEmpty() && !emailInput.isEmpty() && !passwordInput.isEmpty())
            }

        }

        binding.editTextName.addTextChangedListener(signUpTextWatcher)
        binding.editTextEmail.addTextChangedListener(signUpTextWatcher)
        binding.editTextPassword.addTextChangedListener(signUpTextWatcher)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateEmail()
        validateEmptyFields()

        binding.button.setOnClickListener {
            // Manejo de hilos de manera async
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    // Que hilo esta disponible?
                    val responseSignUp = withContext(Dispatchers.Default) {
                        val cakeRepository = CakeRepository()
                        cakeRepository.signUp(
                            SignUpModel(
                                binding.editTextName.text.toString(),
                                binding.editTextEmail.text.toString(),
                                binding.editTextPassword.text.toString()
                            )
                        )
                    }
                    Toast.makeText(
                        this@SignUpFragment.context,
                        responseSignUp.token,
                        Toast.LENGTH_SHORT
                    ).show()

                } catch (e: Exception) {
                    Toast.makeText(this@SignUpFragment.context, "${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


    }


}