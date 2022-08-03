package com.colosoft.mybooks.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.colosoft.mybooks.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar

class SignUpFragment : Fragment() {

    private lateinit var signUpBinding: FragmentSignUpBinding
    private lateinit var signUpviewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        signUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        signUpviewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        signUpviewModel.errorMsg.observe(viewLifecycleOwner){ msg ->
            showErrorMessage(msg)
        }

        signUpviewModel.registerSuccess.observe(viewLifecycleOwner){
            goToLogin()
        }

        with(signUpBinding) {
            signUpButton.setOnClickListener {
                signUpviewModel.validateFields(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    repeatPasswordEditText.text.toString()
                )

            }
        }

        return signUpBinding.root
    }

    private fun showErrorMessage(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()
    }


    fun goToLogin() {
    findNavController().navigate(SignUpFragmentDirections.actionNavigationSignupToNavigationLogin())

}

override fun onResume() {
    super.onResume()
    (activity as AppCompatActivity).supportActionBar!!.hide()
}


}