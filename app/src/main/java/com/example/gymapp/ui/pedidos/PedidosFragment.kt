package com.example.gymapp.ui.pedidos

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.gym_system.interfaces.retrofit
import com.example.gym_system.interfaces.service
import com.example.gym_system.models.productos.Data
import com.example.gym_system.models.productos.getProducts
import com.example.gymapp.R
import com.example.gymapp.global.APIServices
import com.example.gymapp.global.CustomAdapter
import com.example.gymapp.global.Product
import kotlinx.coroutines.InternalCoroutinesApi



import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PedidosFragment : Fragment() {

    private lateinit var spinner: Spinner
    private var isFirstSelection = true

    private lateinit var selectedProductTextView: TextView
    private lateinit var quantityTextView: TextView

    private var productos: List<Data> = listOf()
    private val selectedProducts = mutableListOf<Data>()


    // Aquí deberías colocar tu adaptador personalizado para el RecyclerView
    private lateinit var adapter: CustomAdapter
    private val productList = mutableListOf<Product>()
    val productosSeleccionados = mutableMapOf<String, Int>()

    @SuppressLint("MissingInflatedId")
    @OptIn(InternalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedidos, container, false)


        spinner = view.findViewById(R.id.spinner)
        quantityTextView = view.findViewById(R.id.quantity_text_view)
        selectedProductTextView = view.findViewById(R.id.selected_product_text_view) // Asegúrate de reemplazar `R.id.selected_products_text_view` con el ID correcto

        spinnerProduct()
        return view
    }

    fun setSpinnerListener(spinner: Spinner) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Get the selected product from the spinner
                val product = productos[position]

                // If product is already selected, increment its quantity
                val quantity = productosSeleccionados[product.cNombreProduct]?.plus(1) ?: 1

                // Update the quantity in the map
                productosSeleccionados[product.cNombreProduct] = quantity

                // Set the quantity of the product
                quantityTextView.text = quantity.toString()

                // Add the product name to the TextView
                selectedProductTextView.text = "${selectedProductTextView.text}\n${product.cNombreProduct} - Quantity: $quantity"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }







    private fun spinnerProduct() {
        service = retrofit.create(APIServices::class.java)
        service.getProductos().enqueue(object : Callback<getProducts> {

            override fun onResponse(call: Call<getProducts>, response: Response<getProducts>) {
                println(response)
                if (response.isSuccessful) {
                    println(response)
                    val productosAPI = response.body()?.data
                    productosAPI?.let {
                        productos = it // Aquí reemplazas los datos de la lista de productos con los datos de la API
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            it.map { producto -> producto.cNombreProduct }
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner.adapter = adapter

                        // Call setSpinnerListener here
                        setSpinnerListener(spinner)
                    }
                } else {
                    // Maneja el error
                }
            }


            override fun onFailure(call: Call<getProducts>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }




}

