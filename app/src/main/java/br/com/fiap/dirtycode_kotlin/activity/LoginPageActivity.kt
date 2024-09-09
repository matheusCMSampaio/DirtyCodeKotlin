package com.example.dirtycode_reactnative.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import br.com.fiap.dirtycode_kotlin.R

class LoginPageActivity : Activity() {
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.login_page_layout)
        val bntCadastr = findViewById<Button>(R.id.btnCadastro)

        bntCadastr.setOnClickListener {
            val intent = Intent(this@LoginPageActivity, CadastroPageActivity::class.java)
            startActivity(intent)
        }
    }
}