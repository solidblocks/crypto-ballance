package com.solidblocks.crypto.ballance

import java.math.BigDecimal

interface ExchangeService {
    fun fetch(): Wallet
}

class ExchangeServiceImpl(private var exchangeConfigList: ExchangeConfigList, private var exchangeClient: ExchangeClient) : ExchangeService {

    override fun fetch(): Wallet {

        val coinBalanceList = mutableListOf<CoinBalance>()
        exchangeConfigList.configList.forEach {
            val fetchWallet = exchangeClient.fetchWallet(it)
            val filteredCoins = fetchWallet.coinBalance.filter { BigDecimal.ZERO.compareTo(it.amount) != 0 }
            coinBalanceList.addAll(filteredCoins)
        }
        return Wallet(coinBalanceList)
    }
}
