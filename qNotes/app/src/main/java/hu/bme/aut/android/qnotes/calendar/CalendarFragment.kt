package hu.bme.aut.android.qnotes.calendar

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.qnotes.R
import hu.bme.aut.android.qnotes.databinding.FragmentCalendarBinding
import java.util.*

class CalendarFragment : Fragment() {

    private var isStartDateClicked: Boolean = false
    private lateinit var binding: FragmentCalendarBinding
    private var title: String = ""
    private var location: String = ""
    private var description: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartDate.setOnClickListener {
            isStartDateClicked = true
            findNavController().navigate(R.id.action_calendarFragment_to_datePickerDialogFragment)
        }

        binding.btnEndDate.setOnClickListener {
            isStartDateClicked = false
            findNavController().navigate(R.id.action_calendarFragment_to_datePickerDialogFragment)
        }

        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<DatePickerDialogFragment.DatePickerResult>(DATE_SELECTED_KEY)
            ?.observe(viewLifecycleOwner) {
                loadDate()
            }

        binding.btnSave.setOnClickListener {
            initInfo()
            insertToCalendar()
        }
    }

    private fun initInfo() {
        title = binding.etTitle.text.toString()
        location = binding.etLocation.text.toString()
        description = binding.etDescription.text.toString()
    }

    private fun insertToCalendar() {
        val startMillis: Long = Calendar.getInstance().run {
            set(startDate.year, startDate.month, startDate.day, 0, 0)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run {
            set(endDate.year, endDate.month, endDate.day, 0, 0)
            timeInMillis
        }
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
            .putExtra(CalendarContract.Events.TITLE, title)
            .putExtra(CalendarContract.Events.DESCRIPTION, description)
            .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
        startActivity(intent)
    }

    private fun loadDate() {
        if (isStartDateClicked) {
            binding.tStartDate.text = "$year.$month.$day."
            startDate = CalendarDate(year.toInt(), month.toInt(), day.toInt())
        }
        else {
            binding.tEndDate.text = "$year.$month.$day."
            endDate = CalendarDate(year.toInt(), month.toInt(), day.toInt())
        }
    }

    companion object {
        const val DATE_SELECTED_KEY = "date_selected"
        var year: String = ""
        var month: String = ""
        var day: String = ""
        var startDate: CalendarDate = CalendarDate()
        var endDate: CalendarDate = CalendarDate()
    }

}