package sh.solidblocks.crypto.balance

import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal

data class FiatPrice(@SerializedName("USD") val priceUSD: BigDecimal,
                     @SerializedName("EUR") val priceEUR: BigDecimal,
                     @SerializedName("GBP") val priceGBP: BigDecimal
) {
    fun getPrice(symbol: String): BigDecimal {
        when (symbol) {
            "USD", "usd" -> return priceUSD
            "EUR", "eur" -> return priceEUR
            "GPB", "gpb" -> return priceGBP
            else -> throw IllegalArgumentException("Fiat symbol $symbol not supported")
        }
    }
}

class CryptocompareClient {
    fun fetchFiatPriceFor(symbol: String): FiatPrice {
        val (_, _, result) = "https://min-api.cryptocompare.com/data/price?fsym=$symbol&tsyms=USD,EUR,GBP".httpGet().responseString()
        val type = object : TypeToken<FiatPrice>() {}.type
        return Gson().fromJson<FiatPrice>(result.get(), type)
    }
}
