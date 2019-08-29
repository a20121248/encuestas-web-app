
package com.ms.encuestas.models.segcen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="ValidarEstadoCuentaResult" type="{http://schemas.datacontract.org/2004/07/SegCen.BE}ResultadoValidacionBE" minOccurs="0"/>
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
    "validarEstadoCuentaResult"
})
@XmlRootElement(name = "ValidarEstadoCuentaResponse")
public class ValidarEstadoCuentaResponse {

    @XmlElementRef(name = "ValidarEstadoCuentaResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ResultadoValidacionBE> validarEstadoCuentaResult;

    /**
     * Gets the value of the validarEstadoCuentaResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ResultadoValidacionBE }{@code >}
     *     
     */
    public JAXBElement<ResultadoValidacionBE> getValidarEstadoCuentaResult() {
        return validarEstadoCuentaResult;
    }

    /**
     * Sets the value of the validarEstadoCuentaResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ResultadoValidacionBE }{@code >}
     *     
     */
    public void setValidarEstadoCuentaResult(JAXBElement<ResultadoValidacionBE> value) {
        this.validarEstadoCuentaResult = value;
    }

}
