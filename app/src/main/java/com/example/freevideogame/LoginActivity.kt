package com.example.freevideogame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.widget.ProgressBar
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.freevideogame.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.firestore

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    private val db = Firebase.firestore
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        handleGoogleSignInResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SystemBarsUtil.hideSystemBars(this)

        loginWithGoogle()

        // ----------------------------------------------------------------
        val text = binding.tvTitle.text.toString()
        val spannableString = SpannableString(text)

        val color = ContextCompat.getColor(this, R.color.blue)
        spannableString.setSpan(ForegroundColorSpan(color), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(color), 5, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(color), 11, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvTitle.text = spannableString
        // ----------------------------------------------------------------

        // =================================================================

        binding.linkCreate.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.etUser.requestFocus()
        binding.tilUser.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
        binding.tilUser.boxStrokeColor = ContextCompat.getColor(this, R.color.blue)

        binding.etUser.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val user = binding.etUser.text.toString()
                if (user.isEmpty()) binding.tilUser.error = getString(R.string.error_user)
                else binding.tilUser.error = null
            }
            if(hasFocus) {
                val user = binding.etUser.text.toString()
                if (user.isEmpty()) {
                    binding.tilUser.error = null
                    binding.tilUser.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
                    binding.tilUser.boxStrokeColor = ContextCompat.getColor(this, R.color.blue)
                }
            }
        }

        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val password = binding.etPassword.text.toString()
                if (password.isEmpty()) binding.tilPassword.error = getString(R.string.error_password)
                else binding.tilPassword.error = null
            }
            if(hasFocus) {
                val password = binding.etPassword.text.toString()
                if (password.isEmpty()) {
                    binding.tilPassword.error = null
                    binding.tilPassword.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
                    binding.tilPassword.boxStrokeColor = ContextCompat.getColor(this, R.color.blue)
                }
            }
        }

        // =================================================================
        binding.btnStart.setOnClickListener {
            val user = binding.etUser.text.toString()
            val password = binding.etPassword.text.toString()

            if (user.isEmpty()) {
                binding.tilUser.error = getString(R.string.error_user)
            }
            else binding.tilUser.error = null
            if (password.isEmpty()) {
                binding.tilPassword.error = getString(R.string.error_password)
            }
            else binding.tilPassword.error = null

            if (user.isNotEmpty() && password.isNotEmpty()) {
                db.collection("User")
                    .get()
                    .addOnSuccessListener { result ->
                        var error = true
                        for (document in result) {
                            val email = document.data["email"].toString()
                            val pass = document.data["password"].toString()
                            val firstName = document.data["firstName"].toString()
                            val lastName = document.data["lastName"].toString()

                            if (user == email && password == pass) {
                                error = false
                                saveSession("$firstName $lastName", email)

                                val intent = Intent(this, IntroActivity::class.java)
                                startActivity(intent)
                                finish()
                                break
                            }
                        }
                        if (error) showError()
                    }
            }
        }
    }
    // =================================================================
    private fun showError() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null)

        val progressBar = bottomSheetView.findViewById<ProgressBar>(R.id.pb)


        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()

        val handler = Handler(Looper.getMainLooper())
        val totalDuration = 3000
        val updateInterval = 50
        val maxProgress = 100
        var currentProgress = 0

        val runnable = object : Runnable {
            override fun run() {

                currentProgress += maxProgress * updateInterval / totalDuration
                progressBar.progress = currentProgress

                if (currentProgress < maxProgress) {
                    handler.postDelayed(this, updateInterval.toLong())
                } else {
                    bottomSheetDialog.dismiss()
                }
            }
        }
        handler.post(runnable)
    }
    // =================================================================

    // =================================================================
    private fun loginWithGoogle() {
        binding.btnGoogle.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            val signInIntent = googleClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private fun handleGoogleSignInResult(result: ActivityResult) {
        val data = result.data
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                saveSession(account.displayName ?: "", account.email ?: "")
                                showLoading(account.displayName ?: "", account.email ?: "")
                            }
                        }
                }
            } catch (e: ApiException) {
                e.printStackTrace()

            }
        }
    }


    private fun showLoading(name: String, email: String) {
        val loadingIntent = Intent(this, IntroActivity::class.java).apply {
            putExtra("name", name)
            putExtra("email", email)
        }
        startActivity(loadingIntent)
        finish()
    }

    private fun saveSession(name: String, email: String) {
        val shared = getSharedPreferences("LoginData", Context.MODE_PRIVATE).edit()
        shared.putString("name", name)
        shared.putString("email", email)
        shared.apply()
    }

}