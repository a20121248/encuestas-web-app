
package com.ms.encuestas.services.segcen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="AgregarLogLogonResult" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
    "agregarLogLogonResult"
})
@XmlRootElement(name = "AgregarLogLogonResponse")
public class AgregarLogLogonResponse {

    @XmlElement(name = "AgregarLogLogonResult")
    protected Integer agregarLogLogonResult;

    /**
     * Gets the value of the agregarLogLogonResult property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAgregarLogLogonResult() {
        return agregarLogLogonResult;
    }

    /**
     * Sets the value of the agregarLogLogonResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAgregarLogLogonResult(Integer value) {
        this.agregarLogLogonResult = value;
    }

}
