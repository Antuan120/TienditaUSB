package com.juniors.tienditausb

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.juniors.tienditausb.Anuncios.CrearAnuncio
import com.juniors.tienditausb.Fragmentos.FragmentChats
import com.juniors.tienditausb.Fragmentos.FragmentCuenta
import com.juniors.tienditausb.Fragmentos.FragmentInicio
import com.juniors.tienditausb.Fragmentos.FragmentMisAnuncios
import com.juniors.tienditausb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase


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
                else -> {
                    false
                }
            }
        }

        binding.FAB.setOnClickListener {
            startActivity(Intent(this, CrearAnuncio::class.java))
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
        binding.TituloRl.text = "Anuncios"
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