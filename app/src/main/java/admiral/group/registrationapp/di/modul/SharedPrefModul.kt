package admiral.group.registrationapp.di.modul

import admiral.group.registrationapp.database.PrefRepository
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefModul {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): PrefRepository {
        return PrefRepository(context)
    }
}