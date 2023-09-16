package com.shivam.bookhub

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.shivam.bookhub.adapter.Book
import com.shivam.bookhub.adapter.BookAdapter
import com.shivam.bookhub.util.ConnectionMnanager
import org.json.JSONException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var recyclerView: RecyclerView
    lateinit var btn: Button
    lateinit var managers: RecyclerView.LayoutManager
    private var param1: String? = null
    private var param2: String? = null
    lateinit var progressBar: ProgressBar

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
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val data = arrayListOf<Book>()
        /*  val data = arrayListOf<Book>(
              Book("A", "Rs.100", "book", "5.0", R.drawable.cover),
              Book("B", "Rs.200", "book", "5.0", R.drawable.cover),
              Book("C", "Rs.300", "book", "5.0", R.drawable.cover),
              Book("D", "Rs.400", "book", "5.0", R.drawable.cover),
              Book("E", "Rs.500", "book", "5.0", R.drawable.cover),
              Book("F", "Rs.600", "book", "5.0", R.drawable.cover),
              Book("G", "Rs.700", "book", "5.0", R.drawable.cover),
              Book("H", "Rs.800", "book", "5.0", R.drawable.cover)

          )*/



        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        btn = view.findViewById(R.id.btnC)
        managers = LinearLayoutManager(activity)


        btn.setOnClickListener {
            if (ConnectionMnanager().isDeviceNetworkConnected(activity as Context)) {
                Toast.makeText(activity as Context, "Connection Successfull..", Toast.LENGTH_SHORT)
                    .show()

            } else {
                Toast.makeText(
                    activity as Context,
                    "Plz connect your internet..",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (ConnectionMnanager().isDeviceNetworkConnected(activity as Context)) {
            progressBar.visibility = View.GONE
            try {
                val queue = Volley.newRequestQueue(activity as Context)
                val url = "http://13.235.250.119/v1/book/fetch_books/"

                val jsonObjectRequest =
                    object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                        val success = it.getBoolean("success")
                        if (success) {
                            val jsonArrayData = it.getJSONArray("data")
                            for (i in 0 until jsonArrayData.length()) {
                                val bookArrayObject = jsonArrayData.getJSONObject(i)
                                val bookObject = Book(
                                    bookArrayObject.getString("book_id"),
                                    bookArrayObject.getString("name"),
                                    bookArrayObject.getString("author"),
                                    bookArrayObject.getString("rating"),
                                    bookArrayObject.getString("price"),
                                    bookArrayObject.getString("image")
                                )
                                data.add(bookObject)
                            }

                            recyclerView.layoutManager = managers
                            val adapters = BookAdapter(activity as Context, data)
                            recyclerView.adapter = adapters


                        } else {
                            Toast.makeText(
                                context,
                                "Error occoured to retrieving data..",
                                Toast.LENGTH_SHORT
                            ).show()
                            //  println("Error to retrieving data..")
                        }
                        println("Response is $it")


                    }, Response.ErrorListener {
                        if (activity != null) {
                            Toast.makeText(context, "Volley errror occured...", Toast.LENGTH_SHORT)
                                .show()
                            //  println("Error to retrieving data..")
                            println("Error is $it")
                        }

                    }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Content-type"] = "application/json"
                            headers["token"] = "b4801aeaa63b02"

                            return headers
                        }
                    }
                queue.add(jsonObjectRequest)
            } catch (e: JSONException) {
                Toast.makeText(context, "Wait..", Toast.LENGTH_SHORT).show()

            }
        } else {
            progressBar.visibility = View.VISIBLE
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Internet Connection is not found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()

            }
            dialog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()


        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}