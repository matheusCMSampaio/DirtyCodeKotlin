package br.com.fiap.dirtycode_kotlin.activity

import Usuario
import android.app.Activity
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


        binding = LoginPageLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val senha = edtSenha.text.toString()

                if (email.isNotEmpty() && senha.isNotEmpty()){
                    Log.v("TESTES", email+" " +senha)
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
        val json = """
        {
          "email": "$email",
          "senha": "$senha"
        }
    """.trimIndent()

        val requestBody = json.toRequestBody("application/json".toMediaType())
        Log.v("TESTES", json)

        val request = Request.Builder()
            .url("$BASE_URL/user/usuario")
            .post(requestBody)
            .build()

        val callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@LoginPageActivity, "Erro de rede: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        val intent = Intent(this@LoginPageActivity, ProfilePageActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginPageActivity, "Login falhou! Verifique suas credenciais.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        client.newCall(request).enqueue(callback)
    }

}
