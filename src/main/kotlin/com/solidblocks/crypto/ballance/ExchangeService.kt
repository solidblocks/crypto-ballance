package com.solidblocks.crypto.ballance

import java.math.BigDecimal

interface ExchangeService {
    fun fetch(): Wallet
}

class ExchangeServiceImpl(private var exchangeClientConfigList: ExchangeClientConfigList, private var exchangeClient: ExchangeClient) : ExchangeService {

    override fun fetch(): Wallet {

        val coinBalanceList = mutableListOf<CoinBalance>()
        exchangeClientConfigList.clientConfigList.forEach {
            val fetchWallet = exchangeClient.fetchWallet(it)
            val filteredCoins = fetchWallet.coinBalance.filter { BigDecimal.ZERO.compareTo(it.amount) != 0 }
            coinBalanceList.addAll(filteredCoins)
        }
        return Wallet(coinBalanceList)
    }
}
