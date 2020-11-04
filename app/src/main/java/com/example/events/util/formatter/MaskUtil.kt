package com.example.events.util.formatter

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MaskUtil {
    companion object {

        fun formatDate(date: Long): String? {
            return try {
                val formatDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateLong = Date(date * 1000L)
                val stringDate = formatDate.format(dateLong)
                stringDate.toString()
            } catch (e: java.lang.Exception) {
                e.message
            }
        }

        fun formatDateWithTime(date: Long): String? {
            return try {
                val formatDate = SimpleDateFormat("dd/MM/YYYY HH:mm", Locale.getDefault())
                val dateLong = Date(date * 1000L)
                val stringDate = formatDate.format(dateLong)
                stringDate.toString()
            } catch (e: java.lang.Exception) {
                e.message
            }
        }

        fun formatPrice(price: Double): String? {
            return try {
                val df = DecimalFormat("#,###.00")
                df.roundingMode = RoundingMode.CEILING
                "R$ " + df.format(price)
            } catch (e: java.lang.Exception) {
                e.message
            }
        }
    }
}