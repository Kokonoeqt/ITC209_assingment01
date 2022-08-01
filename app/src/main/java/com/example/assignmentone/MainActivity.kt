package com.example.assignmentone

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firstName = findViewById<EditText>(R.id.input_first_name)
        val lastName = findViewById<EditText>(R.id.input_last_name)
        val employeeID = findViewById<EditText>(R.id.input_employeeID)
        val email = findViewById<EditText>(R.id.input_emailAddress)

        val regexMatch = "^[a-zA-z .-]+".toRegex()

        // errors array.
        val err = mutableListOf<String>()



        // submit button
        val button = findViewById<Button>(R.id.employee_sbmt_btn)
        button.setOnClickListener {
            // first name
            if (!regexMatch.matches(firstName.text)){
               err.add(0, "Error First name can only contain the alphabet, " +
                       "‘.’, ‘-‘, and blank spaces")
            }

            // last name
            if (!regexMatch.matches(lastName.text)){
                err.add(0, "Error Last name can only contain the alphabet, " +
                        "‘.’, ‘-‘, and blank spaces")
            }

            if (employeeID.text.isNotEmpty()){
                // employee ID
                if (employeeID.text[0] == '0'){
                    err.add(0, "Error Employee ID cannot start with 0")
                }
            }

            // email
            if (!isValidEmail(email.text)){
                err.add(0, "Email address is not valid")
            }

            val r = StringBuilder()
            val itr = err.listIterator()
            while(itr.hasNext()){
                r.append(itr.next())
                r.append("\n")
            }

            if (firstName.text.isNullOrBlank() ||
                lastName.text.isNullOrBlank() ||
                employeeID.text.isNullOrBlank() ||
                email.text.isNullOrBlank() ){
                dialogue(this, "All field must be filled.", err)
            }else if(err.size >= 1){
                dialogue(this, r.toString(), err)
            }
            else{
                dialogue(this, "Entered data: \n"
                        + "First Name: " + firstName.text
                        + "\nLast Name: " + lastName.text
                        + "\nEmployee ID: " + employeeID.text
                        + "\nEmail: " + email.text , err)
            }

        }
    }
}

private fun dialogue(context: Context, message: String, err: MutableList<String>) {
    val dlg = AlertDialog.Builder(context)
    dlg.setTitle("Message")
    dlg.setMessage(message)
    dlg.setPositiveButton("Ok") { dialog: DialogInterface, _: Int ->
        err.clear()
        dialog.dismiss()
    }
    dlg.setNegativeButton("Cancel") { dialog: DialogInterface, _: Int ->
        err.clear()
        dialog.dismiss()
    }
    dlg.create()
    dlg.show()
}

private fun isValidEmail(target: CharSequence): Boolean {
    return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
}