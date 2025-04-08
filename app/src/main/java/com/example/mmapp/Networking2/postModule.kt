package com.example.mmapp.Networking2

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object postModule {
    @Provides
    @Singleton
    fun providePostService(): PostService {
        return PostService()
    }

    @Provides
    @Singleton
    fun provideUserRepository(postService: PostService): PostRepository {
        return PostRepository(postService)
    }
}