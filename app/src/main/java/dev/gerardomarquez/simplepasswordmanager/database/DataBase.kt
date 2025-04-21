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

        fun getInstance(context: Context): AppDatabase {
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

        /**
         * Metodo que elimina la base de datos y crea una nueva sin registros
         * @param context Contexto de la aplicacion
         * @return Nueva instancia de la base de datos limpia
         */
        fun resetDatabase(context: Context): AppDatabase {
            synchronized(this) {
                // 1. Cerrar si existe
                INSTANCE?.close()
                INSTANCE = null

                // 2. Borrar los archivos de la base de datos
                context.deleteDatabase("tmp_database")

                // 3. Crear nueva instancia inmediatamente
                val newInstance = getInstance(context)
                INSTANCE = newInstance
                return newInstance
            }
        }
    }
}
