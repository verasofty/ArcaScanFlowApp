package com.onsigna.arcascanflowapp

class MainActivityVC(mainActivity: MainActivity) {
    companion object {
        lateinit var activity : MainActivity
    }

    init {
        activity = mainActivity
    }

}