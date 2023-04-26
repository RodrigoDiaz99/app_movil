package com.example.gym_system.ui.datospredio

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.app_insejupy.models.predio.resultado.PredioClass
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentResultadoPredioBinding
import com.example.gym_system.interfaces.APIServices
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

class ResultadosPredioFragment(
    iIDPredio: Int,
    cCalle: Int,
    cNumero: Int,
    cManzana: Int,
    cTablaje: Int,
    cSeccion: Int,
    cMunicipio: String,
    opcion: Int
) : Fragment(R.layout.fragment_resultado_predio) {

    private val baseurl = rutaancj
    private lateinit var service: com.example.gym_system.interfaces.APIServices

    private val iIDPredio = iIDPredio
    private val cMunicipio = cMunicipio
    private val cCalle = cCalle
    private val cManzana = cManzana
    private val cNumero = cNumero
    private val cTablaje = cTablaje
    private val cSeccion = cSeccion
    private val opcion = opcion

    lateinit var title: TextView
    lateinit var llcontenedor: LinearLayout
    private val client = OkHttpClient()
    private val gson: Gson = GsonBuilder().create()
    val gson2 = Gson()
    lateinit var btnRegresa: Button

    private var _binding: FragmentResultadoPredioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentResultadoPredioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()


        title = binding.txtDatosPredio
        llcontenedor = binding.llcontenedor
        btnRegresa = binding.btnRegresar
        btnRegresa.setOnClickListener {
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.replace(

                R.id.nav_host_fragment_content_main,
                ConsultarPredioFragment()

            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        conexionApi(iIDPredio, cCalle, cNumero, cManzana, cTablaje, cSeccion, cMunicipio, opcion)
        return root
    }

    private fun conexionApi(
        iIDPredio: Int,
        cCalle: Int,
        cNumero: Int,
        cManzana: Int,
        cTablaje: Int,
        cSeccion: Int,
        cMunicipio: String,
        opcion: Int
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
                iIDPredio,
                cCalle,
                cNumero,
                cManzana,
                cTablaje,
                cSeccion,
                cMunicipio,
                opcion
            )

        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadSeguimiento(
        iIDPredio: Int,
        cCalle: Int,
        cNumero: Int,
        cManzana: Int,
        cTablaje: Int,
        cSeccion: Int,
        cMunicipio: String,
        opcion: Int
    ) {

        var post: PredioClass
        var Colonia: String
        var Manzana: String
        var Municipio: String
        var Nomenclatura: String
        var Seccion: String
        var IDPredio: String

        try {
            service.getPredio(
                iIDPredio,
                cCalle,
                cNumero,
                cManzana,
                cTablaje,
                cSeccion,
                cMunicipio,
                opcion
            )
                .enqueue(object : Callback<PredioClass> {
                    override fun onResponse(
                        call: Call<PredioClass>,
                        response: Response<PredioClass>
                    ) {
                        println(response)
                        post = response.body()!!
                        println(post)
                        println("holamundo")
                        if (response.isSuccessful) {

                            val json = gson2.toJson(post)
                            val jsonDecode = gson2.fromJson(json, PredioClass::class.java)
                            if (jsonDecode.lSuccess) {
                                SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Genial")
                                    .setContentText("Se encontraron datos de esa solicitud")
                                    .show()

                                title.append("FOLIO: $iIDPredio\n")

                                jsonDecode.data.forEach {
                                    val txtdinamico = TextView(context)
                                    val btnseguimiento = Button(context)
                                    val espacio = TextView(context)
                                    txtdinamico.textSize = 20F
                                    txtdinamico.setTextColor(Color.parseColor("#000000"))
                                    txtdinamico.setBackgroundResource(R.drawable.font)
                                    btnseguimiento.setTextColor(Color.parseColor("#FFFFFF"))
                                    btnseguimiento.setBackgroundColor(Color.parseColor("#35A5E2"))

                                    IDPredio = ("Folio E: " + it.iIDPredio)
                                    Seccion = ("Seccion: " + it.cSeccion)
                                    Manzana = ("Manzana: " + it.cManzana)
                                    Nomenclatura = ("Nomenclatura: " + it.cNomenclatura)
                                    Colonia = ("Coloina: " + it.cColonia)
                                    Municipio = ("Municipio: " + it.cMunicipio)

                                    llcontenedor.setBackgroundResource(R.drawable.bordecuadro)
                                    btnseguimiento.append("Detalles")
                                    txtdinamico.append(IDPredio + "\n")
                                    txtdinamico.append(Seccion + "\n")
                                    txtdinamico.append(Colonia + "\n")
                                    txtdinamico.append(Manzana + "\n")
                                    txtdinamico.append(Nomenclatura + "\n")
                                    txtdinamico.append(Municipio + "\n")

                                    llcontenedor.addView(txtdinamico)
                                    llcontenedor.addView(btnseguimiento)
                                    llcontenedor.addView(espacio)

                                    val fragmentManager = parentFragmentManager
                                    val fragmentTransaction: FragmentTransaction =
                                        fragmentManager.beginTransaction()

                                    btnseguimiento.setOnClickListener {
                                        fragmentTransaction.setReorderingAllowed(true);
                                        fragmentTransaction.add(

                                            R.id.nav_host_fragment_content_main,
                                            DetallesPredioFragment(
                                                iIDPredio,
                                                cCalle,
                                                cNumero,
                                                cManzana,
                                                cTablaje,
                                                cSeccion,
                                                cMunicipio,
                                                opcion
                                            )

                                        )
                                        fragmentTransaction.addToBackStack(null)
                                        fragmentTransaction.commit()
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

                    override fun onFailure(call: Call<PredioClass>, t: Throwable) {
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

    private fun sweetalert() {
        val pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.progressHelper
        pDialog.show()
        Handler(Looper.getMainLooper()).postDelayed({ pDialog.dismiss() }, 5000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}