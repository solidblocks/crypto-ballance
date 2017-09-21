package sh.solidblocks.crypto.balance

import org.knowm.xchange.ExchangeSpecification
import org.knowm.xchange.currency.Currency
import java.math.BigDecimal


data class ExchangeClientConfig(val specification: ExchangeSpecification)

data class ExchangeClientConfigList(var clientConfigList: List<ExchangeClientConfig>)

data class Wallet(val coinBalance: List<CoinBalance>)

data class CoinBalance(val currency: Currency, val amount: BigDecimal)

data class CoinBalanceWithTotal(val currency: Currency, val amount: BigDecimal, val amountInBTC: BigDecimal)

data class TotalBalances(val coinBalanceList: List<CoinBalanceWithTotal>, val total: BigDecimal)

