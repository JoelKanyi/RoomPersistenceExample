package com.kanyideveloper.roompersistenceexample.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kanyideveloper.roompersistenceexample.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){

    abstract fun userDao(): UserDAO

    companion object{

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context) : UserDatabase{
            val tempInstance = INSTANCE

            //If there is an instance, return the instance
            if (tempInstance != null){
                return tempInstance
            }

            //Else create the database instance
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance

                return instance
            }
        }
    }
}