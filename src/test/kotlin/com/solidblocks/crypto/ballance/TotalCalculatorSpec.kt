package com.solidblocks.crypto.ballance

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.knowm.xchange.currency.Currency
import java.math.BigDecimal

object TotalCalculatorSpec : Spek({

    val totalCalculator = TotalCalculator()

    describe("Total calculator") {

        on("calculate single result") {
            val tickers = CoinmarketcapTickers(listOf(CoinmarketcapTicker("Litecoin", "LTC", BigDecimal(0.1), BigDecimal(123123.123))))
            val wallet = Wallet(listOf(CoinBalance(Currency.LTC, BigDecimal(10.0))))
            val total = totalCalculator.calculate(tickers, wallet)
            val totalCoinBalance = total.coinBalanceList[0]

            it("should return currency symbol") {
                assertThat(totalCoinBalance.currency, equalTo(Currency.LTC))
            }

            it("should enhance result with rate") {
                assertThat(totalCoinBalance.amountInBTC.toDouble(), equalTo(BigDecimal(1.0).toDouble()))
            }
        }
    }
})
