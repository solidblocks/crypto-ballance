package sh.solidblocks.crypto.balance

import org.knowm.xchange.currency.Currency
import java.math.BigDecimal

data class Wallet(val coinBalance: List<CoinBalance>)

data class CoinBalance(val currency: Currency, val amount: BigDecimal)

data class CoinBalanceWithTotal(val currency: Currency, val amount: BigDecimal, val amountInBTC: BigDecimal)

data class WalletWithTotal(val coinBalanceList: List<CoinBalanceWithTotal>, val total: BigDecimal)

data class Wallets(val wallets: List<WalletWithTotal>, val total: BigDecimal)

