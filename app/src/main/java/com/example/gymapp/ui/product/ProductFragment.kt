package com.example.gymapp.ui.product

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
import com.example.gym_system.interfaces.*
import com.example.gym_system.models.productos.Data
import com.example.gym_system.models.productos.getProducts
import com.example.gymapp.R
import com.example.gymapp.global.APIServices
import com.example.gymapp.global.SweetAlert
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.android.material.shape.ShapeAppearanceModel

class ProductFragment : Fragment(R.layout.fragment_product) {

    private lateinit var title: TextView
    private lateinit var llcontenedor: LinearLayout

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        val fragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        title = view.findViewById(R.id.txtDatos)
        llcontenedor = view.findViewById(R.id.llcontenedor)

        fragmentTransaction.setReorderingAllowed(true)
        loadProduct()
        return view
    }

    private fun loadProduct() {
        service = retrofit.create(APIServices::class.java)
println(service)
        service.getProductos().enqueue(object : Callback<getProducts> {
            override fun onResponse(call: Call<getProducts>, response: Response<getProducts>) {
               println(response)
                response.body()?.let { post ->
                    if (response.isSuccessful) {
                        println(post)
                        val jsonDecode = gson2.fromJson(gson2.toJson(post), getProducts::class.java)
println(jsonDecode.lSuccess)
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

        val txtdinamico = TextView(context)
        val btnseguimiento = Button(context)
        val espacio = TextView(context)
        val icon = requireContext().getDrawable(R.drawable.icon_info)
        val buttonWidth = 550

        val params = LinearLayout.LayoutParams(buttonWidth, LinearLayout.LayoutParams.WRAP_CONTENT)


        val cNombre = "Nombre: ${it.cNombreProduct}"
        val cCodeBar = "Codigo de Barras: ${it.cCodeBar}"




        @Suppress("DEPRECATION")
        icon?.apply {
            setBounds(0, 0, 100, 100)
            setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.md_theme_light_onPrimary),
                PorterDuff.Mode.SRC_IN
            )
        }
        btnseguimiento.apply {
            setBackgroundColor(Color.parseColor("#35A5E2"))
            setTextColor(Color.parseColor("#FFFFFF"))
            setBackgroundColor(Color.parseColor("#35A5E2"))
          //  setBackgroundResource(R.drawable.custom_button)
            setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
            compoundDrawablePadding = -50
            setPaddingRelative(50, 0, 0, 0)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
            text = context.getString(R.string.datos)
            layoutParams = params

        }

        txtdinamico.apply {
            setPaddingRelative(50, 0, 50, 0)
            text = listOf(cNombre, cCodeBar)
                .filter { it.isNotEmpty() }
                .joinToString(separator = "\n")
            textSize = 20F
            setTextColor(Color.parseColor("#000000"))
            //setBackgroundResource(R.drawable.font)
        }
        llcontenedor.apply {
            gravity = Gravity.CENTER
            //setBackgroundResource(R.drawable.bordecuadro)
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
