package com.example.gym_system.ui.productos

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.example.app_insejupy.models.seguimiento.rpp.Detalle
import com.example.app_insejupy.models.seguimiento.rpp.Seguimiento
import com.example.gym_system.ui.productos.rpp.LineaTiempoRPPFragment
import com.example.pruebadrawer.*
import com.example.pruebadrawer.databinding.FragmentVistaSolicitudBinding
import com.example.pruebadrawer.interfaces.*
import com.example.pruebadrawer.ui.seguimiento.SeguimientoFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "DEPRECATION")
class ProductsFragment(


) : Fragment(R.layout.fragment_vista_solicitud) {


    lateinit var title: TextView
    lateinit var llcontenedor: LinearLayout
    lateinit var btnRegresa: Button
    private var _binding: Fra = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVistaSolicitudBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        title = binding.txtDatos
        llcontenedor = binding.llcontenedor
        btnRegresa = binding.btnRegresar
        fragmentTransaction.setReorderingAllowed(true)
        btnRegresa.setOnClickListener {
            fragmentTransaction.replace(
                R.id.nav_host_fragment_content_main,
                SeguimientoFragment()
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        loadSeguimiento(iIDSolicitud, iIDAnioFiscal)
        return root
    }


    private fun loadSeguimiento(iIDSolicitud: Int, iIDAnioFiscal: Int) {
        service = retrofit.create(com.example.gym_system.interfaces.APIServices::class.java)

        service.getSolicitud(iIDSolicitud, iIDAnioFiscal).enqueue(object : Callback<Seguimiento> {
            override fun onResponse(call: Call<Seguimiento>, response: Response<Seguimiento>) {
                response.body()?.let { post ->
                    if (response.isSuccessful) {
                        val jsonDecode = gson2.fromJson(gson2.toJson(post), Seguimiento::class.java)

                        if (jsonDecode.lSuccess) {
                            SweetAlert.showSuccessDialog(
                                requireContext(),
                                message_success
                            )
                            title.append("TRAMITES DE SOLICITUD: ${jsonDecode.data.iIDSolicitud}-${jsonDecode.data.iIDAnioFiscal}\n")
                            jsonDecode.data.detalles.forEach { detalle ->
                                createViewForDetalle(detalle)
                            }
                        } else {
                            SweetAlert.showErrorDialog(
                                requireContext(),
                                message_error
                            )
                        }
                    } else {
                        SweetAlert.showErrorDialog(
                            requireContext(),
                            message_api_error
                        )
                        response.errorBody()?.string()

                    }
                }
            }

            override fun onFailure(call: Call<Seguimiento>, t: Throwable) {
                SweetAlert.showErrorDialog(
                    requireContext(),
                    message_api_error
                )
                t.printStackTrace()
                println("Error: ${t.message}")
            }
        })
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createViewForDetalle(it: Detalle) {
        val cetapa: String
        val txtdinamico = TextView(context)
        val btnseguimiento = Button(context)
        val espacio = TextView(context)
        val icon = requireContext().getDrawable(R.drawable.icon_info)
        val buttonWidth = 550
        val iddetalle = it.iIDDetalle
        val params = LinearLayout.LayoutParams(buttonWidth, LinearLayout.LayoutParams.WRAP_CONTENT)

        val cEstatus: String = when (it.cEstatus) {
            "A" -> "ANÁLISIS Y CAPTURA"
            "AS" -> "ASIGNADO"
            "B" -> "VALIDACIÓN"
            "C" -> {
                "CANCELADO (DENEGADO)"
            }
            "CC" -> {
                "CANCELADO"
            }
            "CF" -> "CAPTURA FINAL"
            "CI" -> "CAPTURA INICIAL"
            "D" -> {

                "SUBSANADO"
            }
            "E" -> "POR ENTREGAR"
            "EX" -> "EXPORTADO"
            "F" -> "FINAL"
            "G" -> "ENTREGADO"
            "I" -> "RECEPCIÓN"
            "J" -> "CALIFICACIÓN"
            "L" -> "CALIFICADO"
            "M" -> "EN FIRMA"
            "N", "OB" -> {

                "OBSERVADO"
            }
            "O" -> {

                "OBJETADO"
            }
            "P" -> {

                "SUSPENDIDO"
            }
            "Q" -> {

                "EN FIRMA (DENEGADO)"
            }
            "QF" -> "CALIDAD FINAL"
            "QI" -> "CALIDAD INICIAL"
            "R" -> "CAPTURA DEL PREDIO"
            "RF" -> "REVISIÓN FINAL"
            "RI" -> "REVISIÓN INICIAL"
            "RP" -> "AUTORIZADO"
            "RR" -> "CORREGIDO"
            "S" -> "SUBSANADO"
            "T" -> {

                "ENTREGADO (DENEGADO)"
            }
            "V" -> "AUTORIZACIÓN"
            "X" -> "EN ESPERA"
            else -> ""
        }

        val coperacion = "Tramite: ${it.cOperacion}"
        val iIDPredio = "Folio Electrónico: ${it.iIDPredio}"
        cetapa = "Etapa Actual: $cEstatus"
        val cmotivo = "Motivo: ${it.cMotivo}"


        @Suppress("DEPRECATION")
        icon?.apply {
            setBounds(0, 0, 100, 100)
            setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.white),
                PorterDuff.Mode.SRC_IN
            )
        }
        btnseguimiento.apply {
            setTextColor(Color.parseColor("#FFFFFF"))
            setBackgroundColor(Color.parseColor("#35A5E2"))
            setBackgroundResource(R.drawable.custom_button)
            setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
            compoundDrawablePadding = -50
            setPaddingRelative(50, 0, 0, 0)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
            text = context.getString(R.string.ver_seguimiento)
            layoutParams = params
            setOnClickListener {
                SweetAlert.showProgressDialog(requireContext())
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(
                        R.id.nav_host_fragment_content_main,
                        LineaTiempoRPPFragment(iIDSolicitud, iIDAnioFiscal, iddetalle)
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }

        txtdinamico.apply {
            setPaddingRelative(50, 0, 50, 0)
            text = listOf(coperacion, iIDPredio, cetapa, cmotivo)
                .filter { it.isNotEmpty() }
                .joinToString(separator = "\n")
            textSize = 20F
            setTextColor(Color.parseColor("#000000"))
            setBackgroundResource(R.drawable.font)
        }
        llcontenedor.apply {
            gravity = Gravity.CENTER
            setBackgroundResource(R.drawable.bordecuadro)
            addView(txtdinamico)
            addView(btnseguimiento)
            addView(espacio)
            addView(View(context).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 7)
                setBackgroundColor(Color.parseColor("#CCCCCC"))
            })
        }


    }


}