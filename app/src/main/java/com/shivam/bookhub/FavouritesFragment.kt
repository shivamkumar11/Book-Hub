package com.shivam.bookhub

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shivam.bookhub.adapter.Book
import com.shivam.bookhub.adapter.FavBookAdapter
import com.shivam.bookhub.database.BookEntity
import com.shivam.bookhub.db.BookDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouritesFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
  //  lateinit var progressLayout:RelativeLayout
    var savedbookList = listOf<BookEntity>()
   // lateinit var progressBar:ProgressBar
    lateinit var managers: RecyclerView.LayoutManager
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        recyclerView = view.findViewById(R.id.favRecyclerView)
        //progressLayout = view.findViewById(R.id.progressLayout)
      //  progressBar = view.findViewById(R.id.progressBar)


        savedbookList=RetreiveFavAsyscBook(activity as Context).execute().get()

        if(savedbookList!=null && activity!=null){
          //  progressLayout.visibility=View.GONE
          //  progressBar.visibility=View.GONE
            managers = GridLayoutManager(activity as Context, 2)
            recyclerView.layoutManager = managers
            val adapter = FavBookAdapter(activity as Context, savedbookList)
            recyclerView.adapter = adapter

        }












        return view
    }

    class RetreiveFavAsyscBook(val context:Context):AsyncTask<Void,Void,List<BookEntity>>(){
        override fun doInBackground(vararg params: Void?): List<BookEntity> {
        val db= Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
           return db.bookDao().getAllBooks()

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavouritesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavouritesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}