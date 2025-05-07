package com.juniors.tienditausb.Fragmentos

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.juniors.tienditausb.Adaptadores.AdaptadorAnuncio
import com.juniors.tienditausb.Adaptadores.AdaptadorCategoria
import com.juniors.tienditausb.Costantes
import com.juniors.tienditausb.Modelo.ModeloAnuncio
import com.juniors.tienditausb.Modelo.ModeloCategoria
import com.juniors.tienditausb.R
import com.juniors.tienditausb.RvListenerCategoria
import com.juniors.tienditausb.SeleccionarUbicacion
import com.juniors.tienditausb.databinding.FragmentInicioBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FragmentInicio : Fragment() {


    private lateinit var binding : FragmentInicioBinding


    private companion object{
        private const val MAX_DISTANCIA_MOSTRAR_ANUNCIO = 10
    }

    private lateinit var mContext : Context

    private lateinit var anuncioArrayList : ArrayList<ModeloAnuncio>
    private lateinit var adaptadorAnuncio : AdaptadorAnuncio
    private lateinit var locacionSP : SharedPreferences

    private var actualLatitud = 0.0
    private var actualLongitud = 0.0
    private var actualDireccion = ""

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentInicioBinding.inflate(LayoutInflater.from(mContext),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locacionSP = mContext.getSharedPreferences("LOCACION_SP", Context.MODE_PRIVATE)

        actualLatitud = locacionSP.getFloat("ACTUAL_LATITUD", 0.0f).toDouble()
        actualLongitud = locacionSP.getFloat("ACTUAL_LONGITUD", 0.0f).toDouble()
        actualDireccion = locacionSP.getString("ACTUAL_DIRECCION", "")!!

        if (actualLatitud != 0.0 && actualLongitud != 0.0) {
            binding.TvLocacion.text = actualDireccion
        }

        cargarCategorias()
        cargarAnuncios("Todos")

        binding.TvLocacion.setOnClickListener {
            val intent = Intent(mContext, SeleccionarUbicacion::class.java)
            seleccionarUbicacionARL.launch(intent)
        }

    }

    private val seleccionarUbicacionARL = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){resultado->
        if (resultado.resultCode == Activity.RESULT_OK){
            val data = resultado.data
            if (data!=null){
                actualLatitud = data.getDoubleExtra("latitud", 0.0)
                actualLongitud = data.getDoubleExtra("longitud",0.0)
                actualDireccion = data.getStringExtra("direccion").toString()

                locacionSP.edit()
                    .putFloat("ACTUAL_LATITUD", actualLatitud.toFloat())
                    .putFloat("ACTUAL_LONGITUD", actualLongitud.toFloat())
                    .putString("ACTUAL_DIRECCION", actualDireccion)
                    .apply()

                binding.TvLocacion.text = actualDireccion

                cargarAnuncios("Todos")
            }else{
                Toast.makeText(
                    context, "Cancelado",Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun cargarCategorias(){
        val categoriaArrayList = ArrayList<ModeloCategoria>()
        for (i in 0 until Costantes.categorias.size){
            val modeloCategoria = ModeloCategoria(Costantes.categorias[i], Costantes.categoriasIcono[i])
            categoriaArrayList.add(modeloCategoria)
        }

        val adaptadorCategoria = AdaptadorCategoria(
            mContext,
            categoriaArrayList,
            object : RvListenerCategoria{
                override fun onCategoriaClick(modeloCategoria: ModeloCategoria) {
                    val categoriaSeleccionada = modeloCategoria.categoria
                    cargarAnuncios(categoriaSeleccionada)
                }
            }
        )

        binding.categoriaRv.adapter = adaptadorCategoria
    }

    private fun cargarAnuncios(categoria : String){
        anuncioArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Anuncios")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                anuncioArrayList.clear()
                for (ds in snapshot.children){
                    try {
                        val modeloAnuncio = ds.getValue(ModeloAnuncio::class.java)
                        val distancia = calcularDistanciaKM(
                            modeloAnuncio?.latitud ?: 0.0,
                            modeloAnuncio?.longitud ?: 0.0
                        )
                        if (categoria == "Todos"){
                            if (distancia <= MAX_DISTANCIA_MOSTRAR_ANUNCIO){
                                anuncioArrayList.add(modeloAnuncio!!)
                            }
                        }else{
                            if (modeloAnuncio!!.categoria.equals(categoria)){
                                if (distancia <= MAX_DISTANCIA_MOSTRAR_ANUNCIO){
                                    anuncioArrayList.add(modeloAnuncio)
                                }
                            }
                        }
                    }catch (e:Exception){

                    }
                }
                adaptadorAnuncio = AdaptadorAnuncio(mContext, anuncioArrayList)
                binding.anunciosRv.adapter = adaptadorAnuncio

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun calcularDistanciaKM(latitud : Double , longitud : Double) : Double{
        val puntoPartida = Location(LocationManager.NETWORK_PROVIDER)
        puntoPartida.latitude = actualLatitud
        puntoPartida.longitude = actualLongitud

        val puntoFinal = Location(LocationManager.NETWORK_PROVIDER)
        puntoFinal.latitude = latitud
        puntoFinal.longitude = longitud

        val distanciaMetros = puntoPartida.distanceTo(puntoFinal).toDouble()
        return distanciaMetros/1000
    }


}