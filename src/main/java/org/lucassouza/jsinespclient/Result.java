package org.lucassouza.jsinespclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Lucas Souza [sorack@gmail.com]
 */
public class Result {

  private Integer returnCode;
  private String returnMessage;
  private Integer statusCode;
  private String statusMessage;
  private String model;
  private String brand;
  private String color;
  private Integer year;
  private Integer modelYear;
  private String plate;
  private String date;
  private String state;
  private String city;
  private String vinCode;

  public Integer getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(Integer returnCode) {
    this.returnCode = returnCode;
  }

  public String getReturnMessage() {
    return returnMessage;
  }

  public void setReturnMessage(String returnMessage) {
    this.returnMessage = returnMessage;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getModelYear() {
    return modelYear;
  }

  public void setModelYear(Integer modelYear) {
    this.modelYear = modelYear;
  }

  public String getPlate() {
    return plate;
  }

  public void setPlate(String plate) {
    this.plate = plate;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getVinCode() {
    return vinCode;
  }

  public void setVinCode(String vinCode) {
    this.vinCode = vinCode;
  }

  public String toJSON() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    return gson.toJson(this);
  }
}
