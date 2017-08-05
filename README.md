# JSinespClient

| PagSeguro     | PayPal      |
| ------------- |-------------|
[![ague com PagSeguro - é rápido, grátis e seguro!](https://stc.pagseguro.uol.com.br/public/img/botoes/doacoes/209x48-doar-laranja-assina.gif)](https://pag.ae/bhmK2Xf) | [![Make a donation](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=LKDGCQBKYBW5E)

Biblioteca para consulta de placa no território nacional na base de dados do Sistema Nacional de Informações de Segurança Pública (SINESP).

Para consultar utilize o método `SinespClient.search` enviando a placa desejada como parâmetro:

    org.lucassouza.jsinespclient.Result result = org.lucassouza.jsinespclient.SinespClient.search("BPG0795");

### Atenção

Esta implementação não possui nenhum vínculo oficial com o Sistema Nacional de Informações de Segurança Pública (SINESP).
