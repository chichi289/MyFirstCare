package com.myfirstcare.app

import com.myfirstcare.app.base.BaseActivity
import com.myfirstcare.app.databinding.ActivityAuthenticationBinding
import com.myfirstcare.app.ui.auth.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : BaseActivity<ActivityAuthenticationBinding>() {

    override fun provideLayout(): Int = R.layout.activity_authentication

    override fun provideFragmentHolder(): Int = R.id.frameLayout

    override fun onBindData() {
        openFragment(LoginFragment::class.java, isAdd = false, addToBackStack = false)
    }

}