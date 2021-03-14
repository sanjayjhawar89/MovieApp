package com.example.movieshiringtask

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieshiringtask.PresentationLogic.MainActivityVIewModel
import com.example.movieshiringtask.PresentationLogic.MoviesListAdapter
import com.example.movieshiringtask.businesslogic.Search
import com.example.movieshiringtask.businesslogic.Status
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val mainActivityVIewModel : MainActivityVIewModel by viewModel()

    private lateinit var moviesListAdapter: MoviesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = ""
        }
        initAdapter()
        observeData()
    }

    private fun initAdapter() {
        movieList.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }
        moviesListAdapter = MoviesListAdapter(this)
        movieList.adapter = moviesListAdapter
        mainActivityVIewModel.movieList.observe(this, Observer<PagedList<Search>> {
            moviesListAdapter.submitList(it)
        })
    }

    private fun observeData() {
        mainActivityVIewModel.status.observe(this, Observer<Status> {
            when(it){
                Status.Loading -> {
                    progress_circular.visibility = View.VISIBLE
                    progress_circular.visibility = View.GONE
                }
                Status.Error -> {
                    progress_circular.visibility = View.GONE
                    errorGroup.visibility = View.VISIBLE

                }
                Status.Done -> {
                    errorGroup.visibility = View.GONE
                    progress_circular.visibility = View.GONE
                }
            }
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