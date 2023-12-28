package com.example.antikolektor.Adapter.MultiAdapter

sealed class ElementOfList {
    data class DataDate(val date: String) : ElementOfList()
    data class Item(var number: String, var type: String, var date: String, var duration: String) :
        ElementOfList()
}