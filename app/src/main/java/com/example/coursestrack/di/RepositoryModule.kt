package com.example.coursestrack.di

import com.example.coursestrack.data.repository.AuthRepository
import com.example.coursestrack.data.repository.AuthRepositoryFirebase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthRepositoryFirebase(auth)
    }
}