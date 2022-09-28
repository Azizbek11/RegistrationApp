package admiral.group.registrationapp.di

import admiral.group.registrationapp.data.SharedPref
import admiral.group.registrationapp.ui.login.MyRepository
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 object RepositoryModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context):SharedPref{
       return SharedPref(context)
    }


    @Singleton
    @Provides
    fun provideRepository(sharedPref: SharedPref):MyRepository{
       return MyRepository(sharedPref)
    }

}