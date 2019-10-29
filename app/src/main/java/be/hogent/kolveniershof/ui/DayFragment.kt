package be.hogent.kolveniershof.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import be.hogent.kolveniershof.R
import be.hogent.kolveniershof.databinding.FragmentDayBinding
import be.hogent.kolveniershof.viewmodels.DayViewModel
import org.joda.time.DateTime

private const val ARG_WORKDAY_DATE = "workdayDate"
private const val ARG_WORKDAY_WEEKEND = "isWeekend"

class DayFragment : Fragment() {

    private var workdayDate : String? = null
    private var isWeekend: Boolean? = null

    companion object {
        @JvmStatic
        fun newInstance(workdayDate: DateTime?) =
            DayFragment().apply {
                arguments = Bundle().apply {
                    var date: String? = null
                    var weekend = false
                    if (workdayDate != null) {
                        date = workdayDate.toString("dd_MM_yyyy")
                        if (workdayDate.toString("e") == "6" || workdayDate.toString("e") == "7")
                            weekend = true
                    }
                    putString(ARG_WORKDAY_DATE, date)
                    putBoolean(ARG_WORKDAY_WEEKEND, weekend)
                }
            }
    }

    private lateinit var viewModel: DayViewModel
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workdayDate = it.getString(ARG_WORKDAY_DATE)
            isWeekend = it.getBoolean(ARG_WORKDAY_WEEKEND)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(DayViewModel::class.java)

        // Inflate the layout for this fragment
        val binding: FragmentDayBinding = if (isWeekend!!)
            DataBindingUtil.inflate(inflater, R.layout.fragment_weekend, container, false)
        else
            DataBindingUtil.inflate(inflater, R.layout.fragment_day, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var textAmActivity1: TextView = view.findViewById(R.id.textAmActivity1)
        textAmActivity1.text = workdayDate
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.dag_naam_test)
        sharedPrefs = activity!!.getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE)

        arguments?.getString("workdayDate")?.let { viewModel.getWorkdayByDateByUser(sharedPrefs.getString("TOKEN", "")!!, it, sharedPrefs.getString("ID", "")!!) }
        viewModel.workday.removeObservers(this)
        viewModel.workday.observe(this, Observer { workday ->
            // TODO hier code voor verbegen elementen en andere dergelijke zaken
        })

    }


}
