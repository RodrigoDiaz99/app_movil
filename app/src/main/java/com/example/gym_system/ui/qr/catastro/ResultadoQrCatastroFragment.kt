package com.example.pruebadrawer.ui.qr.rpp

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentResultadoQrCatastroBinding
import com.example.gym_system.interfaces.APIServices
import com.example.pruebadrawer.models.QR.Catastro.QrCatastroClass
import com.example.pruebadrawer.interfaces.rutaancj
import com.example.pruebadrawer.ui.qr.catastro.QrCatastroFragment
import com.example.pruebadrawer.ui.qr.catastro.VisorCatastroFragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ResultadoQrCatastroFragment(
    result: String

) : Fragment(R.layout.fragment_resultado_qr_catastro) {
    constructor() : this("")

    private val baseurl = rutaancj
    private lateinit var service: com.example.gym_system.interfaces.APIServices
    private val result = result

    lateinit var title: TextView
    lateinit var llcontenedor: LinearLayout
    private val client = OkHttpClient()
    private val gson: Gson = GsonBuilder().create()
    val gson2 = Gson()
    lateinit var btnRegresa: Button

    private var _binding: FragmentResultadoQrCatastroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultadoQrCatastroBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)
        title = binding.txtDatosQR
        llcontenedor = binding.llcontenedor
        btnRegresa = binding.btnRegresar
        fragmentTransaction.setReorderingAllowed(true)
        btnRegresa.setOnClickListener {
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.replace(

                R.id.nav_host_fragment_content_main, QrCatastroFragment()

            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        conexionApi(result)

        return root
    }


    private fun conexionApi(result: String) {

        try {
            val retrofit: Retrofit = Retrofit.Builder().baseUrl(baseurl).client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build()

            service = retrofit.create(com.example.gym_system.interfaces.APIServices::class.java)
            println(service)
            loadDatax(result)
        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }

    }

    //
    private fun loadDatax(result: String) {
        val division = result
        val datax = division.split("|")

        var operacion: String

        var fechaFirma: String
        var cadenaoriginal: String
        var cadenahash: String


        var post: QrCatastroClass
        try {

            service.getHojaCatastro(datax[0], datax[1]).enqueue(object : Callback<QrCatastroClass> {
                override fun onResponse(
                    call: Call<QrCatastroClass>,
                    response: Response<QrCatastroClass>
                ) {
                    try {
                        post = response.body()!!

                        if (response.isSuccessful()) {

                            val json = gson2.toJson(post)
                            val jsonDecode = gson2.fromJson(json, QrCatastroClass::class.java)
                            if (jsonDecode.lSuccess) {
                                SweetAlertDialog(
                                    context,
                                    SweetAlertDialog.SUCCESS_TYPE
                                ).setTitleText("Genial")
                                    .setContentText("Se encontraron datos de esa solicitud").show()
                                title.append("SOLICITUD: " + jsonDecode.iIDSolicitud + "-" + jsonDecode.iIDDetalle + "-" + jsonDecode.iIDAnioFiscal + "\n")


                                val txtdinamico = TextView(context)
                                val btnPDF = Button(context)
                                val espacio = TextView(context)
                                txtdinamico.textSize = 20F
                                txtdinamico.setTextColor(Color.parseColor("#000000"))
                                txtdinamico.setBackgroundResource(R.drawable.font)
                                btnPDF.setTextColor(Color.parseColor("#FFFFFF"))
                                btnPDF.setBackgroundColor(Color.parseColor("#35A5E2"))

                                //infoSeguimiento.setPadding(20, 15, 15, 15)
                                operacion = ("Operacion: " + jsonDecode.cOperacion)

                                fechaFirma = ("Fecha Firmaa: " + jsonDecode.cFechaFirma)
                                cadenaoriginal = ("Otorgante: " + jsonDecode.cCadenaOriginal)

                                cadenahash = ("Inscripcion: " + jsonDecode.cCadenaHash)


                                llcontenedor.setBackgroundResource(R.drawable.bordecuadro);
                                btnPDF.setBackgroundResource(R.drawable.custom_button)
                                btnPDF.append("Ver PDF")
                                //  btnseguimiento.append("Ver Seguimiento")
                                txtdinamico.append(operacion + "\n")

                                txtdinamico.append(fechaFirma + "\n")

                                txtdinamico.append(cadenaoriginal + "\n")
                                txtdinamico.append(cadenahash + "\n")
                                llcontenedor.addView(txtdinamico)
                                llcontenedor.addView(btnPDF)
                                llcontenedor.addView(espacio)

                                btnPDF.setOnClickListener {
                                    sweetalert()
                                    val fragmentManager = parentFragmentManager
                                    val fragmentTransaction: FragmentTransaction =
                                        fragmentManager.beginTransaction()
                                    fragmentTransaction.setReorderingAllowed(true)
                                    fragmentTransaction.replace(
                                        R.id.nav_host_fragment_content_main,
                                        VisorCatastroFragment(
                                            jsonDecode.cUrlDocumento
                                        )
                                    )
                                    fragmentTransaction.addToBackStack(null)
                                    fragmentTransaction.commit()
                                    //showDialogAlertSimple(iIDSolicitud, iIDAnioFiscal, iddetalle)
                                }


                            } else {
                                SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText(
                                    "Oops..."
                                ).setContentText("No se encontraron datos de esa solicitud")
                                    .show()
                            }

                        }
                    } catch (e: Exception) {
                        SweetAlertDialog(
                            context,
                            SweetAlertDialog.ERROR_TYPE
                        ).setTitleText("Oops...")
                            .setContentText(e.message).show()

                    }

                }

                override fun onFailure(call: Call<QrCatastroClass>, t: Throwable) {
                    SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...")
                        .setContentText("No se encontraron datos").show()
                    t.printStackTrace()
                }

            })

        } catch (e: Exception) {
            SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...")
                .setContentText(e.message).show()
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

    override fun onDestroy() {
        // liberar los recursos y memoria aquí
        super.onDestroy()
    }
}