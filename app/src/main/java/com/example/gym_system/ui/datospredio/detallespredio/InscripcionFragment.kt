package com.example.gym_system.ui.datospredio.detallespredio

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentInscripcionBinding
import com.example.gym_system.interfaces.APIServices
import com.example.pruebadrawer.models.detallespredio.InscripcionClass
import com.example.pruebadrawer.interfaces.rutaancj
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class InscripcionFragment(
    iIDPredio: Int
) : Fragment(R.layout.fragment_inscripcion) {

    private val baseurl = rutaancj
    private lateinit var service: com.example.gym_system.interfaces.APIServices

    private val iIDPredio = iIDPredio

    lateinit var title: TextView
    lateinit var llcontenedor: LinearLayout
    private val client = OkHttpClient()
    private val gson: Gson = GsonBuilder().create()
    val gson2 = Gson()

    private var _binding: FragmentInscripcionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInscripcionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        llcontenedor = binding.llcontenedor

        conexionApi(iIDPredio)
        // Inflate the layout for this fragment
        return root
    }


    private fun conexionApi(
        iIDPredio: Int,

        ) {
        println("entrado conexion api")
        try {

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            service = retrofit.create(com.example.gym_system.interfaces.APIServices::class.java)
            loadSeguimiento(
                iIDPredio
            )

        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadSeguimiento(
        iIDPredio: Int
    ) {
        var post: InscripcionClass
        var cDatosLibro: String
        var cFuenteConsulta: String
        var cGestor: String
        var cOperacion: String
        var cReferenciaLibro: String
        var cRutaImagen: String
        var didInscripcion: String
        var dtFechaInscripcion: String
        var iIDImagenLibro: String
        var iIDSeccion: String
        var lVigente: String
        var cVisor: String
        val cVerPDF: String

        try {
            service.getInscripcionPredio(
                iIDPredio
            )
                .enqueue(object : Callback<InscripcionClass> {
                    override fun onResponse(
                        call: Call<InscripcionClass>,
                        response: Response<InscripcionClass>
                    ) {

                        post = response.body()!!

                        if (response.isSuccessful) {

                            val json = gson2.toJson(post)
                            val jsonDecode = gson2.fromJson(json, InscripcionClass::class.java)
                            if (jsonDecode.lSuccess) {
                                jsonDecode.data.forEach {
                                    val txtdinamico = TextView(context)
                                    val btnseguimiento = Button(context)
                                    val espacio = TextView(context)
                                    txtdinamico.textSize = 20F
                                    txtdinamico.setTextColor(Color.parseColor("#000000"))
                                    txtdinamico.setBackgroundResource(R.drawable.font)
                                    btnseguimiento.setTextColor(Color.parseColor("#FFFFFF"))
                                    btnseguimiento.setBackgroundColor(Color.parseColor("#35A5E2"))

                                    didInscripcion = ("Inscripcion: " + it.didInscripcion)
                                    if (it.lVigente == "TRUE ") {
                                        lVigente = ("Vigente")
                                    } else {
                                        lVigente = ("NO Vigente")
                                    }
                                    dtFechaInscripcion = ("Fecha: " + it.dtFechaInscripcion)
                                    cOperacion = ("Manzana: " + it.cOperacion)
                                    cGestor = ("Nomenclatura: " + it.cGestor)
                                    cReferenciaLibro = ("Coloina: " + it.cReferenciaLibro)
                                    cVisor = (it.cVisor)

                                    llcontenedor.setBackgroundResource(R.drawable.bordecuadro)
                                    btnseguimiento.append("Ver Tomo")
                                    txtdinamico.append(didInscripcion + "\n")
                                    txtdinamico.append(lVigente + "\n")
                                    txtdinamico.append(dtFechaInscripcion + "\n")
                                    txtdinamico.append(cOperacion + "\n")
                                    txtdinamico.append(cGestor + "\n")
                                    txtdinamico.append(cReferenciaLibro + "\n")

                                    llcontenedor.addView(txtdinamico)
                                    llcontenedor.addView(btnseguimiento)
                                    llcontenedor.addView(espacio)

                                    btnseguimiento.setOnClickListener {
                                        val url = cVisor
                                        println(url)
                                        val intent = Intent(Intent.ACTION_VIEW)
                                        intent.data = Uri.parse(url)
                                        startActivity(intent)
                                    }

                                }
                            } else {
                                SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("No se encontraron datos de esa solicitud")
                                    .show()
                            }
                        } else {
                            throw Exception("No se pudo establecer conexión con la api de Datos Inscripcion")
                        }
                    }

                    override fun onFailure(call: Call<InscripcionClass>, t: Throwable) {
                        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No se encontraron datos de esa solicitud")
                            .show()
                        t.printStackTrace()
                    }

                })


        } catch (e: Exception) {
            println(e.message)
        }

    }
}