package com.example.freevideogame

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.freevideogame.databinding.ActivityAccountBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    private var db = Firebase.firestore

    private var isFirstName: Boolean = true
    private var isLastName: Boolean = true

    private var isButton: Boolean = false

    // ----------------------------------------------------------------
    private var firstName: String? = null
    private var lastName: String? = null
    private var password: String? = null
    // ----------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponent()

        val email = intent.getStringExtra("email").toString()

        binding.etEmail.setText(email)


        binding.ivReturn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.tvEdit.setOnClickListener {
            if (binding.tvEdit.text.toString() == "Cerrar") {
                binding.tvSave.isVisible = false
                binding.etFirstName.isEnabled = false
                binding.etLastName.isEnabled = false
                binding.tvEdit.text = "Editar"
                binding.etFirstName.setTextColor(ContextCompat.getColor(this, R.color.grey))
                binding.etLastName.setTextColor(ContextCompat.getColor(this, R.color.grey))

                datos(email)
                binding.etFirstName.setText(firstName)
                binding.etLastName.setText(lastName)

                binding.btnChanged.isEnabled = true
                binding.btnChanged.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
            else {
                binding.tvSave.isVisible = true
                binding.etFirstName.isEnabled = true
                binding.etLastName.isEnabled = true
                binding.tvEdit.text = "Cerrar"
                binding.etFirstName.requestFocus()
                binding.etFirstName.setSelection(binding.etFirstName.text.toString().length)
                binding.etFirstName.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.etLastName.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.btnChanged.isEnabled = false
                binding.btnChanged.setTextColor(ContextCompat.getColor(this, R.color.grey))
            }
        }

        datos(email)
        binding.tvSave.setOnClickListener {
            password?.let { it1 -> updateUserData(email, it1) }
            binding.btnChanged.isEnabled = true
            binding.btnChanged.setTextColor(ContextCompat.getColor(this, R.color.white))

        }

        binding.btnChanged.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_change_password)
            dialog.setCancelable(false)
            // ----------------------------------------------------------------
            val btnUpdate: Button = dialog.findViewById(R.id.btnUpdate)
            val btnCancel: Button = dialog.findViewById(R.id.btnCancel)

            val tilPassword: TextInputLayout = dialog.findViewById(R.id.tilPassword)
            val tilConfirm: TextInputLayout = dialog.findViewById(R.id.tilConfirm)

            val etPassword: TextInputEditText = dialog.findViewById(R.id.etPassword)
            val etPasswordConfirm: TextInputEditText = dialog.findViewById(R.id.etConfirm)

            etPassword.requestFocus()
            tilPassword.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
            tilPassword.boxStrokeColor = ContextCompat.getColor(this, R.color.blue)
            // ----------------------------------------------------------------
            setFocusChangeListener(etPassword, tilPassword, R.color.blue)
            setFocusChangeListener(etPasswordConfirm, tilConfirm, R.color.blue)
            // ----------------------------------------------------------------

            // ----------------------------------------------------------------
            etPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                    val password = etPassword.text.toString()

                    if (password.length in 8..30) {
                        val hasNumber = password.contains(Regex("[0-9]"))
                        val hasSymbol = password.contains(Regex("[^A-Za-z0-9]"))

                        if (!hasNumber || !hasSymbol) {
                            tilPassword.helperText = "Elija una contraseña más segura. Debe tener letras, número y símbolo."
                            tilPassword.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.yellow)))
                            isButton = false
                        } else {
                            tilPassword.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.green)))
                            tilPassword.helperText = "Contraseña segura"
                            isButton = true
                        }
                    } else {
                        tilPassword.error = "Debe tener 8 caracteres o más para su contraseña"
                        isButton = false

                    }
                    isVerifiedButton(btnUpdate)
                }

                override fun afterTextChanged(editable: Editable?) {}
            })
            // ----------------------------------------------------------------

            btnUpdate.setOnClickListener {
                val passwords = etPassword.text.toString()
                val passwordConfirm = etPasswordConfirm.text.toString()

                if (passwords == passwordConfirm) {
                    password = passwords
                    updateUserData(email, passwords)
                    dialog.dismiss()
                }
                else {
                    etPasswordConfirm.requestFocus()
                    etPasswordConfirm.setSelection(etPasswordConfirm.text.toString().length)
                    tilConfirm.error = "Las contraseñas no coinciden"
                }
            }

            btnCancel.setOnClickListener { dialog.dismiss() }

            dialog.show()
        }

    }

    private fun updateUserData(email: String, password: String) {
        db.collection("User")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val documentId = querySnapshot.documents[0].id

                    val userUpdates = hashMapOf<String, Any>(
                        "firstName" to binding.etFirstName.text.toString(),
                        "lastName" to binding.etLastName.text.toString(),
                        "password" to password
                    )

                    db.collection("User").document(documentId)
                        .update(userUpdates)
                        .addOnSuccessListener {
                            binding.tvSave.isVisible = false
                            binding.etFirstName.isEnabled = false
                            binding.etLastName.isEnabled = false
                            binding.tvEdit.text = "Editar"
                            binding.etFirstName.setTextColor(ContextCompat.getColor(this, R.color.grey))
                            binding.etLastName.setTextColor(ContextCompat.getColor(this, R.color.grey))
                            editSuccessful()
                        }
                        .addOnFailureListener { e -> }
                } else {
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al buscar el documento", Toast.LENGTH_LONG).show()
            }
    }

    private fun setFocusChangeListener(
        editText: EditText,
        textInputLayout: TextInputLayout,
        colorResId: Int
    ) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            val color = ContextCompat.getColor(this, colorResId)
            if (hasFocus) {
                if (editText.text.toString().isEmpty()) {
                    textInputLayout.error = null
                    textInputLayout.hintTextColor = ColorStateList.valueOf(color)
                    textInputLayout.boxStrokeColor = color
                }
            }
            if (!hasFocus) {
                if (editText.text.toString().isEmpty()) textInputLayout.error = "Ingrese una contraseña válida"
                else textInputLayout.error = null
            }
        }
    }


    private fun initComponent() {
        setFocusChangeListener1(binding.etFirstName, binding.tilFirstName, R.color.blue)
        setFocusChangeListener1(binding.etLastName, binding.tilLastName, R.color.blue)

        binding.etFirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.etFirstName.text.toString().isEmpty()) {
                    binding.etFirstName.error = "Complete el campo"
                    isFirstName = false
                }
                else {
                    binding.etFirstName.error = null
                    isFirstName = true
                }
                enable()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.etLastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.etLastName.text.toString().isEmpty()) {
                    binding.etLastName.error = "Complete el campo"
                    isLastName = false
                }
                else {
                    binding.etLastName.error = null
                    isLastName = true
                }
                enable()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }


    private fun setFocusChangeListener1(
        editText: EditText,
        textInputLayout: TextInputLayout,
        colorResId: Int
    ) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            val color = ContextCompat.getColor(this, colorResId)
            if (hasFocus) {
                textInputLayout.hintTextColor = ColorStateList.valueOf(color)
                textInputLayout.boxStrokeColor = color
                editText.setSelection(editText.text.toString().length)
            }
        }
    }



    private fun editSuccessful() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.edit_successful, null)

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

    private fun enable() {
        if (isFirstName && isLastName) {
            binding.tvSave.isEnabled = true
            binding.tvSave.setTextColor(ContextCompat.getColor(this@AccountActivity, R.color.email))

        } else {
            binding.tvSave.isEnabled = false
            binding.tvSave.setTextColor(ContextCompat.getColor(this@AccountActivity, R.color.grey))
        }
    }

    private fun isVerifiedButton(btnUpdate: Button) {
        if (isButton) {
            btnUpdate.setTextColor(ContextCompat.getColor(this@AccountActivity, R.color.white))
            btnUpdate.isEnabled = true
        }
        else {
            btnUpdate.setTextColor(ContextCompat.getColor(this@AccountActivity, R.color.grey))
            btnUpdate.isEnabled = false
        }
    }

    private fun datos(email: String) {
        db.collection("User")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.data["email"].toString() == email) {
                        firstName = document.data["firstName"].toString()
                        lastName = document.get("lastName").toString()
                        password = document.get("password").toString()

                        binding.etFirstName.setText(firstName)
                        binding.etLastName.setText(lastName)
                    }

                }
            }
    }
}
