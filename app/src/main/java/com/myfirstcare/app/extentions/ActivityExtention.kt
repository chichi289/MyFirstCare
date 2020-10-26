package com.myfirstcare.app.extentions

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myfirstcare.app.utils.Keys.KEY_BUNDLE

inline fun <reified A : Class<*>> AppCompatActivity.finishAndStartNewActivity(newActivity: A,bundle: Bundle?=null) {
    val i = Intent(this, newActivity)
    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    bundle?.let {
        i.putExtra(KEY_BUNDLE,it)
    }
    startActivity(i)
    this.finish()
}