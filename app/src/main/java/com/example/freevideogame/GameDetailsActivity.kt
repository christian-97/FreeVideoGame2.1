package com.example.freevideogame

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freevideogame.adapter.ScreenshotsAdapter
import com.example.freevideogame.databinding.ActivityGameDetailsBinding
import com.example.freevideogame.model.GameDetailsResponse
import com.example.freevideogame.retrofit.RetrofitHelper
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailsBinding
    private  val retrofit = RetrofitHelper.getInstace()
    val db = Firebase.firestore

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    var isExpanded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // -------------------------------------
        val spannableString = SpannableString(binding.tvNIC.text.toString())

        val color = ContextCompat.getColor(this, R.color.blue)
        spannableString.setSpan(ForegroundColorSpan(color), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(color), 5, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(color), 11, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvNIC.text = spannableString
        // -------------------------------------

        // --------------------------------
        val decorView = window.decorView.findViewById<ViewGroup>(android.R.id.content)

        binding.blurView.setupWith(decorView, RenderScriptBlur(this))
            .setFrameClearDrawable(decorView.background)
            .setBlurRadius(10f)

        binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView.clipToOutline = true
        // --------------------------------

        val id: Int = intent.getIntExtra(EXTRA_ID, 0)
        getGameInformation(id)

        val shared = getSharedPreferences("LoginData", Context.MODE_PRIVATE)
        val email = shared.getString("email", "").toString()

        binding.ivReturn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        binding.ivFavorite.setOnClickListener {
            checkIfFavoriteExists(id, email)
        }
        checkFavoriteStatus(id)
    }

    private fun checkFavoriteStatus(id: Int) {
        db.collection("Favorite")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) binding.ivFavorite.setColorFilter(ContextCompat.getColor(this, R.color.white))
                else binding.ivFavorite.setColorFilter(ContextCompat.getColor(this, R.color.email))
            }
    }

    private fun checkIfFavoriteExists(id: Int, email: String) {
        db.collection("Favorite")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    addFavorite(id, email)
                    animate(binding.ivFavorite)
                } else {
                    deleteFavorite(result.documents.first().id)
                    binding.ivFavorite.setColorFilter(ContextCompat.getColor(this, R.color.white))
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al comprobar: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun addFavorite(id: Int, email: String) {
        val favorite = hashMapOf(
            "id" to id,
            "email" to email,
        )

        db.collection("Favorite")
            .add(favorite)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Éxito en la colección", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteFavorite(documentId: String) {
        db.collection("Favorite")
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Cancelar el éxito de la colección", Toast.LENGTH_SHORT).show()
            }
    }


    private fun animate(image: ImageView) {
        val color = this.let { ContextCompat.getColor(it,R.color.email) }
        image.setColorFilter(color)

        image.animate()
            .scaleX(1.2f)
            .scaleY(1.2f)
            .setDuration(300)
            .withEndAction {

                image.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(300)
                    .start()
            }
            .start()
    }

    private fun getGameInformation(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGameDetails(id)

            if(response.body() != null) {
                runOnUiThread {
                    createUI(response.body()!!)
                }
            }
        }
    }

    private fun webSide(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun createUI(game: GameDetailsResponse) {
        binding.btnPlay.setOnClickListener { webSide(game.game_url) }
        binding.tvProfile.setOnClickListener { webSide(game.freetogame_profile_url) }
        binding.tvSitemap.setOnClickListener { webSide("https://www.freetogame.com/html-sitemap")}
        // ----------------------------------------------------------------
        val platform = game.platform
        if (platform == "Windows") {
            binding.tvPlatform.text = "Windows"
            binding.ivIcon.setImageResource(R.drawable.windows)
        }
        else  {
            binding.tvPlatform.text = "Browser"
            binding.ivIcon.setImageResource(R.drawable.icon_browser)
        }
        // ----------------------------------------------------------------

        Picasso.get().load(game.thumbnail).into(binding.ivGame)


        binding.tvTitle.text = game.title
        binding.tvTextTitle.text = game.title
        binding.textViewTitle.text = "Acerca de " + game.title
        binding.tvStatus.text = "Estado: " + game.status
        binding.tvShortDescription.text = game.short_description

        binding.tvOs.text = game.minimum_system_requirements?.os ?: ""
        binding.tvProcessor.text = game.minimum_system_requirements?.processor ?: ""
        binding.tvMemory.text = game.minimum_system_requirements?.memory ?: ""
        binding.tvGraphics.text = game.minimum_system_requirements?.graphics ?: ""
        binding.tvStorage.text = game.minimum_system_requirements?.storage ?: ""

        binding.tvReleaseDate.text = game.release_date
        binding.tvDeveloper.text = game.developer
        binding.tvPublisher.text = game.publisher
        binding.tvGenre.text = game.genre



        val fullText = game.description
        val truncatedText = fullText.take(100) + "...  dus"
        updateTextView(fullText, truncatedText)

        // ----------------------------------------------------------------
        binding.rvImage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvImage.adapter = ScreenshotsAdapter(game.screenshots) { dialogScreenshots(it) }
        // ----------------------------------------------------------------

    }

    private fun dialogScreenshots(image: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_screenshots)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val ivImage: ImageView = dialog.findViewById(R.id.imageViewScreen)
        val ivClose: ImageView = dialog.findViewById(R.id.ivClose)

        ivClose.setOnClickListener { dialog.hide() }

        Picasso.get().load(image).into(ivImage)

        dialog.show()
    }

    // ------------------------------------------------
    fun updateTextView(fullText: String, truncatedText: String) {
        val currentHeight = binding.tvDescription.height
        binding.tvDescription.text = if (isExpanded) truncatedText else fullText


        binding.tvDescription.measure(
            View.MeasureSpec.makeMeasureSpec(binding.tvDescription.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.UNSPECIFIED
        )
        val targetHeight = binding.tvDescription.measuredHeight


        val animator = ValueAnimator.ofInt(currentHeight, targetHeight).apply {
            duration = 300
            addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Int
                binding.tvDescription.layoutParams.height = animatedValue
                binding.tvDescription.requestLayout()
            }
            doOnEnd {
                if (isExpanded) {
                    binding.tvDescription.text = truncatedText
                } else {
                    binding.tvDescription.text = fullText
                }
                addClickableSpan(fullText, truncatedText)
            }
        }

        animator.start()
        isExpanded = !isExpanded
    }


    private fun addClickableSpan(fullText: String, truncatedText: String) {
        val spannableString = SpannableString(
            if (isExpanded) "$fullText Ver menos" else "$truncatedText Ver más"
        )
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                updateTextView(fullText, truncatedText)
            }
        }
        val actionText = if (isExpanded) "Ver menos" else "Ver más"
        spannableString.setSpan(
            clickableSpan,
            spannableString.length - actionText.length,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvDescription.text = spannableString
        binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()
    }

}