package com.prestosa.dialogmanagers.customdialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.prestosa.dialogmanagers.databinding.EodContentDialogBinding

class EODContentDialog(
    context: Context,
    private val existingContent: String,
    private val onContentEntered: (String) -> Unit
) : Dialog(context) {

    private lateinit var binding: EodContentDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EodContentDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        windowDimensions()


        binding.cancelButton.setOnClickListener{dismiss()}
        binding.eodContentEditText.setText(existingContent) // Display existing content in the EditText

        binding.saveButton.setOnClickListener {
            val enteredText = binding.eodContentEditText.text.toString()
            onContentEntered.invoke(enteredText)
            dismiss()
        }

        binding.clearButton.setOnClickListener {
            val confirmDialog = AlertDialog.Builder(context)
                .setMessage("Are you sure you want to clear the content?")
                .setPositiveButton("Yes") { _, _ ->
                    binding.eodContentEditText.text.clear() // Clear the EditText
                    onContentEntered("") // Clear the content text
                    dismiss() // Dismiss the dialog
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss() // Dismiss the dialog without clearing content
                }
                .create()

            confirmDialog.show()
        }



    }

    private fun windowDimensions() {
        val window: Window? = this.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }




}