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
import com.kb.nytimes.data.model.Article
import com.kb.nytimes.databinding.ItemPostArticleBinding

class ArticlesAdaptor : RecyclerView.Adapter<ArticlesAdaptor.NewsVH>() {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.source == newItem.source
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val binding =
            ItemPostArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsVH(parent.context, binding, differ.currentList)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {

        val item = differ.currentList[position]
        var image_url = ""
        holder.binding.apply {
            // TODO clean logic
            item.media?.let { mediaList ->
                if (!mediaList.isNullOrEmpty()) {
                    mediaList.get(0).media_metadata.let { metadataList ->
                        if (!metadataList.isNullOrEmpty()) {
                         image_url = metadataList.get(0).url
                        }
                    }
                }
            }

            if (item.title?.isBlank() ?: true) {
                itemArticleTitle.visibility = View.GONE
                itemPostDescription.visibility = View.GONE
                itemPostAuthor.visibility = View.GONE
                itemArticleImage.visibility = View.GONE
            } else {
                itemArticleTitle.text = item.title
                itemPostDescription.text = item.abstract_tx
                itemPostAuthor.text = item.byline.toString().ifBlank { "Unknown" }
                tvUrl.text = item.url
                itemArticleImage.load(image_url) {
                placeholder(R.drawable.ic_default_news)
                    crossfade(true)
                    crossfade(200)
                    transformations(
                        RoundedCornersTransformation(
                            12f,
                            12f,
                            12f,
                            12f
                        )
                    )
                }
            }

            // on item click
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    inner class NewsVH( val context: Context,
                       val binding: ItemPostArticleBinding,
                       val articles: List<Article>
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
    // on item click listener
    private var onItemClickListener: ((Article) -> Unit)? = null
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}
