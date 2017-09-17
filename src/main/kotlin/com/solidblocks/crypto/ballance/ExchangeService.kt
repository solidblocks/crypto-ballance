package com.solidblocks.crypto.ballance

import java.math.BigDecimal

class ExchangeService(var exchangeConfigList: ExchangeConfigList, var exchangeClient: ExchangeClient) {

    fun fetch(): TotalBalances {

        val coinBalanceList = mutableListOf<CoinBalance>()
        exchangeConfigList.configList.forEach {
            val fetchWallet = exchangeClient.fetchWallet(it)
            val filteredCoins = fetchWallet.coinBalance.filter { BigDecimal.ZERO.compareTo(it.amount) != 0 }
            coinBalanceList.addAll(filteredCoins)
        }
        return TotalBalances(coinBalanceList)
    }
}
