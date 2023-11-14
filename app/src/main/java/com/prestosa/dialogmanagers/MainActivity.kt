package com.prestosa.dialogmanagers

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import com.prestosa.dialogmanagers.customdialogs.EODContentDialog
import com.prestosa.dialogmanagers.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addADateButton.setOnClickListener{addADateDialog()}
        binding.addAContentButton.setOnClickListener { showEODContentDialog() }
        binding.sendButton.setOnClickListener { showSendMessageDialog() }
    }

    private fun addADateDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val calendars = Calendar.getInstance()
                calendars.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
                val selectedDate = dateFormat.format(calendars.time)
                binding.dateTextView.text = selectedDate
            }, year, month, day)

        datePickerDialog.show()
    }


    private fun showEODContentDialog() {
        val existingContent = binding.contentTextView.text.toString()
        val isDefaultText = existingContent.trim() == getString(R.string.empty_content)

        val eodDialog = EODContentDialog(this, if (isDefaultText) "" else existingContent) { enteredContent ->
            if (isDefaultText) {
                val content = enteredContent.ifBlank { getString(R.string.empty_content) }
                binding.contentTextView.text = content
            } else {
                if (enteredContent.isBlank()) {
                    binding.contentTextView.text = getString(R.string.empty_content)
                } else {
                    binding.contentTextView.text = enteredContent
                }
            }
        }
        eodDialog.show()
    }

    private fun showSendMessageDialog() {
        val sendMessageDialog = AlertDialog.Builder(this)

        sendMessageDialog.setMessage("Your EOD has been sent.")
            .setCancelable(false) // Set this to true if you dialog can be closed via tapping outside.
            .setPositiveButton("Okay") { dialog, _ -> dialog.dismiss() }

        val alert = sendMessageDialog.create()
        alert.setTitle("Send Successful")
        alert.show()
    }

}