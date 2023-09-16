package com.shivam.bookhub.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shivam.bookhub.database.BookDao
import com.shivam.bookhub.database.BookEntity

@Database(entities = [BookEntity::class],version=1)
abstract class BookDatabase:RoomDatabase() {
    abstract fun bookDao():BookDao

}