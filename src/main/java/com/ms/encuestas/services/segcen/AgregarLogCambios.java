
package com.ms.encuestas.services.segcen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="str_pCodigoAplicacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="int_pMayor" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="int_pMinor" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="int_pVersion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="str_pIdUsuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="str_pNombreEntidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="str_pCampo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="str_pValorAnterior" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="str_pValorNuevo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="str_pHostName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="str_pIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "strPCodigoAplicacion",
    "intPMayor",
    "intPMinor",
    "intPVersion",
    "strPIdUsuario",
    "strPNombreEntidad",
    "strPCampo",
    "strPValorAnterior",
    "strPValorNuevo",
    "strPHostName",
    "strPIP"
})
@XmlRootElement(name = "AgregarLogCambios")
public class AgregarLogCambios {

    @XmlElementRef(name = "str_pCodigoAplicacion", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> strPCodigoAplicacion;
    @XmlElement(name = "int_pMayor")
    protected Integer intPMayor;
    @XmlElement(name = "int_pMinor")
    protected Integer intPMinor;
    @XmlElement(name = "int_pVersion")
    protected Integer intPVersion;
    @XmlElementRef(name = "str_pIdUsuario", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> strPIdUsuario;
    @XmlElementRef(name = "str_pNombreEntidad", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> strPNombreEntidad;
    @XmlElementRef(name = "str_pCampo", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> strPCampo;
    @XmlElementRef(name = "str_pValorAnterior", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> strPValorAnterior;
    @XmlElementRef(name = "str_pValorNuevo", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> strPValorNuevo;
    @XmlElementRef(name = "str_pHostName", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> strPHostName;
    @XmlElementRef(name = "str_pIP", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> strPIP;

    /**
     * Gets the value of the strPCodigoAplicacion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStrPCodigoAplicacion() {
        return strPCodigoAplicacion;
    }

    /**
     * Sets the value of the strPCodigoAplicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStrPCodigoAplicacion(JAXBElement<String> value) {
        this.strPCodigoAplicacion = value;
    }

    /**
     * Gets the value of the intPMayor property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIntPMayor() {
        return intPMayor;
    }

    /**
     * Sets the value of the intPMayor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIntPMayor(Integer value) {
        this.intPMayor = value;
    }

    /**
     * Gets the value of the intPMinor property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIntPMinor() {
        return intPMinor;
    }

    /**
     * Sets the value of the intPMinor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIntPMinor(Integer value) {
        this.intPMinor = value;
    }

    /**
     * Gets the value of the intPVersion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIntPVersion() {
        return intPVersion;
    }

    /**
     * Sets the value of the intPVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIntPVersion(Integer value) {
        this.intPVersion = value;
    }

    /**
     * Gets the value of the strPIdUsuario property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStrPIdUsuario() {
        return strPIdUsuario;
    }

    /**
     * Sets the value of the strPIdUsuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStrPIdUsuario(JAXBElement<String> value) {
        this.strPIdUsuario = value;
    }

    /**
     * Gets the value of the strPNombreEntidad property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStrPNombreEntidad() {
        return strPNombreEntidad;
    }

    /**
     * Sets the value of the strPNombreEntidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStrPNombreEntidad(JAXBElement<String> value) {
        this.strPNombreEntidad = value;
    }

    /**
     * Gets the value of the strPCampo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStrPCampo() {
        return strPCampo;
    }

    /**
     * Sets the value of the strPCampo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStrPCampo(JAXBElement<String> value) {
        this.strPCampo = value;
    }

    /**
     * Gets the value of the strPValorAnterior property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStrPValorAnterior() {
        return strPValorAnterior;
    }

    /**
     * Sets the value of the strPValorAnterior property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStrPValorAnterior(JAXBElement<String> value) {
        this.strPValorAnterior = value;
    }

    /**
     * Gets the value of the strPValorNuevo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStrPValorNuevo() {
        return strPValorNuevo;
    }

    /**
     * Sets the value of the strPValorNuevo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStrPValorNuevo(JAXBElement<String> value) {
        this.strPValorNuevo = value;
    }

    /**
     * Gets the value of the strPHostName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStrPHostName() {
        return strPHostName;
    }

    /**
     * Sets the value of the strPHostName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStrPHostName(JAXBElement<String> value) {
        this.strPHostName = value;
    }

    /**
     * Gets the value of the strPIP property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStrPIP() {
        return strPIP;
    }

    /**
     * Sets the value of the strPIP property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStrPIP(JAXBElement<String> value) {
        this.strPIP = value;
    }

}
