
package br.com.fiap.dirtycode_kotlin.activity

import Usuario
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.fiap.dirtycode_kotlin.databinding.LoginPageLayoutBinding
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class LoginPageActivity : Activity() {

    private lateinit var binding: LoginPageLayoutBinding
    val BASE_URL = "https://webappdirtycodeapi.azurewebsites.net"
    val gson = Gson()
    private val client = OkHttpClient()

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        // Check if user is already logged in
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("is_logged_in", false)) {
            navigateToHomePage()
            return
        }

        binding = LoginPageLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val senha = edtSenha.text.toString()

                if (email.isNotEmpty() && senha.isNotEmpty()) {
                    Log.v("TESTES", "$email $senha")
                    fazerLogin(email, senha)
                }
            }
            btnCadastro.setOnClickListener {
                val intent = Intent(this@LoginPageActivity, CadastroPageActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun fazerLogin(email: String, senha: String) {
        val url = "$BASE_URL/login"
        val json = """{"email": "$email", "senha": "$senha"}"""
        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@LoginPageActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // Save login state
                    val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putBoolean("is_logged_in", true).apply()

                    runOnUiThread {
                        Toast.makeText(this@LoginPageActivity, "Login bem-sucedido", Toast.LENGTH_SHORT).show()
                        navigateToHomePage()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@LoginPageActivity, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun navigateToHomePage() {
        val intent = Intent(this@LoginPageActivity, HomePageActivity::class.java)
        startActivity(intent)
        finish()
    }
}
