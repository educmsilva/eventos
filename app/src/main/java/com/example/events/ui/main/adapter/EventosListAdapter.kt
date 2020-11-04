package com.example.events.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.events.R
import com.example.events.util.formatter.MaskUtil
import com.example.events.util.glide.GlideApp
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
            it.bindView(evento, context)
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
        fun bindView(event: Eventos, context: Context) {
            val image = itemView.imageView_Evento
            val eventDate = itemView.textView_eventDate
            val eventTitle = itemView.textView_eventTitle
            val eventPrice = itemView.textView_eventPrice

            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

            GlideApp.with(context)
                .load(event.image)
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.colorLight)
                .error(R.drawable.ic_event_white_24dp)
                .into(image)

            val entradaPlaceHolder: String =
                context.getString(R.string.entrada_placeholder) + "\n" + MaskUtil.formatPrice(event.price)

            val dataPlaceHolder: String =
                context.getString(R.string.date_placeholder) + "\n" + MaskUtil.formatDateWithTime(
                    event.date
                )

            eventDate.text = dataPlaceHolder
            eventTitle.text = event.title
            eventPrice.text = entradaPlaceHolder
        }
    }
}