package com.example.feature_authentication.domain.use_case

data class ValidationResult(
    val successful: Boolean,
    var errorMessage: String? = null
)