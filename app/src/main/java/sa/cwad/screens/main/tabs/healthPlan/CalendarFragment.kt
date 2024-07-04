package sa.cwad.screens.main.tabs.healthPlan

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import androidx.fragment.app.Fragment
import sa.cwad.R
import sa.cwad.databinding.CalendarBinding


class CalendarFragment : Fragment(R.layout.calendar) {

    private var handler: Handler? = null
    private var runnable: Runnable? = null
    private var firstSelectTime: Long = 0
    private var doubleSelectTimeDelta: Long = 500 // Задержка в миллисекундах между двумя выборами для определения двойного выбора

    private lateinit var binding: CalendarBinding

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

    fun saveNote(date: String, note: String) {
        val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(date, note)
        editor.apply()
    }

    fun loadNotes(): MutableMap<String, *>? {
        val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.all
    }

    fun listen() {
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            if (handler != null && runnable != null) {
                handler!!.removeCallbacks(runnable!!)
            }
            firstSelectTime = System.currentTimeMillis()

            runnable = object : Runnable {
                override fun run() {
                    if (System.currentTimeMillis() - firstSelectTime <= doubleSelectTimeDelta) {
                        // Обработка двойного выбора
                        showAlertDialog()
                    } else {
                        // Обработка обычного выбора
                    }
                }
            }
            handler = Handler(Looper.getMainLooper())
            handler!!.postDelayed(runnable!!, doubleSelectTimeDelta.toLong())
        }

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
                // User cancelled the dialog
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