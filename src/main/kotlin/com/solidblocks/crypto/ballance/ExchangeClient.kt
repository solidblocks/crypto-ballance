package com.solidblocks.crypto.ballance

import org.knowm.xchange.ExchangeFactory

interface ExchangeClient {
    fun fetchWallet(clientConfig: ExchangeClientConfig): Wallet
}

class ExchangeClientImpl : ExchangeClient {
    override fun fetchWallet(clientConfig: ExchangeClientConfig): Wallet {
        val exchange = ExchangeFactory.INSTANCE.createExchange(clientConfig.specification)
        val accountInfo = exchange.accountService.accountInfo
        val coins = mutableListOf<CoinBalance>()
        accountInfo.wallets.values.forEach { coins.addAll(it.balances.map { CoinBalance(it.key, it.value.total) }) }
        return Wallet(coins)
    }
}
