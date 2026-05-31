package ua.opu.lab4shydl.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "tasks_database"
            ).build()
            database = instance
            instance
        }
    }
}
