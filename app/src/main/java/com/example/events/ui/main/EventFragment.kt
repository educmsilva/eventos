package com.example.events.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.events.R
import com.example.events.util.formatter.MaskUtil
import com.example.events.util.glide.GlideApp
import com.example.events.util.model.CheckInRequest
import com.example.events.util.model.CheckInResponse
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
    private var checkInRequest = CheckInRequest("", "", "")

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
        initObservers()
        configureClickListeners()
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

    private fun initObservers() {
        observerCheckInResponse()
        observerErrorMessage()
    }


    private fun observerCheckInResponse() {
        viewModel.getSucessMessageLive()
            .observe(viewLifecycleOwner, Observer(this::showMessageSucessCheckIn))
    }

    private fun showMessageSucessCheckIn(checkInResponse: CheckInResponse?) {
        if (checkInResponse?.code == "200") {
            Toast.makeText(context, "Check-In realizado com sucesso", Toast.LENGTH_LONG).show()
            viewModel.clearMessages()
        }
    }

    private fun observerErrorMessage() {
        viewModel.getErrorMessageLive()
            .observe(viewLifecycleOwner, Observer(this::updateErrorMessage))
    }

    private fun updateErrorMessage(errorMessage: String?) {
        if (errorMessage != null) {
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupView(view: View) {
        val evento =
            viewModel.getEventosLive().value?.find { it.id == arguments?.getString("EVENT_ID") }
        evento?.let {

            checkInRequest.eventId = it.id

            val entradaPlaceHolder: String =
                getString(R.string.entrada_placeholder) + "\n" + MaskUtil.formatPrice(it.price)

            val dataPlaceHolder: String =
                getString(R.string.date_placeholder) + "\n" + MaskUtil.formatDateWithTime(it.date)

            view.textView_eventDate.text = dataPlaceHolder
            view.textView_eventTitle.text = it.title
            view.textView_eventPrice.text = entradaPlaceHolder
            view.textView_eventDescription.text = it.description
            latlng = LatLng(it.latitude, it.longitude)

            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

            GlideApp.with(view.imageView_Evento.context)
                .load(it.image)
                .transition(withCrossFade(factory))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.colorLight)
                .error(R.drawable.ic_event_white_24dp)
                .into(view.imageView_Evento)
        }
    }

    private fun configureClickListeners() {
        imageCheckInButton.setOnClickListener {
            CheckInFragment.newInstance(checkInRequest.eventId)
                .show(parentFragmentManager, CheckInFragment.TAG)
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