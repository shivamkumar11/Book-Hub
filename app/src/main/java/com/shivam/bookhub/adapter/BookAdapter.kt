package com.shivam.bookhub.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.shivam.bookhub.BookDetailActivity
import com.shivam.bookhub.R
import com.squareup.picasso.Picasso

class BookAdapter(private val context: Context, private val data: ArrayList<Book>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.data_item, parent, false)

        return BookViewHolder(view)

    }

    override fun getItemCount(): Int {
        return data.size

    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val books = data[position]
        holder.bookTitle.text = books.bookName
        // holder.bookImage=books.bookImage
        holder.bookAuthor.text = books.bookAuthor
        holder.bookPrice.text = books.bookPrice
        holder.bookRatingNo.text = books.bookRating
        Picasso.get().load(books.bookImage).into(holder.bookImage)


        // books.bookImage?.let { holder.bookImage.setImageResource(it) }

        holder.itemView.setOnClickListener {
            val a = Intent(context,BookDetailActivity::class.java)
            a.putExtra("book_id",books.bookId)
            context.startActivity(a)

            when (position) {
                0 -> {
                    Toast.makeText(context, "hi$position", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    Toast.makeText(context, "hi$position", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    Toast.makeText(context, "hi$position", Toast.LENGTH_SHORT).show()
                }
                3 -> {
                    Toast.makeText(context, "hi$position", Toast.LENGTH_SHORT).show()
                }
                4 -> {
                    Toast.makeText(context, "hi$position", Toast.LENGTH_SHORT).show()
                }
                5 -> {
                    Toast.makeText(context, "hi$position", Toast.LENGTH_SHORT).show()
                }
                6 -> {
                    Toast.makeText(context, "hi$position", Toast.LENGTH_SHORT).show()
                }
                7 -> {
                    Toast.makeText(context, "hi$position", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var bookTitle: TextView = view.findViewById(R.id.bookTitle)
        val bookAuthor: TextView = view.findViewById(R.id.bookAuthor)
        val bookPrice: TextView = view.findViewById(R.id.bookPrice)
        val bookRatingNo: TextView = view.findViewById(R.id.bookRatingNo)
        val bookImage: ImageView = view.findViewById(R.id.favBookImage)
    }


}