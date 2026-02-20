package com.example.expensetrackerpro.firebase_realtimedatabase

data class RealTimeUser(
    val items: RealTimeItems,
    val key: String? = ""
) {
    data class RealTimeItems(
        var userFirstName: String = "",
        var email: String = "",
        var password: String = ""
    ) {
        constructor() : this("", "", "")
    }
}
