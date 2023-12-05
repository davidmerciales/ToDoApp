package com.example.mvvmtodo.data.di

import android.app.Application
import androidx.room.Room
import com.example.mvvmtodo.data.dao.TodoDao
import com.example.mvvmtodo.data.db.AppDatabase
import com.example.mvvmtodo.data.repository.TodoRepository
import com.example.mvvmtodo.data.repository.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application) : AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "todo.db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideTodoDao(db :AppDatabase) : TodoDao {
        return db.dao
    }

    @Provides
    @Singleton
    fun provideTodoRepositoryImpl(dao:TodoDao) : TodoRepository {
        return TodoRepositoryImpl(dao)
    }

}