package com.nazirjon.testtaskapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.nazirjon.testtaskapp.*
import com.nazirjon.testtaskapp.databinding.FragmentAuthBinding
import com.nazirjon.testtaskapp.ui.payment.PaymentsFragment
import com.nazirjon.testtaskapp.viewModel.AuthViewModel
import com.nazirjon.testtaskapp.viewModel.AuthViewModelFactory

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory((activity?.application as App).repository,(activity?.application as App).sharedPreference )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAuthBinding.inflate(layoutInflater)

        binding.loginButton.setOnClickListener {
            val login = binding.loginEt.text.toString()
            val password = binding.passwordEt.text.toString()
            viewModel.signIn(login, password)
        }

        viewModel.token.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { token ->
                moveToPayments(token)
            }
        }

        viewModel.showSnackBar.observe(viewLifecycleOwner) { it ->
            it.getContentIfNotHandled()?.let { message ->
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }
        }

        val isLogout = arguments?.getBoolean(LOGOUT_TAG)?:false
        if (!isLogout){
            viewModel.getToken()
        }

        return binding.root
    }

    private fun moveToPayments(token: String) {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, PaymentsFragment.newInstance(token))
                .remove(this)
                .addToBackStack("payments")
                .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(isLogout: Boolean) =
                AuthFragment().apply {
                    arguments = Bundle().apply {
                        putBoolean(LOGOUT_TAG, isLogout)
                    }
                }

        private const val LOGOUT_TAG = "logout_tag"
    }
}