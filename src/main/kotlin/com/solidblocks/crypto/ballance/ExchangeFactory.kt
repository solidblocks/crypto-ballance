package com.solidblocks.crypto.ballance

import org.knowm.xchange.bittrex.v1.BittrexExchange
import org.knowm.xchange.gdax.GDAXExchange
import org.knowm.xchange.kraken.KrakenExchange
import org.knowm.xchange.poloniex.PoloniexExchange

data class ExchangeConfig(val type: String,
                          val apiKey: String,
                          val secretKey: String,
                          val additionalParams: Map<String, String>?,
                          val ignore: Boolean
)

class ExchangeFactory {
    private val exchangeMapping = hashMapOf(
            "bittrex" to BittrexExchange().defaultExchangeSpecification,
            "poloniex" to PoloniexExchange().defaultExchangeSpecification,
            "kraken" to KrakenExchange().defaultExchangeSpecification,
            "gdax" to GDAXExchange().defaultExchangeSpecification
    )

    fun create(configs: List<ExchangeConfig>): ExchangeClientConfigList {
        val specifications = configs.filter { !it.ignore }.map {
            val exchangeSpecification = exchangeMapping[it.type] ?: throw IllegalArgumentException("No exchange of type ${it.type}")
            exchangeSpecification.apiKey = it.apiKey
            exchangeSpecification.secretKey = it.secretKey
            if (it.additionalParams != null) {
                it.additionalParams.forEach { key, value -> exchangeSpecification.setExchangeSpecificParametersItem(key, value) }
            }
            ExchangeClientConfig(exchangeSpecification)
        }
        return ExchangeClientConfigList(specifications)
    }


}