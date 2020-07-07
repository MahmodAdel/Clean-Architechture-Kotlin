package com.example.domain.model

import com.example.domain.annotation.TagName

data class Tag(@TagName val name: String, val message: String?)