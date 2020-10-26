package com.myfirstcare.app.ui.auth

import android.os.Bundle
import android.view.View
import com.myfirstcare.app.HomeActivity
import com.myfirstcare.app.R
import com.myfirstcare.app.base.BaseFragment
import com.myfirstcare.app.databinding.FragmentSignUpBinding
import com.myfirstcare.app.db.UserDatabase
import com.myfirstcare.app.db.entity.User
import com.myfirstcare.app.extentions.finishAndStartNewActivity
import com.myfirstcare.app.utils.Keys
import com.myfirstcare.app.utils.Validator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.toolbar_back_title.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(), View.OnClickListener {

    @Inject
    lateinit var validator: Validator

    @Inject
    lateinit var userDatabase: UserDatabase

    override fun provideLayout(): Int = R.layout.fragment_sign_up

    override fun onBindData() {
        imageViewBack.setOnClickListener(this)
        buttonRegister.setOnClickListener(this)
        textViewToolbarTitle.text = getString(R.string.register)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imageViewBack -> goBack()
            R.id.buttonRegister -> {
                if (isValidated()) {
                    val userEmail = editTextEmail.text.toString().trim()
                    val userPassword = editTextPassword.text.toString().trim()

                    GlobalScope.launch {
                        launch(Dispatchers.IO) {
                            userDatabase.userDao().insertUser(
                                User().apply {
                                    email = userEmail
                                    password = userPassword
                                }
                            )
                        }.join()
                        withContext(Dispatchers.Main) {
                            finishAndStartNewActivity(
                                requireActivity(),
                                HomeActivity::class.java,
                                Bundle().apply {
                                    putString(Keys.KEY_EMAIL, userEmail)
                                })
                        }
                    }
                }
            }
        }
    }

    private fun isValidated(): Boolean {
        return try {
            validator.submit(editTextNameSurname, textInputLayoutNameSurname)
                .checkEmpty()
                .errorMessage(getString(R.string.error_message_please_enter_name_and_surname))
                .check()

            validator.submit(editTextEmail, textInputLayoutEmail)
                .checkEmpty().errorMessage(getString(R.string.error_message_please_enter_email))
                .check()

            validator.submit(editTextEmail, textInputLayoutEmail)
                .checkValidEmail()
                .errorMessage(getString(R.string.error_message_please_enter_valid_email))
                .check()

            validator.submit(editTextMobile, textInputLayoutMobile)
                .checkEmpty()
                .errorMessage(getString(R.string.error_message_please_enter_mobile_number))
                .check()

            validator.submit(editTextMobile, textInputLayoutMobile)
                .checkMinDigits(10)
                .errorMessage(getString(R.string.error_message_please_enter_valid_mobile_number))
                .check()

            validator.submit(editTextPassword, textInputLayoutPassword)
                .checkEmpty().errorMessage(getString(R.string.error_message_please_enter_password))
                .check()

            validator.submit(editTextConfirmPassword, textInputLayoutConfirmPassword)
                .checkEmpty()
                .errorMessage(getString(R.string.error_message_please_enter_confirm_password))
                .check()

            validator.submit(editTextConfirmPassword, textInputLayoutConfirmPassword)
                .matchString(editTextPassword.text.toString().trim())
                .errorMessage(getString(R.string.error_message_confirm_password_does_not_match))
                .check()

            validator.submit(editTextWork, textInputLayoutWork)
                .checkEmpty()
                .errorMessage(getString(R.string.error_message_please_enter_your_position_work_or_responsible))
                .check()

            validator.submit(editTextGeneralHospitalName, textInputLayoutGeneralHospitalName)
                .checkEmpty()
                .errorMessage(getString(R.string.error_message_please_enter_general_hospital_name))
                .check()

            validator.submit(editTextCommunityHospitalName, textInputLayoutCommunityHospitalName)
                .checkEmpty()
                .errorMessage(getString(R.string.error_message_please_enter_community_hospital_name))
                .check()

            validator.submit(editTextVillageHospitalName, textInputLayoutVillageHospitalName)
                .checkEmpty()
                .errorMessage(getString(R.string.error_message_please_enter_village_hospital_name))
                .check()

            true
        } catch (e: Validator.ApplicationException) {
            false
        }
    }

}