package org.lucassouza.jsinespclient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

/**
 *
 * @author Lucas Souza [sorack@gmail.com]
 */
public class SinespClient {

  // Reference: https://github.com/victor-torres/sinesp-client
  private static final String SECRET = "TRwf1iBwvCoSboSscGne";
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private final String baseTemplate;

  public SinespClient() {
    this.baseTemplate = this.readTemplate();
  }

  private String readTemplate() {
    ClassLoader classLoader;
    File file;
    String content = null;

    try {
      classLoader = getClass().getClassLoader();
      file = new File(classLoader.getResource("file/body.xml").getFile());
      content = new String(Files.readAllBytes(file.toPath()));
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    return content;
  }

  public Result search(String plate) {
    return this.request(plate);
  }

  private Result request(String plate) {
    String content;
    Connection connection;
    Document xml;

    content = generateBody(plate);

    try {
      connection = Jsoup.connect("http://sinespcidadao.sinesp.gov.br/sinesp-cidadao/mobile/consultar-placa")
              .requestBody(content)
              .method(Method.POST);
      xml = Jsoup.parse(connection.execute().body(), "", Parser.xmlParser());
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    return this.parseResult(xml);
  }

  private String generateBody(String plate) {
    String token;
    double latitude;
    double longitude;
    String uuid;
    String date;
    String content;

    token = this.generateToken(plate);
    latitude = this.generateLatitude();
    longitude = this.generateLongitude();
    // RFC 4122 Class 4 random UUID
    uuid = UUID.randomUUID().toString();
    date = this.generateDate();
    content = this.baseTemplate
            .replace("{LATITUDE}", String.valueOf(latitude))
            .replace("{TOKEN}", token)
            .replace("{UUID}", uuid)
            .replace("{LONGITUDE}", String.valueOf(longitude))
            .replace("{DATE}", date)
            .replace("{PLATE}", plate);

    return content;
  }

  /**
   * Generates SHA1 token as HEX based on specified and secret key.
   *
   * @param plate
   * @return token based on plate
   */
  private String generateToken(String plate) {
    return sha1Hex(plate + SECRET, plate);
  }

  private String sha1Hex(String secret, String input) {
    String check = null;

    try {
      Mac sha1_HMAC = Mac.getInstance("HmacSHA1");
      SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA1");
      sha1_HMAC.init(secretKey);
      byte[] hash = sha1_HMAC.doFinal(input.getBytes());
      check = Hex.encodeHexString(hash);
    } catch (NoSuchAlgorithmException | InvalidKeyException exception) {
      throw new RuntimeException(exception);
    }

    return check;
  }

  /**
   * Generates random seed for latitude and longitude coordinates.
   *
   * @return random seed
   */
  private double generateCoordinate() {
    double seed;

    seed = 2000 / Math.sqrt(Math.random());
    seed = seed * Math.sin(2 * 3.141592654 * Math.random());

    return seed;
  }

  /**
   * Generates random latitude
   *
   * @return random latitude
   */
  private double generateLatitude() {
    return this.generateCoordinate() - 38.5290245;
  }

  /**
   * Generates random longitude
   *
   * @return random longitude
   */
  private double generateLongitude() {
    return this.generateCoordinate() - 3.7506985;
  }

  private String generateDate() {
    return LocalDateTime.now().format(DATE_FORMAT);
  }

  private Result parseResult(Document xml) {
    Result result = new Result();

    result.setReturnCode(Integer.parseInt(xml.select("return > codigoRetorno").first().text()));
    result.setReturnMessage(xml.select("return > mensagemRetorno").first().text());
    result.setStatusCode(Integer.parseInt(xml.select("return > codigoSituacao").first().text()));
    result.setStatusMessage(xml.select("return > situacao").first().text());
    result.setModel(xml.select("return > modelo").first().text());
    result.setBrand(xml.select("return > marca").first().text());
    result.setColor(xml.select("return > cor").first().text());
    result.setYear(Integer.parseInt(xml.select("return > ano").first().text()));
    result.setModelYear(Integer.parseInt(xml.select("return > anoModelo").first().text()));
    result.setPlate(xml.select("return > placa").first().text());
    result.setDate(xml.select("return > data").first().text());
    result.setState(xml.select("return > uf").first().text());
    result.setCity(xml.select("return > municipio").first().text());
    result.setVinCode(xml.select("return > chassi").first().text());

    return result;
  }

  public static void main(String[] args) {
    SinespClient client = new SinespClient();
    Result result;

    result = client.search("ABC1234");
    System.out.println(result.getModel());
  }
}
