package com.example.common.utils

import com.example.common.ui.UiText

data class ValidationResult(
    val successful: Boolean,
    var errorMessage: UiText? = null
)