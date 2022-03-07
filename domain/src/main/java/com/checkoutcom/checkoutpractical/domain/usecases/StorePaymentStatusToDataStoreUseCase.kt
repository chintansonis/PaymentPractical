package com.checkoutcom.checkoutpractical.domain.usecases

import com.checkoutcom.checkoutpractical.domain.repository.JetPackDataStoreRepository
import javax.inject.Inject


class StorePaymentStatusToDataStoreUseCase @Inject constructor(
    private val jetPackDataStoreRepository: JetPackDataStoreRepository,
) : SuspendUseCase<String, Unit> {

    override suspend fun execute(params: String) =
        jetPackDataStoreRepository.passPaymentStatus(params)
}