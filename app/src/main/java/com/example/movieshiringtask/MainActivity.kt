package com.example.movieshiringtask

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.movieshiringtask.PresentationLogic.MainActivityVIewModel
import com.example.movieshiringtask.businesslogic.Search
import com.example.movieshiringtask.businesslogic.Status
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val mainActivityVIewModel : MainActivityVIewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = ""
        }
        observeData()
    }

    private fun observeData() {
        mainActivityVIewModel.status.observe(this, Observer<Status> {
            when(it){
                Status.Loading -> {
                    Timber.i("Sanju: Api statis is loading")
                    progress_circular.visibility = View.VISIBLE

                }
                Status.Error -> {
                    Timber.i("Sanju: Error statis is Error")
                    progress_circular.visibility = View.GONE
                    errorGroup.visibility = View.VISIBLE

                }
                Status.Done -> {
                    Timber.i("Sanju: Error statis is Done")
                    errorGroup.visibility = View.GONE
                    progress_circular.visibility = View.GONE
                }
            }
        })


        mainActivityVIewModel.movieList.observe(this, Observer<PagedList<Search>> {
            Log.d("Sanju" , "List size is  " + it.size)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    mainActivityVIewModel.loadApiData(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        return true
    }
}