package com.example.common.di

import com.example.common.settings.UserSessionStorage
import com.example.common.settings.UserSessionStorageImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {

    single<UserSessionStorage> {
        UserSessionStorageImpl(context = androidContext())
    }
}