package com.example.gym_system.ui.datospredio

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentConsultarPredioBinding

class ConsultarPredioFragment : Fragment(R.layout.fragment_consultar_predio) {

    private var _binding: FragmentConsultarPredioBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentConsultarPredioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinnerConsultarPredio: Spinner = binding.spinnerConsultarPorPredio
        val spinnerMunicipios: Spinner = binding.spinnerMunicipos
        val textViewTablaje: TextView = binding.txtViewTablaje
        val textViewFolioe: TextView = binding.txtViewFolioe
        val textViewNomenclatura: TextView = binding.txtViewNomenclatura
        val textureViewInfocat: TextView = binding.txtViewInfoCat
        val txtSeccion: EditText = binding.txtSeccion
        val txtManzana: EditText = binding.txtManzana
        val txtSolicitudPredio: EditText = binding.txtSolicitudFolioe
        val txtCalle: EditText = binding.txtCalle
        val txtNumero: EditText = binding.txtNumero
        val txtTablaje: EditText = binding.txtTablaje
        val btnBuscarPredio: Button = binding.btnBuscarPredio

        textViewFolioe.isVisible = false
        textViewTablaje.isVisible = false
        textViewNomenclatura.isVisible = false
        textureViewInfocat.isVisible = false
        txtSeccion.isVisible = false
        txtManzana.isVisible = false
        txtSolicitudPredio.isVisible = false
        txtCalle.isVisible = false
        txtNumero.isVisible = false
        txtTablaje.isVisible = false
        spinnerMunicipios.isVisible = false


        val municipiosSpinner = resources.getStringArray(R.array.municipios)
        val adapterarray = ArrayAdapter(requireContext(), R.layout.spinner, municipiosSpinner)
        spinnerMunicipios.setAdapter(adapterarray)

        val consultarpredioSpinner = resources.getStringArray(R.array.DatosPredio)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner, consultarpredioSpinner)
        spinnerConsultarPredio.setAdapter(arrayAdapter)

        spinnerConsultarPredio.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    Toast.makeText(context, consultarpredioSpinner[p2], Toast.LENGTH_SHORT).show()

                    try {
                        val fragmentManager = parentFragmentManager
                        val fragmentTransaction: FragmentTransaction =
                            fragmentManager.beginTransaction()
                        var iIDPredio: Int
                        var cSeccion: Int
                        var cManzana: Int
                        var cCalle: Int
                        var cNumero: Int
                        var cTablaje: Int
                        var cMunicipio: String
                        var opcion = 0

                        when (consultarpredioSpinner[p2]) {

                            "Folio Electronico" -> {
                                textViewFolioe.isVisible = true
                                textViewTablaje.isVisible = false
                                textViewNomenclatura.isVisible = false
                                textureViewInfocat.isVisible = false
                                txtSeccion.isVisible = false
                                txtManzana.isVisible = false
                                txtSolicitudPredio.isVisible = true
                                txtCalle.isVisible = false
                                txtNumero.isVisible = false
                                txtTablaje.isVisible = false
                                spinnerMunicipios.isVisible = false
                                opcion = 1
                            }

                            "Nomenclatura" -> {
                                textViewTablaje.isVisible = false
                                textViewFolioe.isVisible = false
                                textViewNomenclatura.isVisible = true
                                textureViewInfocat.isVisible = false
                                txtSeccion.isVisible = false
                                txtManzana.isVisible = false
                                txtSolicitudPredio.isVisible = false
                                txtCalle.isVisible = true
                                txtNumero.isVisible = true
                                txtTablaje.isVisible = false
                                spinnerMunicipios.isVisible = true
                                opcion = 2
                            }

                            "Tablaje" -> {
                                textViewTablaje.isVisible = true
                                textViewFolioe.isVisible = false
                                textViewNomenclatura.isVisible = false
                                textureViewInfocat.isVisible = false
                                txtSeccion.isVisible = false
                                txtManzana.isVisible = false
                                txtSolicitudPredio.isVisible = false
                                txtCalle.isVisible = false
                                txtNumero.isVisible = false
                                txtTablaje.isVisible = true
                                spinnerMunicipios.isVisible = true
                                opcion = 3

                            }

                            "Informacion Catastral" -> {
                                textViewTablaje.isVisible = false
                                textViewFolioe.isVisible = false
                                textViewNomenclatura.isVisible = false
                                textureViewInfocat.isVisible = true
                                txtSeccion.isVisible = true
                                txtManzana.isVisible = true
                                txtSolicitudPredio.isVisible = false
                                txtCalle.isVisible = false
                                txtNumero.isVisible = false
                                txtTablaje.isVisible = false
                                spinnerMunicipios.isVisible = true
                                opcion = 4
                            }
                        }

                        btnBuscarPredio.setOnClickListener {
                            try {
                                if (txtSolicitudPredio.text.toString().trim().isEmpty()) {
                                    iIDPredio = 0
                                } else {
                                    iIDPredio = txtSolicitudPredio.text.toString().toInt()
                                }

                                if (txtCalle.text.toString().trim().isEmpty()) {
                                    cCalle = 0
                                } else {
                                    cCalle = txtCalle.text.toString().toInt()
                                }

                                if (txtNumero.text.toString().trim().isEmpty()) {
                                    cNumero = 0
                                } else {
                                    cNumero = txtNumero.text.toString().toInt()
                                }

                                if (txtSeccion.text.toString().trim().isEmpty()) {
                                    cSeccion = 0
                                } else {
                                    cSeccion = txtSeccion.text.toString().toInt()
                                }

                                if (txtTablaje.text.toString().trim().isEmpty()) {
                                    cTablaje = 0
                                } else {
                                    cTablaje = txtTablaje.text.toString().toInt()
                                }

                                if (txtManzana.text.toString().trim().isEmpty()) {
                                    cManzana = 0
                                } else {
                                    cManzana = txtManzana.text.toString().toInt()
                                }

                                if (spinnerMunicipios.selectedItem.toString() == "SELECCIONA") {
                                    cMunicipio = "vacio"
                                } else {
                                    cMunicipio = spinnerMunicipios.selectedItem.toString()
                                }
                                sweetalert()

                                fragmentTransaction.setReorderingAllowed(true)
                                fragmentTransaction.add(
                                    R.id.nav_host_fragment_content_main,
                                    ResultadosPredioFragment(
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
                            } catch (e: Exception) {
                                println(e.message)
                            }
                            println("entrando boton")

                        }


                    } catch (e: Exception) {
                        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(e.message)
                            .show()
                    }


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Por favor escoja una opcion valida")
                        .show()
                }

            }
        // Inflate the layout for this fragment
        return root
    }

    fun sweetalert() {
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