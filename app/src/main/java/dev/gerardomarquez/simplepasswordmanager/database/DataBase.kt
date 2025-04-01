package dev.gerardomarquez.simplepasswordmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.gerardomarquez.simplepasswordmanager.dao.PasswordsInformationsDao
import dev.gerardomarquez.simplepasswordmanager.entities.PasswordsInformations

@Database(entities = [PasswordsInformations::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun passwordsInformationsDao(): PasswordsInformationsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tmp_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
