package com.example.shownews

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shownews.models.Article
import com.example.shownews.repo.Repository


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: ArticleListAdapter
    private lateinit var viewModel: MainViewModel
    private var articlesList: MutableList<Article> = ArrayList()
    private lateinit var progressBarbLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBarbLayout = findViewById(R.id.progress_layout);
        progressBarbLayout.visibility = View.VISIBLE;

        if (!isInternetConnected()) {
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show();
        }

        recyclerView = findViewById(R.id.recyclerView)

        fetchData()
        mAdapter= ArticleListAdapter(articlesList,this)

        recyclerView.adapter = mAdapter
        }

    private fun isInternetConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun fetchData() {
        val repo = Repository()
        val viewModelFactory = MainViewModelFactory(repo)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        viewModel.getNews()
        viewModel.myResponse.observe(this, Observer { response ->

            if (response != null) {
//                Log.d("Response", response.articles[0].title)
//                Log.d("Response", response.articles[0].author)
//                Log.d("Response", response.totalResults.toString())
                articlesList.addAll(response.articles)

                mAdapter.notifyDataSetChanged()
                progressBarbLayout.visibility = View.GONE
            } else {
                Log.e("TAG", "onFailure error")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_button -> {
                showDiaglogBox()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDiaglogBox() {
        val alert = AlertDialog.Builder(this@MainActivity)
        val mView = layoutInflater.inflate(R.layout.about_me_layout, null)
        val call: TextView = mView.findViewById(R.id.about_me_call)
        val email: TextView = mView.findViewById(R.id.about_me_email)
        val github: TextView = mView.findViewById(R.id.about_me_github)

        val resume: TextView = mView.findViewById(R.id.about_me_resume)
        alert.setView(mView)

        val alertDialog = alert.create()
        alertDialog.setCanceledOnTouchOutside(true)

        call.setOnClickListener { view: View? ->
            val uri = "tel:9716609164"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }

        email.setOnClickListener { view: View? ->
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:sumit.ryder22@gmail.com")
            startActivity(Intent.createChooser(intent, "Send Email"))
        }

        github.setOnClickListener { view: View? ->
            val url = "https://github.com/sumit0705/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        resume.setOnClickListener { view: View? ->
            val url =
                "https://drive.google.com/file/d/1HIsYHXGFDtkExVzi3JH_5XKnfVpHSNjF/view?usp=sharing"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        alertDialog.show()
    }
}





