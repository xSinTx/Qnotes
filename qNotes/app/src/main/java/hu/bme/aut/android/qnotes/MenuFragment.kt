package hu.bme.aut.android.qnotes

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.qnotes.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private lateinit var binding : FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.root.setBackgroundResource(R.drawable.background_night)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.root.setBackgroundResource(R.drawable.background)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_calendarFragment)
        }
        binding.bTodo.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_todoFragment)
        }
        binding.bExpenses.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_expensesFragment)
        }
    }
}