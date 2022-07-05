package com.example.common.settings

interface UserSessionStorage {

    fun saveUserSessionId(userId: String)

    fun getUserSessionId(): String
}