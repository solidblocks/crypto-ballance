package com.solidblocks.crypto.ballance
import org.knowm.xchange.ExchangeSpecification
import org.knowm.xchange.currency.Currency
import java.math.BigDecimal


data class ExchangeConfig(val specification: ExchangeSpecification)

data class ExchangeConfigList(var configList: List<ExchangeConfig>)

data class Wallet(val coinBalance: List<CoinBalance>)

data class CoinBalance(val currency: Currency, val amount: BigDecimal)

data class TotalBalances(val coinBalanceList: List<CoinBalance>)

