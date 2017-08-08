# JSinespClient

| PagSeguro     | PayPal      |
| ------------- |-------------|
[![Doe com PagSeguro - é rápido, grátis e seguro!](https://stc.pagseguro.uol.com.br/public/img/botoes/doacoes/209x48-doar-laranja-assina.gif)](https://pag.ae/bhmK2Xf) | [![Make a donation](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=LKDGCQBKYBW5E)

Biblioteca para consulta de placa no território nacional na base de dados do Sistema Nacional de Informações de Segurança Pública (SINESP).

Para consultar utilize o método `SinespClient.search` enviando a placa desejada como parâmetro.

## Exemplo

```java
org.lucassouza.jsinespclient.Result result = org.lucassouza.jsinespclient.SinespClient.search("BPG0795");
System.out.println(result.toJSON());
```

### Saída

```json
{
  "returnCode": 0,
  "returnMessage": "Sem erros.",
  "statusCode": 0,
  "statusMessage": "Sem restrição",
  "model": "VW/PARATI CL",
  "brand": "VW/PARATI CL",
  "color": "BRANCA",
  "year": 1992,
  "modelYear": 1992,
  "plate": "BPG0795",
  "date": "07/08/2017 às 14:39:54",
  "state": "SP",
  "city": "FERNANDOPOLIS",
  "vinCode": "************39562"
}
```

---

### Atenção

Esta implementação não possui nenhum vínculo oficial com o Sistema Nacional de Informações de Segurança Pública (SINESP).
