package com.ph.confession.base

class Functions{

    fun category(): HashMap<Int, String> {
        var category = HashMap<Int, String>()

        category[1] = "My weird dream"
        category[2] = "What I fantasize"
        category[3] = "First time and unforgettable"
        category[4] = "My guilt"
        category[5] = "What I lie about"
        category[6] = "A Pain"
        category[7] = "What the truth is"
        category[8] = "My wild experience"
        category[9] = "A regret"
        category[10] = "Just saying"
        category[11] = "Joke time"
        category[12] = "Scary AF"
        category[13] = "3 AM Thoughts"

        return category
    }
}

class Api{
    val login: String = "http://10.0.2.2/phconfession/mobile/?view=login"
    val compose: String = "http://10.0.2.2/phconfession/mobile/?view=confess"
    val getAll : String = "http://10.0.2.2/phconfession/mobile/"
//    val login: String = "https://phconfession.com/mobile/?view=login"
//    val compose: String = "https://phconfession.com/mobile/?view=confess"
//    val getAll : String = "https://phconfession.com/mobile/"
}