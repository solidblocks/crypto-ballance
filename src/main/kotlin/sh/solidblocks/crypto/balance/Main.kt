package sh.solidblocks.crypto.balance

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal
import java.math.RoundingMode

fun main(args: Array<String>) {
    val config = TotalCalculator::class.java.getResource("/config.json").readText()
    val type = object : TypeToken<List<ExchangeConfig>>() {}.type
    val configs = Gson().fromJson<List<ExchangeConfig>>(config, type)

    val wallets = ExchangeWalletsApp(configs).fetchAllWallets()
    println(GsonBuilder().setPrettyPrinting().create().toJson(wallets))
    wallets.wallets.forEach {
        println("Wallet ${it.name}")
        it.coinBalanceList.forEach {
            println("${it.amount} ${it.currency} => ${rounded(it.amountInBTC)} BTC")
        }
        println("Total ${rounded(it.total)} BTC\n")
    }
    println("Total: ${rounded(wallets.total)} BTC")
}

private fun rounded(amount: BigDecimal) =
        amount.setScale(3, RoundingMode.DOWN)
