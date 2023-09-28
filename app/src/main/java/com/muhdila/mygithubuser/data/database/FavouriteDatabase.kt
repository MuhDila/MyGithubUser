package com.muhdila.mygithubuser.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Favourite::class], version = 2, exportSchema = false)
abstract class FavouriteDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao

    companion object {
        @Volatile
        private var instance: FavouriteDatabase? = null

        // Define the migration from version 1 to 2
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // You can add SQL statements here to modify the schema
                // In your case, you're adding a new column
                database.execSQL("ALTER TABLE Favourite ADD COLUMN homeUrl TEXT")
            }
        }

        fun getInstance(context: Context): FavouriteDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteDatabase::class.java, "Favourite.db"
                )
                    .addMigrations(MIGRATION_1_2) // Add the migration here
                    .build()
            }
    }
}
