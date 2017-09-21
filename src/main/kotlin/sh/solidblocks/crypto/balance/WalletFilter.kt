package sh.solidblocks.crypto.balance

import java.math.BigDecimal

class WalletFilter {

    fun filterOutEmptyBalances(wallet: Wallet): Wallet {
        val filteredCoins = wallet.coinBalance.filter { BigDecimal.ZERO.compareTo(it.amount) != 0 }
        return Wallet(filteredCoins)
    }
}
