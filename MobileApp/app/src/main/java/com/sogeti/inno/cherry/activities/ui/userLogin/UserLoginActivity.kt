package com.sogeti.inno.cherry.activities.ui.userLogin



import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.log4k.Log4k
import com.log4k.i
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.MainActivity
import kotlinx.android.synthetic.main.activity_user_login.*


class UserLoginActivity : AppCompatActivity() {


    var _userEmailField: EditText? = null
    var _userPasswordField: EditText? = null
    var _userConnectButton: Button? = null
    var _userResetButton: Button? = null
    // var _progressBar2: ProgressBar = new ProgressBar

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        // get references to all views in activity_user_login
        _userEmailField = findViewById(R.id.userEmailField) as EditText
        _userPasswordField = findViewById(R.id.userPasswordField) as EditText
        _userConnectButton = findViewById(R.id.userConnectButton) as Button
        _userResetButton = findViewById(R.id.userResetButton) as Button
        _userConnectButton!!.setOnClickListener { userLogin() }


        // Clear userEmailField and userPasswordField if needed
        userResetButton.setOnClickListener {
            userEmailField.setText("")
            userPasswordField.setText("")

        }

        // Set up on-click listener
        userConnectButton.setOnClickListener {
            val user_name = userEmailField.text
            val user_password = userPasswordField.text


            Toast.makeText( this@UserLoginActivity, user_name, Toast.LENGTH_LONG).show()



            // Check if the combination user_name/user_password exist in database
            // and connect to PlayerListActivity


        }

    }



    fun userLogin() {
        Log.d(TAG, "login")

        if (!validate()) {
            onLoginFailed()
            return
        }

        _userConnectButton!!.isEnabled = false

        val email = _userEmailField!!.text.toString()
        val password = _userPasswordField!!.text.toString()


        // Beginning of authentication logic

        Log4k.i("Authenticating for user $email")
        // end of authentication logic


        android.os.Handler().postDelayed(
            {
                //on complete call either success or fail
                onLoginSuccess()
                // onLoginFailed()
            }, 3000)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == Activity.RESULT_OK) {

                // implement successful signup, by default we just finish
                // the Activity and log in automaticaly by finish()
                this.finish()
            }
        }
    }


    // Disable going back on MainActivity
    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    private fun onLoginSuccess() {
        _userConnectButton!!.isEnabled = true
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun onLoginFailed() {
        Toast.makeText(baseContext, "La connexion a échouée", Toast.LENGTH_LONG).show()
        _userConnectButton!!.isEnabled = true
    }

    private fun validate(): Boolean {
        var valid = true
        val email = _userEmailField!!.text.toString()
        val password = _userPasswordField!!.text.toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _userPasswordField!!.error = "Merci d'indiquer un couriel valide"
            valid = false
        } else {
            _userEmailField!!.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            _userPasswordField!!.error = "Veuillez entrer entre 4 et 10 caractères"
            valid = false
        } else {
            _userPasswordField!!.error = null
        }
        return valid

    }



    companion object {
        const val TAG = "UserLoginActivity"
        private val REQUEST_SIGNUP = 0
    }

}
