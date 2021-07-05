package com.falanper.nybooksapp.presentation.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.falanper.nybooksapp.R
import com.falanper.nybooksapp.data.model.Book

class BooksAdapter(
    private val books: List<Book>, private val onItemClickListener: ((book: Book) -> Unit)
) :
    RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val itemBinding =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_book_item, parent, false)
        return BooksViewHolder(itemBinding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bindView(books[position])
    }

    override fun getItemCount(): Int = books.count()

    class BooksViewHolder(itemView: View, private val onItemClickListener: ((book: Book) -> Unit)) :
        RecyclerView.ViewHolder(itemView) {
        // cria a variavel vinculando o campo id do xml
        private val title: TextView = itemView.findViewById(R.id.textViewTitle)

        // cria a variavel vinculando o campo id do xml
        private val author: TextView = itemView.findViewById(R.id.textViewAuthor)

        fun bindView(book: Book) {
            // vincula no texto da variavel title o construtor da data class
            title.text = book.title
            // vincula no texto da variavel author o construtor da data class
            author.text = book.author

            itemView.setOnClickListener { onItemClickListener(book) }
        }
    }
}