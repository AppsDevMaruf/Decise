package com.example.decise.api
import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String?
        get() = "No Internet Connection !" //TODO Language

}