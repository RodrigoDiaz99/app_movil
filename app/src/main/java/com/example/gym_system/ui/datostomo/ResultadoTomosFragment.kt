package com.example.gym_system.ui.datostomo

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
import androidx.fragment.app.FragmentTransaction
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentResultadoTomosBinding
import com.example.gym_system.interfaces.APIServices
import com.example.pruebadrawer.models.tomo.resultado.TomoClass
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

import com.example.pruebadrawer.interfaces.rutaancj

class ResultadoTomosFragment(
    iTipoPredio: Int,
    iVolumenPredio: Int,
    cLetraLibro: String,
    cFolioLibro: Int,
    iLibro: Int,
    cTomo: Int
) : Fragment(R.layout.fragment_resultado_tomos) {

    private val baseurl = rutaancj

    private lateinit var service: com.example.gym_system.interfaces.APIServices

    private val iTipoPredio = iTipoPredio
    private val iVolumenPredio = iVolumenPredio
    private val cLetraLibro = cLetraLibro
    private val cFolioLibro = cFolioLibro
    private val iLibro = iLibro
    private val cTomo = cTomo

    lateinit var title: TextView
    lateinit var llcontenedor: LinearLayout
    private val client = OkHttpClient()
    private val gson: Gson = GsonBuilder().create()
    val gson2 = Gson()
    lateinit var btnRegresa: Button

    private var _binding: FragmentResultadoTomosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println(baseurl)
        _binding = FragmentResultadoTomosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        llcontenedor = binding.llcontenedor
        btnRegresa = binding.btnRegresar

        btnRegresa.setOnClickListener {
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.replace(

                R.id.nav_host_fragment_content_main,
                DatosTomoFragment()

            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
        conexionApi(iTipoPredio, iVolumenPredio, cLetraLibro, cFolioLibro, iLibro, cTomo)
        return root
    }

    private fun conexionApi(
        iTipoPredio: Int,
        iVolumenPredio: Int,
        cLetraLibro: String,
        cFolioLibro: Int,
        iLibro: Int,
        cTomo: Int
    ) {
        println(iTipoPredio)
        println(iVolumenPredio)
        println(cLetraLibro)
        println(cFolioLibro)
        println(iLibro)
        println(cTomo)


        try {

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            service = retrofit.create(com.example.gym_system.interfaces.APIServices::class.java)
            loadSeguimiento(
                iTipoPredio,
                iVolumenPredio,
                cLetraLibro,
                cFolioLibro,
                iLibro,
                cTomo,
            )

        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadSeguimiento(
        iTipoPredio: Int,
        iVolumenPredio: Int,
        cLetraLibro: String,
        cFolioLibro: Int,
        iLibro: Int,
        cTomo: Int
    ) {
        var post: TomoClass
        var LetraTomo: String
        var Libro: String
        var RutaImagen: String
        var Sistema: String
        var TipoPredio: String
        var Tomo: String
        var Volumen: String
        var iIDLibrosRpp: String
        var visor: String

        try {
            println("prueba entrnado response")
            service.getTomo(
                iTipoPredio,
                iVolumenPredio,
                cLetraLibro,
                cFolioLibro,
                iLibro,
                cTomo
            )
                .enqueue(object : Callback<TomoClass> {

                    override fun onResponse(
                        call: Call<TomoClass>,
                        response: Response<TomoClass>
                    ) {


                        post = response.body()!!
                        if (response.isSuccessful) {

                            val json = gson2.toJson(post)
                            val jsonDecode = gson2.fromJson(json, TomoClass::class.java)
                            println(jsonDecode)
                            if (jsonDecode.isNotEmpty()) {
                                SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Genial")
                                    .setContentText("Se encontraron datos de esa solicitud")
                                    .show()
                                jsonDecode.forEach() {
                                    val txtdinamico = TextView(context)
                                    val btnseguimiento = Button(context)
                                    val espacio = TextView(context)
                                    txtdinamico.textSize = 20F
                                    txtdinamico.setTextColor(Color.parseColor("#000000"))
                                    txtdinamico.setBackgroundResource(R.drawable.font)
                                    btnseguimiento.setTextColor(Color.parseColor("#FFFFFF"))
                                    btnseguimiento.setBackgroundColor(Color.parseColor("#35A5E2"))

                                    Tomo = ("Tomo: " + it.cTomo)
                                    Volumen = ("Volumen: " + it.cVolumen)
                                    TipoPredio = ("Tipo: " + it.cTipoPredio)
                                    LetraTomo = ("Letra: " + it.cLetraTomo)
                                    Libro = ("Libro: " + it.cLibro)
                                    Sistema = (it.cSistema)
                                    RutaImagen = (it.cRutaImagen)
                                    visor = (it.cVisor)
                                    println(visor)

                                    llcontenedor.setBackgroundResource(R.drawable.bordecuadro)
                                    btnseguimiento.append("Ver Tomo")
                                    txtdinamico.append(Tomo + "\n")
                                    txtdinamico.append(Volumen + "\n")
                                    txtdinamico.append(TipoPredio + "\n")
                                    txtdinamico.append(LetraTomo + "\n")
                                    txtdinamico.append(Libro + "\n")
                                    txtdinamico.append(Sistema + "\n")

                                    llcontenedor.addView(txtdinamico)
                                    llcontenedor.addView(btnseguimiento)
                                    llcontenedor.addView(espacio)

                                    val fragmentManager = parentFragmentManager
                                    val fragmentTransaction: FragmentTransaction =
                                        fragmentManager.beginTransaction()
                                    btnseguimiento.setOnClickListener {
                                        fragmentTransaction.setReorderingAllowed(true);
                                        val url = visor
                                        println(url)
                                        val intent = Intent(Intent.ACTION_VIEW)
                                        intent.data = Uri.parse(url)
                                        startActivity(intent)
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();
                                    }

                                }
                            } else {
                                SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("No se encontraron datos de esa solicitud")
                                    .show()
                            }
                        } else {
                            throw Exception("No se pudo establecer conexión con la api de Datos Predio")
                        }
                    }

                    override fun onFailure(call: Call<TomoClass>, t: Throwable) {
                        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No se encontraron datos de esa solicitud")
                            .show()
                        t.printStackTrace()
                    }

                })


        } catch (e: Exception) {
            println(e.message + "hola")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}