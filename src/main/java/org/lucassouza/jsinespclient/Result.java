package org.lucassouza.jsinespclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Lucas Souza [sorack@gmail.com]
 */
public class Result {

  private int returnCode;
  private String returnMessage;
  private int statusCode;
  private String statusMessage;
  private String model;
  private String brand;
  private String color;
  private int year;
  private int modelYear;
  private String plate;
  private String date;
  private String state;
  private String city;
  private String vinCode;

  public int getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(int returnCode) {
    this.returnCode = returnCode;
  }

  public String getReturnMessage() {
    return returnMessage;
  }

  public void setReturnMessage(String returnMessage) {
    this.returnMessage = returnMessage;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
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

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getModelYear() {
    return modelYear;
  }

  public void setModelYear(int modelYear) {
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
