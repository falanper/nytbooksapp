package com.falanper.nybooksapp.presentation.books

import android.os.Bundle
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.falanper.nybooksapp.R
import com.falanper.nybooksapp.databinding.ActivityBooksBinding
import com.falanper.nybooksapp.databinding.IncludeToolbarBinding
import com.falanper.nybooksapp.presentation.base.BaseActivity
import com.falanper.nybooksapp.presentation.details.BookDetailsActivity

class BooksActivity : BaseActivity() {

    private lateinit var binding: ActivityBooksBinding
    private lateinit var toolbar: Toolbar
    private lateinit var viewFlipperBooks: ViewFlipper
    private lateinit var textViewError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbarMain)

        setupToolbar(toolbar, R.string.books_title)

        // instanciando o viewModel sem nenhum parametro
        val viewModel: BooksViewModel = ViewModelProvider(this).get(BooksViewModel::class.java)

        viewModel.booksLiveData.observe(this, {
            it?.let { books ->
                with(binding.recyclerView) {
                    layoutManager =
                        LinearLayoutManager(this@BooksActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = BooksAdapter(books) { book ->
                        val intent = BookDetailsActivity.getStartIntent(
                            this@BooksActivity,
                            book.title,
                            book.description
                        )
                        this@BooksActivity.startActivity(intent)
                    }
                }
            }
        })

        viewModel.viewFlipperLiveData.observe(this, Observer {
            it?.let{viewFlipper ->
                viewFlipperBooks = findViewById(R.id.viewFlipperBooks)
                viewFlipperBooks.displayedChild = viewFlipper.first

                viewFlipper.second?.let { errorMessageResId ->
                    textViewError = findViewById(R.id.text_view_error)
                    textViewError.text = getString(errorMessageResId)
                }
            }
        })

        viewModel.getBooks()
    }
}