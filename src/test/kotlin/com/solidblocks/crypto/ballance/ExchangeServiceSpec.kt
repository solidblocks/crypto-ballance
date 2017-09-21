package com.solidblocks.crypto.ballance

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.hasElement
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.knowm.xchange.currency.Currency
import java.math.BigDecimal

object ExchangeServiceSpec : Spek({
    describe("Exchange service") {
        val singleExchangeConfig = ExchangeClientConfig(mock())
        val clientConfigList: ExchangeClientConfigList = ExchangeClientConfigList(listOf(singleExchangeConfig))
        val exchangeAdapter = mock<ExchangeClient>()
        val exchangeService = ExchangeServiceImpl(clientConfigList, exchangeAdapter)

        on("fetch") {
            val coinBalance = CoinBalance(Currency.AFN, BigDecimal.TEN)
            val zeroCoinBalance = CoinBalance(Currency.AED, BigDecimal(0E-8))
            val wallet = Wallet(listOf(coinBalance, zeroCoinBalance))
            whenever(exchangeAdapter.fetchWallet(singleExchangeConfig)).thenReturn(wallet)

            val balances = exchangeService.fetch()

            it("should call exchange adapter to fetch wallet") {
                verify(exchangeAdapter).fetchWallet(singleExchangeConfig)
            }

            it("should return fetched wallet") {
                assertThat(balances.coinBalance, hasElement(coinBalance))
            }
            it("should filter out empty coins") {
                assertThat(balances.coinBalance, !hasElement(zeroCoinBalance))
            }
        }
    }
})
