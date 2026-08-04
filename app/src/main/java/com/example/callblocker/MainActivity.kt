package com.example.callblocker

import android.app.Activity
import android.app.role.RoleManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val REQUEST_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEnable = findViewById<Button>(R.id.btnEnable)
        btnEnable.setOnClickListener {
            requestRole()
        }
    }

    override fun onResume() {
        super.onResume()
        checkRoleStatus()
    }

    private fun checkRoleStatus() {
        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(ROLE_SERVICE) as RoleManager
            if (roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) {
                tvStatus.text = getString(R.string.status_enabled)
                tvStatus.setTextColor(Color.parseColor("#008000")) // Green
            } else {
                tvStatus.text = getString(R.string.status_disabled)
                tvStatus.setTextColor(Color.RED)
            }
        }
    }

    private fun requestRole() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(ROLE_SERVICE) as RoleManager
            val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
            startActivityForResult(intent, REQUEST_ID)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                checkRoleStatus()
            }
        }
    }
}
