package com.example.coursestrack.di

import com.example.coursestrack.data.repository.AuthRepository
import com.example.coursestrack.data.repository.AuthRepositoryFirebase
import com.example.coursestrack.data.repository.InstitutionRepository
import com.example.coursestrack.data.repository.InstitutionRepositoryFirebase
import com.example.coursestrack.data.repository.MatterRepository
import com.example.coursestrack.data.repository.MatterRepositoryFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    @Provides
    @Singleton
    fun provideMatterRepository(
        firestore: FirebaseFirestore,
        authRepository: AuthRepository
    ): MatterRepository {
        return MatterRepositoryFirebase(firestore, authRepository)
    }

    @Provides
    @Singleton
    fun provideInstitutionRepository(
        firestore: FirebaseFirestore,
        authRepository: AuthRepository
    ): InstitutionRepository {
        return InstitutionRepositoryFirebase(firestore, authRepository)
    }
}