package com.example.freevideogame

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.freevideogame.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    var isPasswordValid = false
    var isUsernameValid = false

    val db = Firebase.firestore
    private var verifyDialog: Dialog? = null

    private  var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivReturn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.tilFirstName.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.violet))
        binding.tilFirstName.boxStrokeColor = ContextCompat.getColor(this, R.color.violet)
        binding.etFirstName.requestFocus()

        // =================================================================
        setFocusChangeListener(binding.etEmail, binding.tilEmail, R.color.violet)
        setFocusChangeListener(binding.etPassword, binding.tilPassword, R.color.violet)
        setFocusChangeListener(binding.etConfirm, binding.tilConfirm, R.color.violet)
        setFocusChangeListener(binding.etFirstName, binding.tilFirstName, R.color.violet)
        setFocusChangeListener(binding.etLastName, binding.tilLastName, R.color.violet)
        // =================================================================

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val password = binding.etPassword.text.toString()

                binding.tilConfirm.visibility = if (password.isNotEmpty()) View.VISIBLE else View.INVISIBLE

                if (password.length in 8..30) {
                    val hasNumber = password.contains(Regex("[0-9]"))
                    val hasSymbol = password.contains(Regex("[^A-Za-z0-9]"))

                    if (!hasNumber || !hasSymbol) {
                        binding.tilPassword.helperText = "Elija una contraseña más segura. Debe tener letras, número y símbolo."
                        binding.tilPassword.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.yellow)))
                        isPasswordValid = false
                    } else {
                        binding.tilPassword.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.green)))
                        binding.tilPassword.helperText = "Contraseña segura"
                        isPasswordValid = true
                    }
                } else {
                    binding.tilPassword.error = "Debe tener 8 caracteres o más para su contraseña"
                    isPasswordValid = false
                }
                updateRegisterButtonState()
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.etEmail.text.toString().isEmpty()) {
                    binding.tilConfirm.error = null
                }

            }

            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                if (email.isValidEmail()) {
                    binding.etEmail.error = null
                    isUsernameValid = true
                } else {
                    binding.etEmail.error = "Correo electrónico no válido"
                    isUsernameValid = false
                }
                updateRegisterButtonState()
            }
        })


        binding.tvRegister.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirm.text.toString()

            if (firstName.isEmpty()) {
                binding.etFirstName.requestFocus()
                binding.etFirstName.error = "Ingrese un nombre"
            } else if (lastName.isEmpty()) {
                binding.etLastName.requestFocus()
                binding.etLastName.error = "Ingrese un apellido"
            } else if (password != confirmPassword) {
                binding.etConfirm.requestFocus()
                binding.etConfirm.setSelection(binding.etConfirm.text.toString().length)
                binding.tilConfirm.error = "Las contraseñas no coinciden. Inténtalo de nuevo."
            } else {
                db.collection("User")
                    .get()
                    .addOnSuccessListener { result ->
                        var error = true
                        for (document in result) {
                            val email = document.data["email"].toString()
                            if (binding.etEmail.text.toString() == email) {
                                error = false
                                Toast.makeText(this, "La dirección de correo electrónico ya está en uso por otra cuenta", Toast.LENGTH_LONG).show()
                            }
                        }
                        if (error) {
                            //FirebaseAuth.getInstance().signOut()
                            binding.progressBar.isVisible = true
                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        sendEmailVerification()
                                        binding.tilConfirm.error = null
                                        binding.progressBar.isVisible = false
                                        showDialogVerify(email)

                                        val user = FirebaseAuth.getInstance().currentUser
                                        if (user != null) {
                                            val handler = Handler(Looper.getMainLooper())
                                            val checkVerification = object : Runnable {
                                                override fun run() {
                                                    user.reload().addOnCompleteListener {
                                                        if (user.isEmailVerified) {
                                                            dismissDialogVerify()
                                                            insert(email, password, firstName, lastName)
                                                            showDialog()
                                                        } else {
                                                            handler.postDelayed(this, 2000)
                                                        }
                                                    }
                                                }
                                            }
                                            handler.postDelayed(checkVerification, 2000)
                                        }
                                    }
                                    else {
                                        Toast.makeText(this, "La dirección de correo electrónico ya se lo había envíado", Toast.LENGTH_LONG).show()
                                    }
                                }
                        }
                    }
            }
        }
    }

    // ----------------------------------------------------------------
    private fun sendEmailVerification() {
        val user = firebaseAuth.currentUser
        user?.sendEmailVerification()
    }
    // ----------------------------------------------------------------

    private fun insert(email: String, password: String, firstName: String, lastName: String) {
        val user = hashMapOf(
            "email" to email,
            "password" to password,
            "firstName" to firstName,
            "lastName" to lastName,
        )

        db.collection("User")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
    }

    fun updateRegisterButtonState() {
        val isFormValid = isPasswordValid && isUsernameValid
        binding.tvRegister.isEnabled = isFormValid
        val color = if (isFormValid) R.color.white else R.color.grey
        binding.tvRegister.setTextColor(ContextCompat.getColor(this, color))
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_register)
        dialog.setCancelable(false)
        dialog.show()

        Handler(Looper.getMainLooper()).postDelayed( {
            dialog.dismiss()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            dialog.hide()
        }, 2500)
    }

    private fun showDialogVerify(email: String) {
        verifyDialog = Dialog(this).apply {
            setContentView(R.layout.dialog_verify)
            setCancelable(false)

            val tvEmail = findViewById<TextView>(R.id.tvEmail)
            val tvLogOut = findViewById<TextView>(R.id.tvLogOut)

            val text = getString(R.string.log_out); val spannableString = SpannableString(text)
            val color = getColor(R.color.email); val colorSpan = ForegroundColorSpan(color)
            spannableString.setSpan(colorSpan, 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            tvLogOut.text = spannableString

            val fullText = getString(R.string.verify_email, email)
            val spannable = SpannableString(fullText)

            val startIndex = fullText.indexOf(email)
            val endIndex = startIndex + email.length

            spannable.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.email)),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            tvEmail.text = spannable
            show()
        }
    }

    private fun dismissDialogVerify() {
        verifyDialog?.dismiss()
        verifyDialog = null
    }

    fun String.isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun setFocusChangeListener(
        editText: EditText,
        textInputLayout: TextInputLayout,
        colorResId: Int
    ) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            val color = ContextCompat.getColor(this, colorResId)
            if (hasFocus) {
                textInputLayout.hintTextColor = ColorStateList.valueOf(color)
                textInputLayout.boxStrokeColor = color
            }
        }
    }
}