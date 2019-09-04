
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
 *         &lt;element name="strIdUsuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "strIdUsuario"
})
@XmlRootElement(name = "ObtenerRolesUsuarioAplicacion")
public class ObtenerRolesUsuarioAplicacion {

    @XmlElementRef(name = "str_pCodigoAplicacion", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> strPCodigoAplicacion;
    @XmlElement(name = "int_pMayor")
    protected Integer intPMayor;
    @XmlElement(name = "int_pMinor")
    protected Integer intPMinor;
    @XmlElement(name = "int_pVersion")
    protected Integer intPVersion;
    @XmlElementRef(name = "strIdUsuario", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> strIdUsuario;

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
     * Gets the value of the strIdUsuario property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStrIdUsuario() {
        return strIdUsuario;
    }

    /**
     * Sets the value of the strIdUsuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStrIdUsuario(JAXBElement<String> value) {
        this.strIdUsuario = value;
    }

}
