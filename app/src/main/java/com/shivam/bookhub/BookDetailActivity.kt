package com.shivam.bookhub

import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.shivam.bookhub.R.*
import com.shivam.bookhub.database.BookEntity
import com.shivam.bookhub.db.BookDatabase
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class BookDetailActivity : AppCompatActivity() {
    var bookid: String? = "100"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_book_detail)

        var bookTitle: TextView = findViewById(id.bookTitle)
        val bookAuthor: TextView = findViewById(id.bookAuthors)
        val bookDescription: TextView = findViewById(id.bookDescription)

        val bookPrice: TextView = findViewById(id.bookPrice)

                                             
        val bookRatingNo: TextView = findViewById(id.bookRatingNo)
        val bookImage: ImageView = findViewById(id.favBookImage)
        val toolbar: Toolbar = findViewById(id.toolbar2)
        val btnFavourites: Button = findViewById(id.buttonFavourites)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Detail"
        toolbar.setTitleTextColor(Color.WHITE)


        if (intent != null) {
            bookid = intent.getStringExtra("book_id")

        } else {
            if (bookid == "100") {
                finish()
                Toast.makeText(this@BookDetailActivity, "Error", Toast.LENGTH_SHORT).show()
            }


        }

        val queue = Volley.newRequestQueue(this@BookDetailActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParam = JSONObject()
        jsonParam.put("book_id", bookid)

        val jsonRequest =
            object : JsonObjectRequest(Method.POST, url, jsonParam, Response.Listener {
                try {
                    val success = it.getBoolean("success")
                    if (success) {
                        val bookJsonObject = it.getJSONObject("book_data")
                        val bookImageUrl = bookJsonObject.getString("image")
                        Picasso.get().load(bookJsonObject.getString("image")).into(bookImage)
                        bookTitle.text = bookJsonObject.getString("name")
                        bookAuthor.text = bookJsonObject.getString("author")
                        bookPrice.text = bookJsonObject.getString("price")
                        bookRatingNo.text = bookJsonObject.getString("rating")
                        bookDescription.text = bookJsonObject.getString("description")


                        val bookEntity = BookEntity(
                            bookid?.toInt() as Int,
                            bookTitle.text.toString(),
                            bookAuthor.text.toString(),
                            bookPrice.text.toString(),
                            bookRatingNo.text.toString(),
                            bookImageUrl,
                            bookDescription.text.toString()

                        )
                        val checkFav =
                            BookDetailAsyncTask(applicationContext, bookEntity, 1).execute()
                        val isFav = checkFav.get()
                        if (isFav == true) {
                            btnFavourites.text = "Remove from Favourites"
                            val favColor = ContextCompat.getColor(
                                applicationContext,
                                color.color_favourites
                            )
                            btnFavourites.setBackgroundColor(favColor)


                        } else {
                            btnFavourites.text = "Add to Favourites"
                            val nofavColor =
                                ContextCompat.getColor(applicationContext, color.black)
                            btnFavourites.setBackgroundColor(nofavColor)

                        }

                        btnFavourites.setOnClickListener {
                            // this line tell about that yeh block jab execute hoga jab book favourite me nahi hogi
                            if (!BookDetailAsyncTask(applicationContext, bookEntity, 1).execute().get()) {

                                // for save book we execute insert mode 2 method here
                                val async = BookDetailAsyncTask(applicationContext, bookEntity, 2).execute()
                                val result = async.get()
                                if (result) {
                                    // yeadi book add ho gyi to remove mutton show kro otherwise error show kro
                                    Toast.makeText(this@BookDetailActivity, "Book added successfully...", Toast.LENGTH_SHORT).show()
                                    btnFavourites.text = "Remove from Favourites"
                                    val favColor = ContextCompat.getColor(applicationContext, color.color_favourites
                                    )
                                    btnFavourites.setBackgroundColor(favColor)
                                } else {
                                    Toast.makeText(this@BookDetailActivity, "Something error occured...", Toast.LENGTH_SHORT).show()

                                }

                            } else {
                                // yeh block jab execute hoga jab book favourites me hai tab
                                val async = BookDetailAsyncTask(applicationContext, bookEntity, 3).execute()
                                val result = async.get()
                                if (result) { Toast.makeText(this@BookDetailActivity, "Book remove from favourites", Toast.LENGTH_SHORT).show()
                                    btnFavourites.text = "Add to Favourites"
                                    val nofavColor = ContextCompat.getColor(applicationContext, color.black
                                        )
                                    btnFavourites.setBackgroundColor(nofavColor)

                                } else {
                                    Toast.makeText(this@BookDetailActivity, "Some error occured..", Toast.LENGTH_SHORT).show()

                                }


                            }
                        }


                    }

                } catch (e: JSONException) {
                    Toast.makeText(this@BookDetailActivity, "Wait..", Toast.LENGTH_SHORT).show()
                }


            },
                Response.ErrorListener {
                    Toast.makeText(
                        this@BookDetailActivity,
                        "Volley errror occured...",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "b4801aeaa63b02"

                    return headers
                }
            }
        queue.add(jsonRequest)

    }

    class BookDetailAsyncTask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        /*
        Mode 1 check db if book is favourites or not
        mode 2 check book into db as favourites
        Mode 3 remove book from the database

         */
        // room class databasebuilder method(context,class javaversion responsible for creation of db,database name)
        val db = Room.databaseBuilder(context, BookDatabase::class.java, "books-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    // check db if book is favourites or not
                    val book: BookEntity? = db.bookDao().getBookById(bookEntity.bookId.toString())
                    db.close()
                    return book != null


                }
                2 -> {
                    // save the book into db as favourites
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true

                }
                3 -> {
                    // remove the favourites book
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true


                }
            }


            return false
        }

    }

}