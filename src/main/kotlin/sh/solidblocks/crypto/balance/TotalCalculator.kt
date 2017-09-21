package sh.solidblocks.crypto.balance

class TotalCalculator {
    fun calculate(tickers: CoinmarketcapTickers, wallet: Wallet): WalletWithTotal {
        val result = mutableListOf<CoinBalanceWithTotal>()
        wallet.coinBalance.forEach { coinBalance ->
            val coinmarketcapTicker = tickers.findTicker(coinBalance.currency.currencyCode)
            result.add(CoinBalanceWithTotal(coinBalance.currency, coinBalance.amount, coinBalance.amount.multiply(coinmarketcapTicker.priceBTC)))
        }
        val total = result.map { it.amountInBTC }.reduce { a, b -> a.add(b) }
        return WalletWithTotal(result, total)
    }

}
