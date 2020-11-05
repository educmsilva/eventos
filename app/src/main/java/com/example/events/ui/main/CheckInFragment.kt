package com.example.events.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.events.R
import com.example.events.util.model.CheckInRequest
import kotlinx.android.synthetic.main.dialog_user_data.*

class CheckInFragment : DialogFragment() {
    private val viewModel: EventosViewModel by activityViewModels()
    private lateinit var checkInRequest: CheckInRequest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_user_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.getCheckInRequest().value != null) {
            checkInRequest = viewModel.getCheckInRequest().value!!
            userName.setText(checkInRequest.name)
            userEmail.setText(checkInRequest.email)
        } else {
            checkInRequest = CheckInRequest("", "", "")
        }
        configureButtons()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun configureButtons() {
        imageButtonCancel.setOnClickListener {
            dismiss()
        }
        imageButtonConfirm.setOnClickListener {
            checkInRequest.name = userName.text.toString()
            checkInRequest.email = userEmail.text.toString()
            checkIn(checkInRequest)
        }
    }

    private fun checkIn(checkInRequest: CheckInRequest) {
        val evento =
            viewModel.getEventosLive().value?.find { it.id == arguments?.getString("EVENT_ID") }
        evento?.let {
            this.checkInRequest.eventId = it.id
            viewModel.updateCheckinUserdata(this.checkInRequest)
            viewModel.checkIn(checkInRequest)
            dismiss()
        }
    }

    companion object {
        const val TAG = "SimpleDialog"
        private const val EVENT_ID = "EVENT_ID"
        fun newInstance(eventID: String): CheckInFragment {
            val args = Bundle()
            args.putString(EVENT_ID, eventID)
            val fragment = CheckInFragment()
            fragment.arguments = args
            return fragment
        }
    }
}