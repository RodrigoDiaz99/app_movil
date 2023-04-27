package com.example.gym_system.ui.datostomo

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.FragmentTransaction
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.gym_system.R
import com.example.gym_system.databinding.FragmentPedidoBinding


class DatosTomoFragment : Fragment(R.layout.fragment_pedido) {

    private var _binding: FragmentPedidoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_productos, container, false)


        val spinnerProductos: Spinner = root.findViewById(R.id.product_spinner)
        val spinnerTipopredio: Spinner = binding.spinnerTipoPredio
        val spinnerVolumen: Spinner = binding.spinnerVolumen
        val btnBuscarTomo: Button = binding.btnBuscarTomo
        val txtFolio: EditText = binding.txtFolio
        val txtLetra: EditText = binding.txtLetra
        val txtTomo: EditText = binding.txtTomo

        val libroSpinner = resources.getStringArray(R.array.libros)
        val adapterarray = ArrayAdapter(requireContext(), R.layout.spinner, libroSpinner)
        spinnerLibro.setAdapter(adapterarray)

        val TipopredioSpinner = resources.getStringArray(R.array.Tpredio)
        val adapterarray1 = ArrayAdapter(requireContext(), R.layout.spinner, TipopredioSpinner)
        spinnerTipopredio.setAdapter(adapterarray1)

        val volumenSpinner = resources.getStringArray(R.array.volumen)
        val adapterarray2 = ArrayAdapter(requireContext(), R.layout.spinner, volumenSpinner)
        spinnerVolumen.setAdapter(adapterarray2)

        var iTipoPredio = 0
        var iVolumenPredio = 0
        var cLetraLibro: String
        var cFolioLibro: Int
        var iLibro = 0

        spinnerTipopredio.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    try {
                        when (TipopredioSpinner[position]) {
                            "TODOS" -> {
                                iTipoPredio = 0
                            }
                            "URBANO" -> {
                                iTipoPredio = 1
                            }
                            "RUSTICO" -> {
                                iTipoPredio = 2
                            }
                            "COMERCIO" -> {
                                iTipoPredio = 3
                            }
                        }
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

        spinnerVolumen.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    try {
                        when (volumenSpinner[position]) {
                            "TODOS" -> {
                                iVolumenPredio = 0
                            }
                            "UNICO" -> {
                                iVolumenPredio = 1
                            }
                            "PRIMERO" -> {
                                iVolumenPredio = 2
                            }
                            "SEGUNDO" -> {
                                iVolumenPredio = 3
                            }
                            "TERCERO" -> {
                                iVolumenPredio = 4
                            }
                            "CUARTO" -> {
                                iVolumenPredio = 5
                            }
                            "QUINTO" -> {
                                iVolumenPredio = 7
                            }
                            "BIS" -> {
                                iVolumenPredio = 8
                            }
                            "SEXTO" -> {
                                iVolumenPredio = 9
                            }
                            "AUXILIAR UNICO" -> {
                                iVolumenPredio = 55
                            }
                        }
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

        spinnerLibro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                btnBuscarTomo.setOnClickListener {
                    try {
                        val fragmentManager = parentFragmentManager
                        val fragmentTransaction: FragmentTransaction =
                            fragmentManager.beginTransaction()


                        val tomo = txtTomo.text.toString().trim()

                        if (tomo.isEmpty()) {
                            txtTomo.error = "El campo tomo esta vacio"
                            return@setOnClickListener
                        }
                        var cTomo = tomo.toInt()

                        when (libroSpinner[position]) {
                            "SELECCIONA LIBRO" -> {
                                SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Por favor seleccione un libro")
                                    .show()
                                return@setOnClickListener
                            }
                            "PRIMERO" -> {
                                iLibro = 1
                            }
                            "TERCERO" -> {
                                iLibro = 3
                            }
                            "CUARTO" -> {
                                iLibro = 4
                            }
                            "NOVENO" -> {
                                iLibro = 5
                            }
                            "SEGUNDO" -> {
                                iLibro = 6
                            }
                            "SEXTO" -> {
                                iLibro = 7
                            }
                            "TERCERO DEL REGISTRO DEL CREDITO AGRICOLA" -> {
                                iLibro = 11
                            }
                            "2DO COMERCIO" -> {
                                iLibro = 14
                            }
                            "5TO AGRICOLA" -> {
                                iLibro = 20
                            }
                            "SEXTO AUXILIAR" -> {
                                iLibro = 26
                            }
                            "TERRENOS NACIONALES" -> {
                                iLibro = 29
                            }
                            "REGISTRO DE CREDITO RURAL" -> {
                                iLibro = 30
                            }
                        }


                        if (txtTomo.text.toString().trim().isEmpty()) {
                            cTomo = 0
                        } else {
                            cTomo = txtTomo.text.toString().toInt()
                        }

                        if (txtFolio.text.toString().trim().isEmpty()) {
                            cFolioLibro = 0
                        } else {
                            cFolioLibro = txtFolio.text.toString().toInt()
                        }

                        if (txtLetra.text.toString().trim().isEmpty()) {
                            cLetraLibro = " "
                        } else {
                            cLetraLibro = txtLetra.text.toString()
                        }

                        sweetalert()
                        fragmentTransaction.setReorderingAllowed(true);
                        fragmentTransaction.add(
                            R.id.nav_host_fragment_content_main,
                            ResultadoTomosFragment(
                                iTipoPredio,
                                iVolumenPredio,
                                cLetraLibro,
                                cFolioLibro,
                                iLibro,
                                cTomo
                            )
                        )

                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()

                    } catch (e: Exception) {
                        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(e.message)
                            .show()
                        println(e.message)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Por favor seleccione un libro")
                    .show()
            }

        }
        return root
    }

    fun sweetalert() {
        val pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.progressHelper
        pDialog.show()
        Handler(Looper.getMainLooper()).postDelayed({ pDialog.dismiss() }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}