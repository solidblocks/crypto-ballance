package sh.solidblocks.crypto.balance

class ExchangeWalletsApp(configs: List<ExchangeConfig>) {
    private val walletFilter = WalletFilter()
    private val totalCalculator = TotalCalculator()
    private val coinmarketcapClient = CoinmarketcapClientImpl()
    private val cryptocompareClient = CryptocompareClient()
    private val exchangeServiceList = ExchangeFactory().create(configs)

    fun fetchAllWallets(): Wallets {
        val tickers = coinmarketcapClient.getTickers()
        val fiatBTCPrice = cryptocompareClient.fetchFiatPriceFor("BTC")
        val walletList = exchangeServiceList
                .map { it.fetchWallet() }
                .map { walletFilter.filterOutEmptyBalances(it) }
                .map { totalCalculator.calculate(tickers, fiatBTCPrice, it) }
        return Wallets(walletList, walletList.map { it.total }.reduce { a, b -> a.add(b) })
    }

}
