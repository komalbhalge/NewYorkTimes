package com.kb.nytimes.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.kb.nytimes.R
import com.kb.nytimes.data.model.Doc
import com.kb.nytimes.databinding.ItemPostArticleBinding

class SearchedNewsAdaper :
    RecyclerView.Adapter<SearchedNewsAdaper.SearchedArticleViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Doc>() {
        override fun areItemsTheSame(oldItem: Doc, newItem: Doc): Boolean {
            return oldItem.source == newItem.source
        }

        override fun areContentsTheSame(oldItem: Doc, newItem: Doc): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedArticleViewHolder {
        val binding =
            ItemPostArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchedArticleViewHolder(parent.context, binding, differ.currentList)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchedArticleViewHolder, position: Int) {

        val item = differ.currentList[position]
        var image_url = ""
        holder.binding.apply {
             item.multimedia?.let { mediaList ->
                if (!mediaList.isNullOrEmpty()) {
                     image_url =mediaList.get(0).url
                }
            }

            itemArticleTitle.text = item.headline.main
            itemPostDescription.text = item.abstract_tx
            itemPostAuthor.text = item.byline.original.ifBlank { "Unknown" }
            tvUrl.text = item.web_url
            itemArticleImage.load(image_url) {
                placeholder(R.drawable.ic_default_news)
                crossfade(true)
                crossfade(50)
                transformations(
                    RoundedCornersTransformation(
                        12f,
                        12f,
                        12f,
                        12f
                    )
                )
            }

            // on item click
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }


    // on item click listener
    private var onItemClickListener: ((Doc) -> Unit)? = null
    fun setOnItemClickListener(listener: (Doc) -> Unit) {
        onItemClickListener = listener
    }

    class SearchedArticleViewHolder(
        val context: Context,
        val binding: ItemPostArticleBinding,
        val articles: List<Doc>
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            binding.tvUrl.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            //Handle item click
            when (v.id) {
                binding.tvUrl.id -> openURLInBrowser(binding.tvUrl.text.toString())
            }

        }

        fun openURLInBrowser(url: String) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context?.startActivity(intent)
        }
    }
}
