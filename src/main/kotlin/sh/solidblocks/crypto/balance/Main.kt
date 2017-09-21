package sh.solidblocks.crypto.balance

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun main(args: Array<String>) {
    val config = TotalCalculator::class.java.getResource("/config.json").readText()
    val type = object : TypeToken<List<ExchangeConfig>>() {}.type
    val configs = Gson().fromJson<List<ExchangeConfig>>(config, type)

    val wallets = ExchangeWalletsApp(configs).fetchAllWallets()
    println(wallets)

}
