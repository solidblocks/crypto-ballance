Balances
=========


Sample configList

```yaml
wallets:
  - name: bittrex-1
    exchenge: bittrex
    apiKey: asdf
    secretKey: sdf
  - name: poloniex-1
    exchenge: poloniex
    apiKey: asdf
    secretKey: sdf
```

Sample output

```json
{
  "wallets": [
    {
      "name": "bittrex-1",
      "ballances": [
        {
          "coin": "ETH",
          "amount": 123.3213,
          "rate": 0.1,
          "amountBTC": 0.4
        }
      ],
      "totalInBTC": 3.123
    }
  ],
  "totalInBTC": 3.123
}

```

## Binance config

Binance sometimes fails on ssl handshake. You may need to add this certificate to your truststore. Follow instructions here [https://github.com/timmolter/XChange/wiki/Installing-SSL-Certificates-into-TrustStore](https://github.com/timmolter/XChange/wiki/Installing-SSL-Certificates-into-TrustStore)

```$bash
$ java InstallCert www.binance.com
```
