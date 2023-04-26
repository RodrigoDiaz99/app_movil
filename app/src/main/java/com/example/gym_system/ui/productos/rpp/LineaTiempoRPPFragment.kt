package com.example.gym_system.ui.productos.rpp

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction

import com.example.app_insejupy.models.seguimiento.rpp.seguimiento.SeguimientoClass
import com.example.gym_system.ui.productos.ProductsFragment
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentLineaDeTiempoBinding
import com.example.pruebadrawer.interfaces.*


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LineaTiempoRPPFragment(
    private val iIDSolicitud: Int,
    private val iIDAnioFiscal: Int,
    iddetalle: Int,
) : Fragment(R.layout.fragment_linea_de_tiempo) {
    lateinit var timeline: LinearLayout
    private val iIDDetalle = iddetalle
    lateinit var btnRegresa: Button
    private var _binding: FragmentLineaDeTiempoBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLineaDeTiempoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        timeline = binding.timeline
        btnRegresa = binding.btnRegresar
        fragmentTransaction.setReorderingAllowed(true)
        btnRegresa.setOnClickListener {
            fragmentTransaction.replace(
                R.id.nav_host_fragment_content_main,
                ProductsFragment(iIDSolicitud, iIDAnioFiscal)
            )
                .addToBackStack(null)
                .commit()
        }
        loadData(iIDSolicitud, iIDAnioFiscal, iIDDetalle)

        // Inflate the layout for this fragment
        return root
    }


    private fun loadData(iIDSolicitud: Int, iIDAnioFiscal: Int, iIDDetalle: Int) {
        service = retrofit.create(com.example.gym_system.interfaces.APIServices::class.java)
        val icon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_info)
        @Suppress("DEPRECATION")
        icon?.setColorFilter(
            ContextCompat.getColor(requireContext(), R.color.color_iconos),
            PorterDuff.Mode.SRC_ATOP
        )
        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
        try {
            service.getSeguimeinto(iIDSolicitud, iIDAnioFiscal, iIDDetalle)
                .enqueue(object : Callback<SeguimientoClass> {
                    override fun onResponse(
                        call: Call<SeguimientoClass>,
                        response: Response<SeguimientoClass>
                    ) {
                        if (response.isSuccessful) {
                            SweetAlert.showSuccessDialog(
                                requireContext(),
                                message_success
                            )
                            response.body()?.data?.lstSeguimiento?.forEach { seguimiento ->
                                val builder = StringBuilder()
                                builder.append("ETAPA: ${seguimiento.cEtapa}\n")
                                    .append("FECHA: ${seguimiento.cFechaSeguimiento} Hora: ${seguimiento.cHoraSeguimiento}\n")
                                    .append("${seguimiento.cLeyenda}\n")
                                    .append("${seguimiento.cMotivo}\n")
                                val txtdinamico = TextView(context).apply {
                                    textSize = 20F
                                    setTextColor(Color.parseColor("#000000"))
                                    setPadding(50, 0, 50, 0)
                                    text = builder.toString().trimEnd()
                                    setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                                }
                                timeline.apply {
                                    gravity = Gravity.CENTER
                                    addView(txtdinamico)
                                    addView(View(context).apply {
                                        layoutParams = ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            5
                                        ); setBackgroundColor(Color.parseColor("#CCCCCC"))
                                    })
                                }
                            }
                        } else {
                            SweetAlert.showErrorDialog(
                                requireContext(),
                                "Hubo un error al cargar los datos."
                            )
                        }
                    }

                    override fun onFailure(call: Call<SeguimientoClass>, t: Throwable) {
                        t.printStackTrace()
                        SweetAlert.showErrorDialog(
                            requireContext(),
                            message_api_error
                        )
                    }

                })

        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

}