package br.com.fiap.dirtycode_kotlin.activity

import android.app.Activity
import android.os.Bundle
import br.com.fiap.dirtycode_kotlin.R
import br.com.fiap.dirtycode_kotlin.databinding.CadastroPageLayoutBinding

class CadastroPageActivity: Activity() {

    private lateinit var binding: CadastroPageLayoutBinding
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = CadastroPageLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

        }

    }
}