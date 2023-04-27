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

import com.example.gym_system.R
import com.example.gym_system.databinding.FragmentProductosBinding
import com.example.gym_system.interfaces.*
import com.example.gym_system.models.productos.Data
import com.example.gym_system.models.productos.getProducts
import com.example.gym_system.ui.productos.rpp.LineaTiempoRPPFragment
import com.example.pruebadrawer.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "DEPRECATION")
class ProductsFragment(


) : Fragment(R.layout.fragment_productos) {


    lateinit var title: TextView
    lateinit var llcontenedor: LinearLayout
    lateinit var btnRegresa: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_productos, container, false)

        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        title = root.findViewById(R.id.txtDatos)
        llcontenedor = root.findViewById(R.id.llcontenedor)
        btnRegresa = root.findViewById(R.id.btnRegresar)
        fragmentTransaction.setReorderingAllowed(true)


        loadSeguimiento()
        return root
    }


    private fun loadSeguimiento() {
        service = retrofit.create(com.example.gym_system.interfaces.APIServices::class.java)

        service.getProductos().enqueue(object : Callback<getProducts> {
            override fun onResponse(call: Call<getProducts>, response: Response<getProducts>) {
                response.body()?.let { post ->
                    if (response.isSuccessful) {
                        val jsonDecode = gson2.fromJson(gson2.toJson(post), getProducts::class.java)

                        if (jsonDecode.lSuccess) {
                            SweetAlert.showSuccessDialog(
                                requireContext(),
                                message_success
                            )
                            title.append("Productos:\n")
                            jsonDecode.data.forEach { detalle ->
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

            override fun onFailure(call: Call<getProducts>, t: Throwable) {
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
    private fun createViewForDetalle(it: Data) {
        val cetapa: String
        val txtdinamico = TextView(context)
        val btnseguimiento = Button(context)
        val espacio = TextView(context)
        val icon = requireContext().getDrawable(R.drawable.icon_info)
        val buttonWidth = 550
        val iddProducto = it.iIDProducto
        val params = LinearLayout.LayoutParams(buttonWidth, LinearLayout.LayoutParams.WRAP_CONTENT)


        val cNombre = "Nombre: ${it.cNombreProduct}"
        val cCodeBar = "Codigo de Barras: ${it.cCodeBar}"




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

        }

        txtdinamico.apply {
            setPaddingRelative(50, 0, 50, 0)
            text = listOf(cNombre, cCodeBar)
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