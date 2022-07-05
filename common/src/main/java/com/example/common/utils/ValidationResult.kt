package com.example.common.utils

data class ValidationResult(
    val successful: Boolean,
    var errorMessage: String? = null
)