package com.checkoutcom.checkoutpractical.domain.usecases

import com.checkoutcom.checkoutpractical.domain.repository.JetPackDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetPaymentStatusFromDataStoreUseCase @Inject constructor(
    private val jetPackDataStoreRepository: JetPackDataStoreRepository,
) : SuspendUseCase<Unit, Flow<@JvmSuppressWildcards String>> {

    override suspend fun execute(params: Unit): Flow<@JvmSuppressWildcards String> {
        return jetPackDataStoreRepository.getPaymentStatus()
    }
}