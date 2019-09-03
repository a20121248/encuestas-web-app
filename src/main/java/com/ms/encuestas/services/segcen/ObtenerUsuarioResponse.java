
package com.ms.encuestas.services.segcen;

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
 *         &lt;element name="ObtenerUsuarioResult" type="{http://schemas.datacontract.org/2004/07/SegCen.BE}UsuarioBE" minOccurs="0"/>
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
    "obtenerUsuarioResult"
})
@XmlRootElement(name = "ObtenerUsuarioResponse")
public class ObtenerUsuarioResponse {

    @XmlElementRef(name = "ObtenerUsuarioResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<UsuarioBE> obtenerUsuarioResult;

    /**
     * Gets the value of the obtenerUsuarioResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link UsuarioBE }{@code >}
     *     
     */
    public JAXBElement<UsuarioBE> getObtenerUsuarioResult() {
        return obtenerUsuarioResult;
    }

    /**
     * Sets the value of the obtenerUsuarioResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link UsuarioBE }{@code >}
     *     
     */
    public void setObtenerUsuarioResult(JAXBElement<UsuarioBE> value) {
        this.obtenerUsuarioResult = value;
    }

}
