package com.shivam.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shivam.bookhub.R
import com.shivam.bookhub.database.BookEntity
import com.squareup.picasso.Picasso

class FavBookAdapter(private val context: Context, private val savedbookList:List<BookEntity>) :
    RecyclerView.Adapter<FavBookAdapter.FavBookViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavBookViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.favourites_book_data, parent, false)

        return FavBookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return savedbookList.size
    }

    override fun onBindViewHolder(holder: FavBookViewHolder, position: Int) {
        val favBooks = savedbookList.get(position)

        holder.favbookName.text = favBooks.bookName
        holder.favbookAuthor.text = favBooks.bookAuthor
        holder.favbookPrice.text = favBooks.bookPrice
        holder.favbookRating.text = favBooks.bookRating
        Picasso.get().load(favBooks.bookImage).into(holder.favbookImage)
    }


    class FavBookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var favbookName: TextView = view.findViewById(R.id.favBookName)
        val favbookAuthor: TextView = view.findViewById(R.id.favBookAuthor)
        val favbookPrice: TextView = view.findViewById(R.id.favBookPrice)
        val favbookRating: TextView = view.findViewById(R.id.favBookRating)
        val favbookImage: ImageView = view.findViewById(R.id.favBookImage)

    }
}