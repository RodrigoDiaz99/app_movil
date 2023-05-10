package com.example.gymapp.ui.pedidos

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
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

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PedidosFragment : Fragment() {

    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalCostTextView: TextView
    val productosSeleccionados = mutableListOf<Data>()
    // Aquí deberías colocar tu adaptador personalizado para el RecyclerView
    private lateinit var adapter: CustomAdapter
    private val productList = mutableListOf<Product>()


    @SuppressLint("MissingInflatedId")
    @OptIn(InternalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedidos, container, false)

        spinner = view.findViewById(R.id.spinner)



        spinnerProduct()
        return view
    }

    private fun spinnerProduct() {
        service = retrofit.create(APIServices::class.java)
        service.getProductos().enqueue(object : Callback<getProducts> {



            override fun onResponse(call: Call<getProducts>, response: Response<getProducts>) {
                println(response)
                if (response.isSuccessful) {
                    println(response)
                    val productos = response.body()?.data
                    productos?.let {
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            it.map { producto -> producto.cNombreProduct }
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner.adapter = adapter

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