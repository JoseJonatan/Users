package com.f8fit.userssp

data class User(val id: Long, var name: String, var lastName: String, var url: String) {
    fun getFulNaame(): String = "$name $lastName"
}
