package sh.solidblocks.crypto.balance

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun main(args: Array<String>) {
    val config = TotalCalculator::class.java.getResource("/config.json").readText()
    val type = object : TypeToken<List<ExchangeConfig>>() {}.type
    val fromJson = Gson().fromJson<List<ExchangeConfig>>(config, type)
    val clientConfigList = ExchangeFactory().create(fromJson)

    val exchangeService = ExchangeServiceImpl(clientConfigList, ExchangeClientImpl())
    val wallet = exchangeService.fetch()
    val coinmarketcapClientImpl = CoinmarketcapClientImpl()
    val tickers = coinmarketcapClientImpl.getTickers()


    val totalCalculator = TotalCalculator()
    println(totalCalculator.calculate(tickers, wallet))
}
