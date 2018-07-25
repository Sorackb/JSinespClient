  package org.lucassouza.jsinespclient;

  import java.io.ByteArrayOutputStream;
  import java.util.Iterator;
  import javax.xml.bind.JAXBContext;
  import javax.xml.bind.JAXBException;
  import javax.xml.bind.Marshaller;
  import javax.xml.bind.annotation.XmlElement;
  import javax.xml.bind.annotation.XmlRootElement;
  import javax.xml.bind.annotation.XmlTransient;
  import javax.xml.namespace.QName;
  import javax.xml.parsers.DocumentBuilderFactory;
  import javax.xml.parsers.ParserConfigurationException;
  import javax.xml.soap.MessageFactory;
  import javax.xml.soap.SOAPBody;
  import javax.xml.soap.SOAPEnvelope;
  import javax.xml.soap.SOAPException;
  import javax.xml.soap.SOAPHeader;
  import javax.xml.soap.SOAPMessage;
  import javax.xml.transform.OutputKeys;
  import javax.xml.transform.Transformer;
  import javax.xml.transform.TransformerException;
  import javax.xml.transform.TransformerFactory;
  import javax.xml.transform.stream.StreamResult;
  import org.w3c.dom.Document;

  /**
   *
   * @author Lucas Souza [sorack@gmail.com]
   */
  @XmlRootElement(name = "getStatus", namespace = "http://soap.ws.placa.service.sinesp.serpro.gov.br/")
  public class Request {

    private final String prefix = "v";

    private String plate;

    private String device;
    private Double latitude;
    private String operationalSystem;
    private String majorVersion;
    private String minorVersion;
    private String ip;
    private String token;
    private String uuid;
    private Double longitude;
    private String date;
    private String hash;

    public Request() {
      this.device = "LGE Nexus 5";
      this.operationalSystem = "ANDROID";
      this.majorVersion = "8.1.0";
      this.minorVersion = "4.1.5";
      this.ip = "127.0.0.1";
      this.hash = "8797e74f0d6eb7b1ff3dc114d4aa12d3";
    }

    @XmlElement(name = "a")
    public String getPlate() {
      return plate;
    }

    public void setPlate(String plate) {
      this.plate = plate;
    }

    @XmlTransient
    public String getDevice() {
      return device;
    }

    public void setDevice(String device) {
      this.device = device;
    }

    @XmlTransient
    public Double getLatitude() {
      return latitude;
    }

    public void setLatitude(Double latitude) {
      this.latitude = latitude;
    }

    @XmlTransient
    public String getOperationalSystem() {
      return operationalSystem;
    }

    public void setOperationalSystem(String operationalSystem) {
      this.operationalSystem = operationalSystem;
    }

    @XmlTransient
    public String getMajorVersion() {
      return majorVersion;
    }

    public void setMajorVersion(String majorVersion) {
      this.majorVersion = majorVersion;
    }

    @XmlTransient
    public String getMinorVersion() {
      return minorVersion;
    }

    public void setMinorVersion(String minorVersion) {
      this.minorVersion = minorVersion;
    }

    @XmlTransient
    public String getIp() {
      return ip;
    }

    public void setIp(String ip) {
      this.ip = ip;
    }

    @XmlTransient
    public String getToken() {
      return token;
    }

    public void setToken(String token) {
      this.token = token;
    }

    @XmlTransient
    public String getUuid() {
      return uuid;
    }

    public void setUuid(String uuid) {
      this.uuid = uuid;
    }

    @XmlTransient
    public Double getLongitude() {
      return longitude;
    }

    public void setLongitude(Double longitude) {
      this.longitude = longitude;
    }

    @XmlTransient
    public String getDate() {
      return date;
    }

    public void setDate(String date) {
      this.date = date;
    }

    @XmlTransient
    public String getHash() {
      return hash;
    }

    public void setHash(String hash) {
      this.hash = hash;
    }

    public String toXML() {
      Transformer transformer;
      Document document;
      Marshaller marshaller;
      SOAPMessage soapMessage;
      ByteArrayOutputStream outputStream;
      StreamResult result;
      String output;

      try {
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        marshaller = JAXBContext.newInstance(Request.class).createMarshaller();
        marshaller.marshal(this, document);
        soapMessage = MessageFactory.newInstance().createMessage();
        transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        this.fillEnvelope(soapMessage.getSOAPPart().getEnvelope());
        this.fillBody(soapMessage.getSOAPBody(), document);
        this.fillHeader(soapMessage.getSOAPHeader());
        outputStream = new ByteArrayOutputStream();
        result = new StreamResult(outputStream);
        transformer.transform(soapMessage.getSOAPPart().getContent(), result);
        output = outputStream.toString();
        output = fillXMLString(output);
      } catch (JAXBException | ParserConfigurationException | SOAPException | TransformerException ex) {
        throw new RuntimeException(ex);
      }

      return output;
    }

    private void fillEnvelope(SOAPEnvelope envelope) {
      Iterator prefixes;
      envelope.setPrefix(this.prefix);

      prefixes = envelope.getNamespacePrefixes();

      while (prefixes.hasNext()) {
        String currentPrefix = (String) prefixes.next();

        envelope.removeNamespaceDeclaration(currentPrefix);
      }
    }

    private void fillHeader(SOAPHeader soapHeader) throws SOAPException {
      soapHeader.setPrefix(this.prefix);

      soapHeader.addHeaderElement(new QName(soapHeader.getNamespaceURI(), "b")).setValue(this.device);
      soapHeader.addHeaderElement(new QName(soapHeader.getNamespaceURI(), "c")).setValue(this.operationalSystem);
      soapHeader.addHeaderElement(new QName(soapHeader.getNamespaceURI(), "d")).setValue(this.majorVersion);
      soapHeader.addHeaderElement(new QName(soapHeader.getNamespaceURI(), "e")).setValue(this.minorVersion);
      soapHeader.addHeaderElement(new QName(soapHeader.getNamespaceURI(), "f")).setValue(this.ip);
      soapHeader.addHeaderElement(new QName(soapHeader.getNamespaceURI(), "g")).setValue(this.token);
      soapHeader.addHeaderElement(new QName(soapHeader.getNamespaceURI(), "h")).setValue(String.valueOf(this.longitude));
      soapHeader.addHeaderElement(new QName(soapHeader.getNamespaceURI(), "i")).setValue(String.valueOf(this.latitude));
      soapHeader.addHeaderElement(new QName(soapHeader.getNamespaceURI(), "j")).setValue("");
      soapHeader.addHeaderElement(new QName(soapHeader.getNamespaceURI(), "k")).setValue(this.uuid);
      soapHeader.addHeaderElement(new QName(soapHeader.getNamespaceURI(), "l")).setValue(this.date);
      soapHeader.addHeaderElement(new QName(soapHeader.getNamespaceURI(), "m")).setValue(this.hash);
    }

    private String fillXMLString(String xml) {
      xml = xml.replace(" xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"", "");

      return xml;
    }

    private void fillBody(SOAPBody soapBody, Document document) throws SOAPException {
      soapBody.addDocument(document);
      soapBody.setPrefix(this.prefix);
    }
  }
