package com.example.gymapp.ui.pedidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gym_system.interfaces.retrofit
import com.example.gym_system.interfaces.service
import com.example.gym_system.models.productos.Data
import com.example.gym_system.models.productos.getProducts
import com.example.gymapp.R
import com.example.gymapp.global.APIServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PedidosFragment : Fragment() {
    private lateinit var spinnerProductos: Spinner
    private lateinit var listaProductos: List<Data>
    private lateinit var listaPedidos: MutableList<Data>
    private lateinit var txtPrecioTotal: TextView
    private lateinit var txtProductosSeleccionados: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pedidos, container, false)

        spinnerProductos = view.findViewById(R.id.spinner_productos)
        listaProductos = emptyList()
        listaPedidos = mutableListOf()
        txtPrecioTotal = view.findViewById(R.id.txt_precio_total)
        txtProductosSeleccionados = view.findViewById(R.id.txt_productos_seleccionados)

        service = retrofit.create(APIServices::class.java) // Establecer conexión con la API

        cargarProductos()
        configurarSpinner()

        return view
    }


    private fun cargarProductos() {
        // Llamada a la API para obtener la lista de productos
        service.getProductos().enqueue(object : Callback<getProducts> {
            override fun onResponse(call: Call<getProducts>, response: Response<getProducts>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    listaProductos = result?.data ?: emptyList()
                    mostrarProductos(listaProductos)
                } else {
                    // Manejar error en la respuesta de la API
                }
            }

            override fun onFailure(call: Call<getProducts>, t: Throwable) {
                // Manejar error en la llamada a la API
            }
        })
    }

    private fun mostrarProductos(productos: List<Data>) {
        val productosConSeleccion = mutableListOf("Seleccionar producto") + productos.map { it.cNombreProduct }
        val adaptadorProductos = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, productosConSeleccion)
        adaptadorProductos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProductos.adapter = adaptadorProductos
    }

    private fun configurarSpinner() {
        spinnerProductos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val productoSeleccionado = spinnerProductos.getItemAtPosition(position) as String
                if (productoSeleccionado != "Seleccionar producto") {
                    modificarCantidadProducto(productoSeleccionado)
                }
                spinnerProductos.setSelection(0) // Reiniciar el Spinner a "Seleccionar producto"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada en el spinner
            }
        }
    }

    private fun modificarCantidadProducto(nombreProducto: String) {
        val producto = listaProductos.find { it.cNombreProduct == nombreProducto }
        if (producto != null) {
            val pedidoExistente = listaPedidos.find { it.cNombreProduct == nombreProducto }
            if (pedidoExistente != null) {
                pedidoExistente.quantity += 1
            } else {
                val nuevoProducto = Data(producto.cCodeBar, producto.cNombreProduct, producto.iIDProducto, 1, producto.price)
                listaPedidos.add(nuevoProducto)
            }
        }

        mostrarProductosSeleccionados()
        calcularPrecioTotal()
    }


    private fun mostrarProductosSeleccionados() {
        val sb = StringBuilder()
        for (producto in listaPedidos) {
            val totalProducto = producto.quantity * producto.price
            sb.append("Producto: ${producto.cNombreProduct} - Cantidad: ${producto.quantity} - Total: ${String.format(Locale.getDefault(), "%.2f", totalProducto)}\n")
        }
        txtProductosSeleccionados.text = sb.toString()
    }


    private fun calcularPrecioTotal() {
        var precioTotal = 0.0
        for (producto in listaPedidos) {
            precioTotal += producto.quantity * producto.price
        }
        txtPrecioTotal.text = String.format(Locale.getDefault(), "Total: %.2f", precioTotal)
    }
}












