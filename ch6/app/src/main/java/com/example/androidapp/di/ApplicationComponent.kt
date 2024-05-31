package com.example.androidapp.di

import android.content.Context
import com.example.androidapp.presentation.fragment.HomeFragment
import com.example.androidapp.presentation.fragment.LoginFragment
import com.example.androidapp.presentation.fragment.MovieDetailFragment
import com.example.androidapp.presentation.fragment.ProfileFragment
import com.example.androidapp.presentation.fragment.RegisterFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DatabaseModule::class, DataStoreModule::class])
interface ApplicationComponent {

    fun inject(homeFragment: HomeFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(registerFragment: RegisterFragment)
    fun inject(movieDetailFragment: MovieDetailFragment)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}