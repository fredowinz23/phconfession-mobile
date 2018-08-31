package com.ph.confession.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ph.confession.R
import com.ph.confession.base.api.LoginAsyncTask
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        link_signup.setOnClickListener {
//            val intent = Intent(this, SignupActivity::class.java)
//            startActivity(intent)
        }

        btn_login.setOnClickListener {

            // Send username and password to request
            LoginAsyncTask(this).execute(this.input_username.text.toString(), this.input_password.text.toString())

        }

    }

    override fun onBackPressed() {
        // Do nothing to prevent seeing previous page
    }
}
