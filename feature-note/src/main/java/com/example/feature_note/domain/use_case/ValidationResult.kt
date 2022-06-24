package com.example.feature_note.domain.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)