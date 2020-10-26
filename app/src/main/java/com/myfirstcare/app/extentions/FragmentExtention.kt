package com.myfirstcare.app.extentions

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.myfirstcare.app.utils.Keys

inline fun <reified A : Class<*>> Fragment.finishAndStartNewActivity(
    activity: FragmentActivity,
    newActivity: A,
    bundle: Bundle? = null
) {
    val i = Intent(activity, newActivity)
    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    bundle?.let { i.putExtra(Keys.KEY_BUNDLE, it) }
    startActivity(i)
    activity.finish()
}