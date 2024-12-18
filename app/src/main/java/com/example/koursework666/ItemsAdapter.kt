package com.example.koursework666

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(var items: List<Item>, var context: Context) : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
        val desc: TextView = view.findViewById(R.id.item_list_desc)
        val count: TextView = view.findViewById(R.id.item_list_count)
        val price: TextView = view.findViewById(R.id.item_list_price)
        val btn: Button = view.findViewById(R.id.item_list_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.title
        holder.desc.text = item.desc
        holder.count.text = item.count.toString()
        holder.price.text = "${item.price}$"

        val imageId = context.resources.getIdentifier(item.image, "drawable", context.packageName)
        holder.image.setImageResource(imageId)

        holder.btn.setOnClickListener {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra("itemId", item.id) // Передаём itemId
            intent.putExtra("itemTitle", item.title)
            intent.putExtra("itemText", item.text)
            intent.putExtra("itemCount", item.count.toString())
            context.startActivity(intent)
        }
    }
}
