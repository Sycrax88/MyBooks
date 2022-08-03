package com.colosoft.mybooks.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.colosoft.mybooks.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginBinding: FragmentLoginBinding
    private lateinit var loginviewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        loginviewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        loginviewModel.errorMsg.observe(viewLifecycleOwner){ msg ->
            showErrorMessage(msg)
        }
        loginviewModel.loginSuccess.observe(viewLifecycleOwner){
            goToList()
        }

        with(loginBinding){
            loginButton.setOnClickListener {
                loginviewModel.validateFields(emailEditText.text.toString(),passwordEditText.text.toString())
        }

            signUpTextView.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationSignup())
            }

        }
        return loginBinding.root
    }

    private fun showErrorMessage(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()
    }

    fun goToList(){
        findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationSignup())

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

}