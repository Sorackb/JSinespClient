package org.lucassouza.jsinespclient;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
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

  private static final Pattern PLATE_FORMAT = Pattern.compile("^[a-zA-Z]{3}[0-9]{4}$");
  private static final Pattern PLATE_MERCOSUL_FORMAT = Pattern.compile("^[a-zA-Z]{3}[0-9]{1}[a-zA-Z]{1}[0-9]{2}$");
  private static final String SPECIAL = "[^a-zA-Z\\d]";
  private static final String URL = "https://cidadao.sinesp.gov.br/sinesp-cidadao/mobile/consultar-placa/v4";
  private static final String SECRET = "#8.1.0#g8LzUadkEHs7mbRqbX5l";
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static final Map<String, String> HEADERS;
  private static final SinespClient CLIENT;

  static {
    CLIENT = new SinespClient();
  }

  static {
    HEADERS = new HashMap<>();
    HEADERS.put("User-Agent", "SinespCidadao / 3.0.2.1 CFNetwork / 758.2.8 Darwin / 15.0.0");
    HEADERS.put("Host", "cidadao.sinesp.gov.br");
  }

  public static Result search(String plate) {
    if (!validate(plate)) {
      throw new RuntimeException("Formato de placa inválido! Utilize o formato \"AAA999\" ou \"AAA-9999\".");
    }

    return CLIENT.request(plate);
  }

  private Result request(String plate) {
    String content;
    Connection connection;
    Document xml;

    content = generateBody(plate);

    try {
      connection = Jsoup.connect(URL)
              .validateTLSCertificates(false)
              .headers(HEADERS)
              .requestBody(content)
              .method(Method.POST);
      xml = Jsoup.parse(connection.execute().body(), "", Parser.xmlParser());
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    return parseResult(xml);
  }

  private static Boolean validate(String plate) {
    plate = plate.replaceAll(SPECIAL, "");

    return (PLATE_FORMAT.matcher(plate).find() || PLATE_MERCOSUL_FORMAT.matcher(plate).find());
  }

  private static String generateBody(String plate) {
    Request request = new Request();

    request.setToken(generateToken(plate));
    request.setLatitude(generateLatitude());
    request.setLongitude(generateLongitude());
    request.setUuid("");//UUID.randomUUID().toString() // RFC 4122 Class 4 random UUID    
    request.setDate(generateDate());
    request.setPlate(plate);

    return request.toXML();
  }

  /**
   * Generates SHA1 token as HEX based on specified and secret key.
   *
   * @param plate
   * @return token based on plate
   */
  private static String generateToken(String plate) {
    return sha1Hex(plate + SECRET, plate);
  }

  private static String sha1Hex(String secret, String input) {
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
  private static double generateCoordinate() {
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
  private static double generateLatitude() {
    return generateCoordinate() - 38.5290245;
  }

  /**
   * Generates random longitude
   *
   * @return random longitude
   */
  private static double generateLongitude() {
    return generateCoordinate() - 3.7506985;
  }

  private static String generateDate() {
    return LocalDateTime.now().format(DATE_FORMAT);
  }

  private static Result parseResult(Document xml) {
    Result result = new Result();

    result.setReturnCode(Integer.parseInt(xml.select("return > codigoRetorno").first().text()));
    result.setReturnMessage(xml.select("return > mensagemRetorno").first().text());

    if (0 == result.getReturnCode()) {
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
    }

    return result;
  }
}
