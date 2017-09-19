package com.solidblocks.crypto.ballance

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Reader
import java.math.BigDecimal


data class CoinmarketcapTicker(val name: String,
                               val symbol: String,
                               @SerializedName("price_btc") val priceBTC: BigDecimal,
                               @SerializedName("price_usd") val priceUSD: BigDecimal
) {

    class Deserializer : ResponseDeserializable<CoinmarketcapTicker> {
        override fun deserialize(reader: Reader) = Gson().fromJson(reader, CoinmarketcapTicker::class.java)
    }

    class ListDeserializer : ResponseDeserializable<List<CoinmarketcapTicker>> {
        override fun deserialize(reader: Reader): List<CoinmarketcapTicker> {
            val type = object : TypeToken<List<CoinmarketcapTicker>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }
}

class CoinmarketcapClient() {
    fun getTickers(): List<CoinmarketcapTicker> {
        val (_, _, result) = "https://api.coinmarketcap.com/v1/ticker?convert=GBP".httpGet()
                .responseObject(CoinmarketcapTicker.ListDeserializer())
        return result.get()
    }
}
