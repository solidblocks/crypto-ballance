package sh.solidblocks.crypto.balance

import org.knowm.xchange.currency.Currency
import java.math.BigDecimal
import java.math.RoundingMode

class TotalCalculator {
    fun calculate(tickers: CoinmarketcapTickers, fiatBTCPrice: FiatPrice, wallet: Wallet): WalletWithTotal {
        val result = wallet.coinBalance.map { coinBalance ->
            val totalInBTC = getTotalInBTC(tickers, fiatBTCPrice, coinBalance)
            CoinBalanceWithTotal(coinBalance.currency, coinBalance.amount, totalInBTC)
        }
        val total = result.map { it.amountInBTC }.reduce { a, b -> a.add(b) }
        return WalletWithTotal(result, total, wallet.name)
    }

    private fun getTotalInBTC(tickers: CoinmarketcapTickers, fiatBTCPrice: FiatPrice, coinBalance: CoinBalance): BigDecimal {
        if (isFiat(coinBalance.currency)) {
            val priceBTC = fiatBTCPrice.getPrice(coinBalance.currency.currencyCode)
            return coinBalance.amount.divide(priceBTC, RoundingMode.DOWN)
        } else {

            val coinmarketcapTicker = tickers.findTicker(coinBalance.currency.currencyCode)
            return coinBalance.amount.multiply(coinmarketcapTicker.priceBTC)
        }

    }

    private fun isFiat(currency: Currency): Boolean {
        return currency.currencyCode in listOf("EUR", "GBP", "USD")
    }

}
