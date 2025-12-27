package com.stis.titiktemu.data.model

data class User(
    val id: Long,
    val username: String,
    val email: String,
    val namaLengkap: String,
    val jabatan: String,
    val nimNip: String?,
    val noHp: String
)
