package br.com.fiap.dirtycode_kotlin.viewmodel

import Usuario
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class UsuarioViewModel: ViewModel() {

    private val _usuarioData = MutableLiveData<Usuario>()
    val usuarioData : LiveData<Usuario> = _usuarioData

    private val _usuarioList = MutableLiveData<ArrayList<Usuario>>()
    val usuarioList : LiveData<ArrayList<Usuario>> = _usuarioList


    init {
        _usuarioData.value = Usuario()
        _usuarioList.value = ArrayList()
    }
}