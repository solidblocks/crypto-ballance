package sh.solidblocks.crypto.balance

class ExchangeWalletsApp(configs: List<ExchangeConfig>) {
    private val walletFilter = WalletFilter()
    private val totalCalculator = TotalCalculator()
    private val coinmarketcapClient = CoinmarketcapClientImpl()
    private val exchangeServiceList = ExchangeFactory().create(configs)

    fun fetchAllWallets(): Wallets {
        val tickers = coinmarketcapClient.getTickers()
        val walletList = exchangeServiceList
                .map { it.fetchWallet() }
                .map { walletFilter.filterOutEmptyBalances(it) }
                .map { totalCalculator.calculate(tickers, it) }
        return Wallets(walletList, walletList.map { it.total }.reduce { a, b -> a.add(b) })
    }

}
