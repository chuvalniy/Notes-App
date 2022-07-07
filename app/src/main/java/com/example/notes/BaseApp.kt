package com.example.notes

import android.app.Application
import com.example.common.di.coreModule
import com.example.feature_authentication.di.authAppModule
import com.example.feature_authentication.di.authDataModule
import com.example.feature_authentication.di.authDomainModule
import com.example.feature_note.di.noteAppModule
import com.example.feature_note.di.noteDataModule
import com.example.feature_note.di.noteDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApp)
            modules(
                coreModule,
                authAppModule,
                authDataModule,
                authDomainModule,
                noteAppModule,
                noteDomainModule,
                noteDataModule,
            )
        }
    }
}