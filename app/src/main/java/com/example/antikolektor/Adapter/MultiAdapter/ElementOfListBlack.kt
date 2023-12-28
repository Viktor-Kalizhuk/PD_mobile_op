package com.example.antikolektor.Adapter.MultiAdapter

sealed class ElementOfListBlack {
    data class UserDate(
        var id: Int,
        var idAld: Int,
        var type: String,
        var phone: String,
        var subtype: String,
        var comment: String
    ) : ElementOfListBlack()

    data class BaseDate(
        var id: Int,
        var type: String,
        var phone: String,
        var subtype: String,
        var comment: String
    ) : ElementOfListBlack()
}