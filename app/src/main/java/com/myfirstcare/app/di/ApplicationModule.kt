package com.myfirstcare.app.di

import android.content.Context
import com.myfirstcare.app.db.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
class ApplicationModule {

    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context) = UserDatabase(context)
}