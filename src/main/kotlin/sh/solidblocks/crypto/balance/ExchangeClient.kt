package sh.solidblocks.crypto.balance

import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.ExchangeSpecification

interface ExchangeClient {
    fun fetchWallet(): Wallet
}

class ExchangeClientImpl(private val specification: ExchangeSpecification) : ExchangeClient {
    override fun fetchWallet(): Wallet {
        val exchange = ExchangeFactory.INSTANCE.createExchange(specification)
        val accountInfo = exchange.accountService.accountInfo
        val coins = mutableListOf<CoinBalance>()
        accountInfo.wallets.values.forEach { coins.addAll(it.balances.map { CoinBalance(it.key, it.value.total) }) }
        return Wallet(coins)
    }
}
