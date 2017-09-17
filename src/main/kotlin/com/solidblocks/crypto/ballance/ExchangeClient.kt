package com.solidblocks.crypto.ballance

import org.knowm.xchange.ExchangeFactory

interface ExchangeClient {
    fun fetchWallet(config: ExchangeConfig): Wallet
}

class ExchangeClientImpl : ExchangeClient {
    override fun fetchWallet(config: ExchangeConfig): Wallet {
        val exchange = ExchangeFactory.INSTANCE.createExchange(config.specification)
        val accountInfo = exchange.accountService.accountInfo
        val coins = mutableListOf<CoinBalance>()
        accountInfo.wallets.values.forEach { coins.addAll(it.balances.map { CoinBalance(it.key, it.value.total) }) }
        return Wallet(coins)
    }
}
