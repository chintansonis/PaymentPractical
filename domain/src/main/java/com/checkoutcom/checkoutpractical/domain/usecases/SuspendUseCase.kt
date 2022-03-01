package com.checkoutcom.checkoutpractical.domain.usecases

interface SuspendUseCase<in Params, out T> {
    suspend fun execute(params: Params): T
}