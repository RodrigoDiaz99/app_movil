package com.example.gym_system.ui.datospredio.detallespredio

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentCedulaBinding
import com.example.gym_system.interfaces.APIServices
import com.example.pruebadrawer.models.detallespredio.cedula.CedulaClass
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

class CedulaFragment(
    iIDPredio: Int
) : Fragment(R.layout.fragment_cedula) {

    private val baseurl = rutaancj
    private lateinit var service: com.example.gym_system.interfaces.APIServices

    private val iIDPredio = iIDPredio
    private var cMunicipio = 1

    lateinit var title: TextView
    lateinit var llcontenedor: LinearLayout
    private val client = OkHttpClient()
    private val gson: Gson = GsonBuilder().create()
    val gson2 = Gson()

    private var _binding: FragmentCedulaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCedulaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        llcontenedor = binding.llcontenedor
        conexionApi(iIDPredio, cMunicipio)
        // Inflate the layout for this fragment
        return root
    }

    private fun conexionApi(
        iIDPredio: Int,
        cMunicipio: Int

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
                cMunicipio
            )

        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadSeguimiento(
        iIDPredio: Int,
        cMunicipio: Int
    ) {
        var post: CedulaClass
        var cMotivo: String
        var dFondo: Int
        var dFrente: Int
        var dSuperficieConstruccion: Int
        var dSuperficieTerreno: Int
        var dValorCatastral: Double
        var dtFechaCedula: String
        var iFolioCedula: String
        var iIDCedula: Int
        var lVigente: Boolean

        try {
            service.getCedulaPredio(
                iIDPredio,
                cMunicipio
            )
                .enqueue(object : Callback<CedulaClass> {
                    override fun onResponse(
                        call: Call<CedulaClass>,
                        response: Response<CedulaClass>
                    ) {
                        println(response)
                        println("Hola cocmo estas")
                        post = response.body()!!
                        println(post)
                        println("holamundo")
                        if (response.isSuccessful) {

                            val json = gson2.toJson(post)
                            val jsonDecode = gson2.fromJson(json, CedulaClass::class.java)
                            if (jsonDecode.lSuccess) {
                                jsonDecode.data.forEach() {

                                    val txtdinamico = TextView(context)
                                    val espacio = TextView(context)

                                    txtdinamico.textSize = 20F
                                    txtdinamico.setTextColor(Color.parseColor("#000000"))
                                    txtdinamico.setBackgroundResource(R.drawable.font)

                                    iIDCedula = (it.iIDCedula)
                                    iFolioCedula = (it.iFolioCedula)
                                    dtFechaCedula = (it.dtFechaCedula)
                                    cMotivo = (it.cMotivo)
                                    lVigente = (it.lVigente)


                                    llcontenedor.setBackgroundResource(R.drawable.bordecuadro)
                                    txtdinamico.append("Cedula: $iIDCedula\n")
                                    txtdinamico.append("Folio Cedula: $iFolioCedula\n")
                                    txtdinamico.append("Fecha: $dtFechaCedula\n")
                                    txtdinamico.append("Motivo: $cMotivo\n")
                                    if (lVigente) {
                                        txtdinamico.append("Estado: Vigente")
                                    } else {
                                        txtdinamico.append("Estado: NO vigente")
                                    }
                                    llcontenedor.addView(txtdinamico)
                                    llcontenedor.addView(espacio)
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

                    override fun onFailure(call: Call<CedulaClass>, t: Throwable) {
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