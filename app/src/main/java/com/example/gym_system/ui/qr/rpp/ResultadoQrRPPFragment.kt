package com.example.gym_system.ui.qr.rpp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Gravity
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
import com.example.pruebadrawer.databinding.FragmentResultadoQrRPPBinding
import com.example.pruebadrawer.interfaces.*
import com.example.pruebadrawer.models.QR.RPP.QrRPPClass
import com.example.pruebadrawer.ui.qr.PDFFragment
import com.example.pruebadrawer.ui.seguimiento.rpp.LineaTiempoRPPFragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ResultadoQrRPPFragment(
    result: String

) : Fragment(R.layout.fragment_resultado_qr_r_p_p) {
    constructor() : this("")


    private val result = result

    lateinit var title: TextView
    lateinit var llcontenedor: LinearLayout

    lateinit var btnRegresa: Button

    private var _binding: FragmentResultadoQrRPPBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultadoQrRPPBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)
        title = binding.txtDatosTomo
        llcontenedor = binding.llcontenedor
        btnRegresa = binding.btnRegresar
        fragmentTransaction.setReorderingAllowed(true)
        btnRegresa.setOnClickListener {
            fragmentTransaction.replace(

                R.id.nav_host_fragment_content_main, QrRppFragment()

            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        loadDatax(result)
        return root
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun loadDatax(result: String) {
        val delimiter = "-"
        var firmante: String
        var fechaSol: String
        var fechaFirma: String
        var otorgante: String
        var operacion: String
        var inscripcion: String
        var sello: String

        var post: QrRPPClass
        service = retrofit.create(com.example.gym_system.interfaces.APIServices::class.java)
        try {
            val split = result.split(delimiter)
            val last = split.last()
            val icon = requireContext().getDrawable(R.drawable.icon_pdf)
            val buttonWidth = 550

            val params =
                LinearLayout.LayoutParams(buttonWidth, LinearLayout.LayoutParams.WRAP_CONTENT)

            service.getBoletaGeneral(last).enqueue(object : Callback<QrRPPClass> {

                override fun onResponse(
                    call: Call<QrRPPClass>, response: Response<QrRPPClass>
                ) {


                    response.body()?.let { post ->
                        if (response.isSuccessful) {

                            val txtdinamico = TextView(context)
                            val btnPDF = Button(context)
                            val espacio = TextView(context)
                            val jsonDecode =
                                gson2.fromJson(gson2.toJson(post), QrRPPClass::class.java)
                            if (jsonDecode.lSuccess) {
                                SweetAlert.showProgressDialog(requireContext())

                                title.append("SOLICITUD: " + jsonDecode.iSolicitud + "-" + jsonDecode.iIDDetalle + "-" + jsonDecode.iAnioFiscal + "\n")

                                //infoSeguimiento.setPadding(20, 15, 15, 15)
                                firmante = ("Firmante: " + jsonDecode.cFirmante)
                                fechaSol = ("Fecha Solicitud: " + jsonDecode.dtFechaSolicitud)
                                fechaFirma = ("Fecha Firmaa: " + jsonDecode.dtFechaFirma)
                                otorgante = ("Otorgante: " + jsonDecode.cOtorgante)
                                operacion = ("Operacion: " + jsonDecode.cOperacion)
                                inscripcion = ("Inscripcion: " + jsonDecode.cTipoDocumento)
                                sello = ("Sello Digital: " + jsonDecode.cSelloDigital)
                                txtdinamico.apply {
                                    setPaddingRelative(50, 0, 50, 0)
                                    text = listOf(
                                        firmante,
                                        fechaSol,
                                        fechaFirma,
                                        otorgante,
                                        operacion,
                                        inscripcion,
                                        sello
                                    )
                                        .filter { it.isNotEmpty() }
                                        .joinToString(separator = "\n")
                                    textSize = 20F
                                    setTextColor(Color.parseColor("#000000"))
                                    setBackgroundResource(R.drawable.font)
                                }


                                btnPDF.apply {
                                    setTextColor(Color.parseColor("#FFFFFF"))
                                    setBackgroundColor(Color.parseColor("#35A5E2"))
                                    setBackgroundResource(R.drawable.custom_button)
                                    setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                                    compoundDrawablePadding = -50
                                    setPaddingRelative(50, 0, 0, 0)
                                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
                                    text = context.getString(R.string.ver_pdf)
                                    layoutParams = params
                                    setOnClickListener {
                                        SweetAlert.showProgressDialog(requireContext())
                                        parentFragmentManager.beginTransaction()
                                            .setReorderingAllowed(true)
                                            .replace(
                                                R.id.nav_host_fragment_content_main,
                                                PDFFragment(
                                                    jsonDecode.cURL
                                                )
                                            )
                                            .addToBackStack(null)
                                            .commit()
                                        //showDialogAlertSimple(iIDSolicitud, iIDAnioFiscal, iddetalle)
                                    }
                                }
                                llcontenedor.apply {
                                    gravity = Gravity.CENTER
                                    setBackgroundResource(R.drawable.bordecuadro)
                                    addView(txtdinamico)
                                    addView(btnPDF)
                                    addView(espacio)
                                    addView(View(context).apply {
                                        layoutParams = ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            7
                                        )
                                        setBackgroundColor(Color.parseColor("#CCCCCC"))
                                    })
                                }


                            } else {
                                SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText(
                                    "Oops..."
                                ).setContentText("No se encontraron datos de esa solicitud")
                                    .show()
                            }

                        }
                    }


                }

                override fun onFailure(call: Call<QrRPPClass>, t: Throwable) {
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


}