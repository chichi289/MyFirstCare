package com.myfirstcare.app.ui.home

import android.view.View
import com.myfirstcare.app.AuthenticationActivity
import com.myfirstcare.app.R
import com.myfirstcare.app.base.BaseFragment
import com.myfirstcare.app.databinding.FragmentHomeBinding
import com.myfirstcare.app.extentions.finishAndStartNewActivity
import com.myfirstcare.app.utils.Keys.KEY_EMAIL

class HomeFragment : BaseFragment<FragmentHomeBinding>(), View.OnClickListener {

    override fun provideLayout(): Int = R.layout.fragment_home

    override fun onBindData() {
        arguments?.let {
            if (it.containsKey(KEY_EMAIL)) {
                binding.textViewWelcomeMessage.text =
                    getString(R.string.welcome_username, it.getString(KEY_EMAIL))
            }
        }

        binding.buttonLogout.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonLogout -> finishAndStartNewActivity(
                requireActivity(),
                AuthenticationActivity::class.java
            )
        }
    }

}