package be.hogent.kolveniershof.injection

import org.koin.dsl.module.Module

val appComponent: List<Module> = listOf(viewModelModule, networkModule, repositoryModule, sharedPreferencesModule)