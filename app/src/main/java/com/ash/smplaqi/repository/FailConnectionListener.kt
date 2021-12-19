package com.ash.smplaqi.repository

/**
 * This is on of the requirement of the project, to show socket failure
 */
interface FailConnectionListener {
    fun onSocketFailure()
}