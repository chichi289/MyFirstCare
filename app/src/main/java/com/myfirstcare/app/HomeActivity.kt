package com.myfirstcare.app

import com.myfirstcare.app.base.BaseActivity
import com.myfirstcare.app.databinding.ActivityHomeBinding
import com.myfirstcare.app.ui.home.HomeFragment
import com.myfirstcare.app.utils.Keys

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun provideLayout(): Int = R.layout.activity_home

    override fun provideFragmentHolder(): Int = R.id.frameLayout

    override fun onBindData() {
        openFragment(
            HomeFragment::class.java,
            isAdd = false,
            addToBackStack = false,
            bundle = intent?.getBundleExtra(
                Keys.KEY_BUNDLE
            )
        )
    }
}