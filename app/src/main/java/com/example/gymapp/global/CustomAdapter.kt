package com.example.gymapp.global

import com.example.gymapp.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(private val productList: MutableList<Product>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.product_name)
        val productQuantity: TextView = view.findViewById(R.id.product_quantity)
        val minusButton: Button = view.findViewById(R.id.minus_button)
        val plusButton: Button = view.findViewById(R.id.plus_button)
        val deleteButton: Button = view.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]

        holder.productName.text = product.name
        holder.productQuantity.text = product.quantity.toString()

        holder.minusButton.setOnClickListener {
            if (product.quantity > 1) {
                product.quantity -= 1
                holder.productQuantity.text = product.quantity.toString()
            }
        }

        holder.plusButton.setOnClickListener {
            product.quantity += 1
            holder.productQuantity.text = product.quantity.toString()
        }

        holder.deleteButton.setOnClickListener {
            productList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
