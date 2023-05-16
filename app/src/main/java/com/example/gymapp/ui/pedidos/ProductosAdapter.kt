package com.example.gymapp.ui.pedidos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gym_system.models.productos.Data
import com.example.gymapp.R

class ProductosAdapter(private val productos: List<Data>) :
    RecyclerView.Adapter<ProductosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.producto_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nombreTextView: TextView = view.findViewById(R.id.nombreTextView)
        private val codigoTextView: TextView = view.findViewById(R.id.codigoTextView)

        fun bind(producto: Data) {
            nombreTextView.text = producto.cNombreProduct
            codigoTextView.text = producto.cCodeBar
        }
    }
}
