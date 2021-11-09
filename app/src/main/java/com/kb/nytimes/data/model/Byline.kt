package com.kb.nytimes.data.model

data class Byline(
    val organization: Any,
    val original: String,
    val person: List<Person>
)