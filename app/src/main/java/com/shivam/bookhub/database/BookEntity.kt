package com.shivam.bookhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// in a database can multiple table exist with a common primary relationship with each other.
// this line define that there is a table which name as books and its column with one primary key available  here.
@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey@ColumnInfo(name="book_Id") val bookId: Int,
    @ColumnInfo(name = "book_name") val bookName: String,
    @ColumnInfo(name = "book_author") val bookAuthor: String,
    @ColumnInfo(name = "book_rating") val bookRating: String,
    @ColumnInfo(name = "book_price") val bookPrice: String,
    @ColumnInfo(name = "book_image") val bookImage: String,
    @ColumnInfo(name = "book_Desc") val bookDesc: String,
)
