package hu.bme.aut.android.qnotes.calendar

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import java.io.Serializable
import java.util.*

class DatePickerDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener{

    private val c: Calendar = Calendar.getInstance()

    data class DatePickerResult(
        val year: Int,
        val month: Int,
        val dayOfMonth: Int,
    ) : Serializable

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val result = DatePickerResult(year, month, dayOfMonth)
        CalendarFragment.year = year.toString()
        CalendarFragment.month = month.toString()
        CalendarFragment.day = dayOfMonth.toString()
        findNavController()
            .previousBackStackEntry
            ?.savedStateHandle
            ?.set(CalendarFragment.DATE_SELECTED_KEY, result)
    }
}