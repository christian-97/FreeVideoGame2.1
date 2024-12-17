package com.example.freevideogame.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.R
import com.example.freevideogame.databinding.LayoutCategoryIdBinding
import com.example.freevideogame.model.GameListResponse
import com.example.freevideogame.retrofit.RetrofitHelper
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryIdAdapter(
    private val context: Context,
    private val category: List<GameListResponse>,
    private val onItemSelected:(Int) -> Unit
) : RecyclerView.Adapter<CategoryIdAdapter.CategoryIdAdapterViewHolder>() {

    class CategoryIdAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LayoutCategoryIdBinding.bind(view)
        private val retrofit = RetrofitHelper.getInstace()

        fun bind(context: Context, category: GameListResponse, onItemSelected:(Int) -> Unit) {
            Picasso.get().load(category.thumbnail).into(binding.imageView)
            binding.tvTitle.text = category.title
            binding.tvDescription.text = category.short_description

            CoroutineScope(Dispatchers.IO).launch {
                //binding.progress.isVisible = true
                val response = retrofit.getGameDetails(category.id)

                response.body()?.let { gameDetails ->
                    withContext(Dispatchers.Main) {
                        binding.rvCategory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        binding.rvCategory.adapter = ScreenshotsAdapter(gameDetails.screenshots) { image ->
                            dialogScreenshots(context, image)
                        }
                        //binding.progress.isVisible = false
                    }
                }
            }

            binding.root.setOnClickListener {
                onItemSelected(category.id)
            }
        }

        private fun dialogScreenshots(context: Context, image: String) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_screenshots)

            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            val ivImage: ImageView = dialog.findViewById(R.id.imageViewScreen)
            val ivClose: ImageView = dialog.findViewById(R.id.ivClose)

            ivClose.setOnClickListener { dialog.dismiss() }

            Picasso.get().load(image).into(ivImage)
            dialog.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryIdAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(context).inflate(R.layout.layout_category_id, parent, false)
        return CategoryIdAdapterViewHolder(layoutInflater)
    }

    override fun getItemCount() = category.size

    override fun onBindViewHolder(holder: CategoryIdAdapterViewHolder, position: Int) {
        holder.bind(context, category[position], onItemSelected)
    }
}
