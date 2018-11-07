package com.abdulaziz.submissionfinal.api.repository

interface ApiRepositoryCallback<T> {
    fun onDataLoaded(data: T?)
    fun onDataErrorLoad()
}