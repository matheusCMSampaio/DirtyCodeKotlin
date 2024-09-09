package com.example.dirtycode_reactnative.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import br.com.fiap.dirtycode_kotlin.R


class HomePageActivity: Activity() {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.home_page_layout)

        val btn = findViewById<Button>(R.id.btnIniciar)

        btn.setOnClickListener {
            val intent = Intent(this@HomePageActivity, LoginPageActivity::class.java)
            startActivity(intent)
        }
    }
}