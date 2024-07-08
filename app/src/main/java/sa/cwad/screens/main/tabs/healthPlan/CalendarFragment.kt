package sa.cwad.screens.main.tabs.healthPlan

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import sa.cwad.R
import sa.cwad.Repositories
import sa.cwad.databinding.CalendarBinding
import sa.cwad.screens.main.tabs.dashboard.DashboardViewModel
import sa.cwad.utils.viewModelCreator
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CalendarFragment : Fragment(R.layout.calendar) {

    private var firstSelectTime: Long = 0
    private var secondSelectTime: Long = 0
    private val DOUBLE_CLICK_TIME_DELTA = 500
    private var formattedDate: String? = null

    private lateinit var binding: CalendarBinding

    private val viewModel by viewModelCreator { CalendarViewModel() }

//    private val viewModel by viewModelCreator { BoxViewModel(getBoxId(), Repositories.boxesRepository) }

//    private val args by navArgs<BoxFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = CalendarBinding.bind(view)
        binding.note.text = loadNotes()?.values.toString()
        listen()


//        binding.root.setBackgroundColor(DashboardItemView.getBackgroundColor(getColorValue()))
//        binding.boxTextView.text = getString(R.string.this_is_box, getColorName())

//        binding.goBackButton.setOnClickListener { onGoBackButtonPressed() }

//        listenShouldExitEvent()
    }

    private fun saveNote(date: String, note: String) {
        val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(date, note)
        editor.apply()
    }

    private fun loadNotes(): MutableMap<String, *>? {
        val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.all
    }

    fun listen() {
        binding.calendarView.setOnDateChangeListener { v, year, month, dayOfMonth ->
            val date = createDate(year, month, dayOfMonth)

            when {
                firstSelectTime == 0L -> {
                    // Первый выбор даты
                    firstSelectTime = System.currentTimeMillis()
                    formattedDate = getDataOnClick(date)
                }
                (System.currentTimeMillis() - firstSelectTime <= DOUBLE_CLICK_TIME_DELTA) && (formattedDate == getDataOnClick(date))  -> {
                    // Время между двумя выборами меньше заданной задержки, считаем это двойным кликом
                    // Сброс времени первого выбора, чтобы готовиться к следующему двойному клику
                    firstSelectTime = 0
                    formattedDate = getDataOnClick(date)
                }
                else -> {
                    // Обычный выбор даты
                    Log.d("Single Click", "Одиночный клик по дате $year-$month-$dayOfMonth")

                    // Сброс времени первого выбора, если это не был двойной клик
                    firstSelectTime = 0
                    formattedDate = getDataOnClick(date)
                }
            }
        }

    }

    private fun createDate(year: Int, month: Int, dayOfMonth: Int): Date? {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1) // Месяцы в Calendar начинаются с 0
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        return calendar.time
    }
    private fun getDataOnClick(date: Date?): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun showAlertDialog() {
        val inflater = layoutInflater
        val dialogLayout: View = inflater.inflate(R.layout.enter_note, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogLayout)
            .setPositiveButton("ОК") { dialog, id ->
                val noteInput: EditText = dialogLayout.findViewById(R.id.edit_text_dialog)
                val note = noteInput.text.toString()
                if (note.isNotEmpty()) {
                    // Здесь вы можете обработать введенную заметку, например, сохранить ее
                    saveNote("04.07.2024", "testNote")
                }
            }
            .setNegativeButton("Отмена") { dialog, id ->
            }
        builder.show()
    }


//    private fun onGoBackButtonPressed() {
//        findNavController().popBackStack()
//    }
//
//    private fun listenShouldExitEvent() = viewModel.shouldExitEvent.observeEvent(viewLifecycleOwner) { shouldExit ->
//        if (shouldExit) {
//            // close the screen if the box has been deactivated
//            findNavController().popBackStack()
//        }
//    }
//
//    private fun getBoxId(): Int = args.boxId
//
//    private fun getColorValue(): Int = args.colorValue
//
//    private fun getColorName(): String = args.colorName
}