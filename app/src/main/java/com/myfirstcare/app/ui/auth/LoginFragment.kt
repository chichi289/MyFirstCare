package com.myfirstcare.app.ui.auth

import android.os.Bundle
import android.view.View
import com.myfirstcare.app.HomeActivity
import com.myfirstcare.app.R
import com.myfirstcare.app.base.BaseFragment
import com.myfirstcare.app.databinding.FragmentLoginBinding
import com.myfirstcare.app.db.UserDatabase
import com.myfirstcare.app.db.entity.User
import com.myfirstcare.app.extentions.finishAndStartNewActivity
import com.myfirstcare.app.utils.Keys
import com.myfirstcare.app.utils.Validator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener {

    @Inject
    lateinit var validator: Validator

    @Inject
    lateinit var userDatabase: UserDatabase

    override fun provideLayout(): Int = R.layout.fragment_login

    override fun onBindData() {
        binding.buttonLogin.setOnClickListener(this)
        binding.buttonRegister.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonLogin -> {
                if (isValidated()) {
                    val emailUser = binding.editTextEmail.text.toString().trim()
                    val passwordUser = binding.editTextPassword.text.toString().trim()
                    var fetchedUser: User? = null
                    GlobalScope.launch {
                        launch(Dispatchers.IO) {
                            fetchedUser = userDatabase.userDao().getUserByEmail(emailUser)
                        }.join()
                        withContext(Dispatchers.Main) {
                            fetchedUser?.let {
                                if (it.password == passwordUser) {
                                    finishAndStartNewActivity(
                                        requireActivity(),
                                        HomeActivity::class.java,
                                        Bundle().apply {
                                            putString(Keys.KEY_EMAIL, it.email)
                                        })
                                } else {
                                    showToast(getString(R.string.error_message_incorrect_password))
                                }
                            } ?: showToast(getString(R.string.error_message_please_register_first))
                        }
                    }
                }
            }
            R.id.buttonRegister -> {
                addFragment(SignUpFragment::class.java, isAdd = true, addToBackStack = true)
            }
        }
    }

    private fun isValidated(): Boolean {
        return try {
            validator.submit(binding.editTextEmail, binding.textInputLayoutEmailPhone)
                .checkEmpty()
                .errorMessage(getString(R.string.error_message_please_enter_mobile_number_or_email))
                .check()

            validator.submit(binding.editTextEmail, binding.textInputLayoutEmailPhone)
                .checkEmailOrPassword()
                .errorMessage(getString(R.string.error_message_please_enter_valid_mobile_number_or_email))
                .check()

            validator.submit(binding.editTextPassword, binding.textInputLayoutPassword)
                .checkEmpty().errorMessage(getString(R.string.error_message_please_enter_password))
                .check()
            true
        } catch (e: Validator.ApplicationException) {
            false
        }
    }

}