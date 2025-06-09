package com.juniors.tienditausb.Opciones_login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.juniors.tienditausb.MainActivity
import com.juniors.tienditausb.R
import com.juniors.tienditausb.RecuperarPassword
import com.juniors.tienditausb.Registro_email
import com.juniors.tienditausb.databinding.ActivityLoginEmailBinding

class Login_email : AppCompatActivity() {

    private lateinit var binding : ActivityLoginEmailBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor espere")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.BtnIngresar.setOnClickListener {
            validarDatos()

        }

       binding.TxtRegistrarme.setOnClickListener {
           startActivity(Intent(this@Login_email, Registro_email::class.java))
       }

        binding.TvRecuperar.setOnClickListener {
            startActivity(Intent(this@Login_email, RecuperarPassword::class.java))
        }

    }
    private var email = ""
    private var password = ""

    private fun validarDatos() {
email= binding.EtEmail.text.toString().trim()
        password= binding.EtPassword.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EtEmail.error = "Email inválido"
            binding.EtEmail.requestFocus()
        }
        else if(email.isEmpty()){
            binding.EtEmail.error = "Escribe tu email"
            binding.EtEmail.requestFocus()
            }
        else if(password.isEmpty()){
            binding.EtPassword.error = "Escribe tu contraseña"
            binding.EtPassword.requestFocus()
        }
        else{
            LoginUsuario()
        }

        }

    private fun LoginUsuario() {
        progressDialog.setMessage("Ingresando")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
                Toast.makeText(
                    this,
                    "Bienvenido(a)",
                    Toast.LENGTH_SHORT

                ).show()

            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se pudo iniciar sesión debido a ${e.message}",
                    Toast.LENGTH_SHORT

                ).show()

            }
    }
}
