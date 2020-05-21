package com.fuun.notihub.di

import com.fuun.notihub.data.db.NotifyDatabase
import com.fuun.notihub.data.mapper.NotifyCachedRawMapper
import com.fuun.notihub.data.preferences.PreferencesStore
import com.fuun.notihub.data.repository.NotifyRepositoryImpl
import com.fuun.notihub.domain.repository.NotifyRepository
import com.fuun.notihub.features.notifylist.NotificationTrackerViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { NotifyDatabase.create(get()) }

    factory<NotifyRepository> {
        val notifyDao = get<NotifyDatabase>().notifyDao()
        NotifyRepositoryImpl(notifyDao, NotifyCachedRawMapper())
    }

    factory {
        PreferencesStore(androidContext())
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
val notificationTrackerFragmentModule = module {
    viewModel {
        NotificationTrackerViewModel(get(), get())
    }
}
