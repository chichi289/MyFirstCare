package com.myfirstcare.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    protected lateinit var binding: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            provideLayout(), container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onBindData()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getCurrentActivity(): BaseActivity<*> =
        (requireActivity() as BaseActivity<*>)

    protected fun <T : BaseFragment<*>> addFragment(
        fragment: Class<T>,
        isAdd: Boolean,
        addToBackStack: Boolean = true
    ) {
        getCurrentActivity().openFragment(fragment, isAdd, addToBackStack)
    }

    open fun onBackActionPerform(): Boolean = true

    protected fun goBack(){
        getCurrentActivity().onBackPressed()
    }

    protected fun showToast(message:String){
        getCurrentActivity().showToast(message)
    }

    @LayoutRes
    abstract fun provideLayout(): Int
    abstract fun onBindData()
}