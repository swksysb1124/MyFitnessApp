package com.example.myfitnessapp.util

open class SingletonHolder<out Singleton, in Context>(private val constructor: (Context) -> Singleton) {

    @Volatile
    private var instance: Singleton? = null

    fun getInstance(arg: Context): Singleton {
        return instance ?: synchronized(this) {
            instance ?: constructor(arg).also { instance = it }
        }
    }
}