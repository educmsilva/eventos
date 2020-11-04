package com.example.events.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.events.R
import com.example.events.util.model.Eventos
import kotlinx.android.synthetic.main.event_item.view.*

class EventosListAdapter(
    private val events: List<Eventos>,
    private val context: Context,
    private var onItemClickListener: (event: Eventos, position: Int) -> Unit
) : Adapter<EventosListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val evento = events[position]
        holder.let {
            it.bindView(evento)
            it.itemView.setOnClickListener {
                onItemClickListener(evento, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(event: Eventos) {
            val image = itemView.imageView_Evento
            val eventDate = itemView.textView_eventDate
            val eventTitle = itemView.textView_eventTitle
            val eventPrice = itemView.textView_eventPrice

            Glide.with(image.context)
                .load(event.image)
                .into(image)

            eventDate.text = event.date.toString()
            eventTitle.text = event.title
            eventPrice.text = event.price.toString()
        }
    }
}