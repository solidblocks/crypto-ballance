package sh.solidblocks.crypto.ballance

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.hasElement
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.knowm.xchange.currency.Currency
import sh.solidblocks.crypto.balance.*
import java.math.BigDecimal

object WalletFilterSpec : Spek({
    describe("Wallet filter") {
        val walletFilter = WalletFilter()

        on("filterOutEmptyBalances") {
            val coinBalance = CoinBalance(Currency.AFN, BigDecimal.TEN)
            val zeroCoinBalance = CoinBalance(Currency.AED, BigDecimal(0E-8))
            val wallet = walletFilter.filterOutEmptyBalances(Wallet(listOf(coinBalance, zeroCoinBalance), "Wallet"))
            it("should return not empty balance") {
                assertThat(wallet.coinBalance, hasElement(coinBalance))
            }
            it("should filter out empty coins") {
                assertThat(wallet.coinBalance, !hasElement(zeroCoinBalance))
            }
        }
    }
})
