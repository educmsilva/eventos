package com.example.events.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.events.R
import com.example.events.util.formatter.MaskUtil
import com.example.events.util.glide.GlideApp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_event.view.*


class EventFragment : DialogFragment(), OnMapReadyCallback {

    private val viewModel: EventosViewModel by activityViewModels()
    private var latlng: LatLng? = null
    private var mapViewEvent: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        mapViewEvent = view.findViewById(R.id.mapView) as MapView
        mapViewEvent!!.onCreate(savedInstanceState)
        mapViewEvent!!.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        mapViewEvent!!.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapViewEvent!!.onResume()
    }

    override fun onStop() {
        super.onStop()
        mapViewEvent!!.onStop()
    }

    override fun onPause() {
        mapViewEvent!!.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapViewEvent!!.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapViewEvent!!.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapViewEvent!!.onSaveInstanceState(outState)
    }

    private fun setupView(view: View) {
        val evento =
            viewModel.getEventosLive().value?.find { it.id == arguments?.getString("EVENT_ID") }
        evento?.let {
            view.textView_eventDate.text = MaskUtil.formatDateWithTime(it.date)
            view.textView_eventTitle.text = it.title
            view.textView_eventPrice.text = MaskUtil.formatPrice(it.price)
            view.textView_eventDescription.text = it.description
            latlng = LatLng(it.latitude, it.longitude)
            GlideApp.with(view.imageView_Evento.context)
                .load(it.image)
                .error(R.drawable.ic_calendar_today_black_24dp)
                .into(view.imageView_Evento)
        }
    }

    companion object {
        const val TAG = "SimpleDialog"
        private const val EVENT_ID = "EVENT_ID"
        fun newInstance(eventID: String): EventFragment {
            val args = Bundle()
            args.putString(EVENT_ID, eventID)
            val fragment = EventFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            with(map) {
                moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16f))
                addMarker(latlng?.let {
                    MarkerOptions().position(it).title(textView_eventTitle.text.toString())
                })
            }
        }
    }

}