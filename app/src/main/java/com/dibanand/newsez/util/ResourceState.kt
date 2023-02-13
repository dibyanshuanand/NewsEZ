package com.dibanand.newsez.util

sealed class ResourceState<T>(
    val data: T? = null,
    val message: String? = null
) {

    // In-process state
    class Loading<T>() : ResourceState<T>()

    // Success state
    class Success<T>(data: T) : ResourceState<T>(data)

    // Error state
    class Error<T>(message: String, data: T? = null) : ResourceState<T>(data, message)

    // Undefined state
    class Blank<T>(message: String? = null) : ResourceState<T>(message = message)
}
