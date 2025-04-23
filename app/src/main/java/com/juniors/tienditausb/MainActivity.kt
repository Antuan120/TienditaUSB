package com.juniors.tienditausb

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.juniors.tienditausb.Fragmentos.FragmentChats
import com.juniors.tienditausb.Fragmentos.FragmentCuenta
import com.juniors.tienditausb.Fragmentos.FragmentInicio
import com.juniors.tienditausb.Fragmentos.FragmentMisAnuncios
import com.juniors.tienditausb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        verFragmentInicio()

        binding.bottomNV.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Item_Inicio -> {
                    verFragmentInicio()
                    true
                }

                R.id.Item_Chats -> {
                    verFragmentChats()
                    true
                }

                R.id.Item_Mis_Anuncios -> {
                    verFragmentMisAnuncios()
                    true
                }

                R.id.Item_Cuenta -> {
                    verFragmentCuenta()
                    true
                }
                else -> false
            }
        }


                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun comprobarSesion() {
        if (firebaseAuth.currentUser == null){
            startActivity(Intent(this, OpcionesLogin::class.java))
            finishAffinity()
        }
    }

    private fun verFragmentInicio(){
        binding.TituloRl.text = "Inicio"
        val fragment = FragmentInicio()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id, fragment, "fragmentInicio")
        fragmentTransition.commit()
    }
    private fun verFragmentChats(){
        binding.TituloRl.text = "Chats"
        val fragment = FragmentChats()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id, fragment, "FragmentChats")
        fragmentTransition.commit()
    }
    private fun verFragmentMisAnuncios(){
        binding.TituloRl.text = "Mis Anuncios"
        val fragment = FragmentMisAnuncios()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id, fragment, "FragmentMisAnuncios")
        fragmentTransition.commit()
    }
    private fun verFragmentCuenta(){
        binding.TituloRl.text = "Cuenta"
        val fragment = FragmentCuenta()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id, fragment, "FragmentCuenta")
        fragmentTransition.commit()
    }
}