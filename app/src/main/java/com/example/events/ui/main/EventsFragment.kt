package com.example.events.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.events.R
import com.example.events.ui.main.adapter.EventosListAdapter
import com.example.events.util.model.Eventos
import kotlinx.android.synthetic.main.fragment_eventos.*

class EventsFragment : Fragment() {

    private val viewModel: EventsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_eventos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        getData()
    }

    private fun getData() {
        viewModel.getEventos()
    }

    private fun initObservers() {
        observerEventos()
        observerErrorMessage()
    }

    private fun observerEventos() {
        viewModel.getEventosLive()
            .observe(viewLifecycleOwner, Observer(this::updateEventos))
    }

    private fun updateEventos(eventos: List<Eventos>?) {
        if (eventos != null) {
            configureList(eventos)
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

    private fun configureList(eventos: List<Eventos>) {
        val recyclerView = event_list
        recyclerView.adapter =
            context?.let {
                EventosListAdapter(eventos, it) { evento, position ->
                    parentFragmentManager.let { it1 ->
                        EventFragment.newInstance(evento.id).show(
                            it1, EventFragment.TAG
                        )
                    }
                }
            }
        val layoutManager = StaggeredGridLayoutManager(
            1, StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.layoutManager = layoutManager
    }

    companion object {
        fun newInstance() = EventsFragment()
    }

}