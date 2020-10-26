package com.myfirstcare.app.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, provideLayout())
        binding.lifecycleOwner = this
        onBindData()
    }

    open fun <T : BaseFragment<*>> openFragment(
        fragment: Class<T>,
        isAdd: Boolean,
        addToBackStack: Boolean = true,
        bundle: Bundle? = null
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val instance = fragment.newInstance()
        bundle?.let {
            instance.arguments = it
        }
        if (isAdd) {

            fragmentTransaction.add(
                provideFragmentHolder(),
                instance,
                fragment::class.java.simpleName
            )
        } else {
            fragmentTransaction.replace(
                provideFragmentHolder(),
                instance,
                fragment::class.java.simpleName
            )
        }

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    private fun getCurrentFragment(): BaseFragment<*>? {
        supportFragmentManager.findFragmentById(provideFragmentHolder())?.let {
            return it as BaseFragment<*>
        } ?: return null
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        val currentFragment = getCurrentFragment()
        if (currentFragment == null) {
            super.onBackPressed()
        } else {
            if (currentFragment.onBackActionPerform()) {
                super.onBackPressed()
            }
        }
    }

    @LayoutRes
    abstract fun provideLayout(): Int

    @IdRes
    abstract fun provideFragmentHolder(): Int
    abstract fun onBindData()
}