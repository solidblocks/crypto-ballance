package sh.solidblocks.crypto.balance

import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal


data class CoinmarketcapTicker(val name: String,
                               val symbol: String,
                               @SerializedName("price_btc") val priceBTC: BigDecimal,
                               @SerializedName("price_usd") val priceUSD: BigDecimal
)

class CoinmarketcapTickers(private val tickers: List<CoinmarketcapTicker>) {

    val tickersAliases = hashMapOf("STR" to "XLM")

    fun findTicker(tickerSymbol: String): CoinmarketcapTicker {
        val symbol = if (tickersAliases.contains(tickerSymbol)) tickersAliases[tickerSymbol] else tickerSymbol
        return tickers.last { it.symbol == symbol }
    }
}

interface CoinmarketcapClient {
    fun getTickers(): CoinmarketcapTickers
}

class CoinmarketcapClientImpl : CoinmarketcapClient {
    override fun getTickers(): CoinmarketcapTickers {
        val (_, _, result) = "https://api.coinmarketcap.com/v1/ticker?limit=0".httpGet().responseString()
        val type = object : TypeToken<List<CoinmarketcapTicker>>() {}.type
        return CoinmarketcapTickers(Gson().fromJson<List<CoinmarketcapTicker>>(result.get(), type))
    }
}
