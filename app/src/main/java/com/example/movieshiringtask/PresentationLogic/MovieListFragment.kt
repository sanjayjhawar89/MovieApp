package com.example.movieshiringtask.PresentationLogic

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieshiringtask.R
import com.example.movieshiringtask.businesslogic.Search
import com.example.movieshiringtask.businesslogic.Status
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment() {

    private val movieListFragmentVIewModel : MovieListFragmentVIewModel by viewModel()

    private lateinit var moviesListAdapter: MoviesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_movie_list , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(toolbar)
        activity?.supportActionBar?.apply {
            title = ""
        }
        initAdapter()
        observeData()
    }

    private fun initAdapter() {
        textError.setOnClickListener { movieListFragmentVIewModel.retry() }
        movieList.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }
        moviesListAdapter = MoviesListAdapter()
        movieList.adapter = moviesListAdapter
        movieListFragmentVIewModel.movieList.observe(viewLifecycleOwner, Observer<PagedList<Search>> {
            moviesListAdapter.submitList(it)
        })
    }

    private fun observeData() {
        movieListFragmentVIewModel.status.observe(this, Observer<Status> {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    movieListFragmentVIewModel.loadApiData(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }
}