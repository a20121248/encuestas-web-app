
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
 *         &lt;element name="ObtenerRolesUsuarioAplicacionResult" type="{http://schemas.datacontract.org/2004/07/SegCen.BE}ArrayOfRolBE" minOccurs="0"/>
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
    "obtenerRolesUsuarioAplicacionResult"
})
@XmlRootElement(name = "ObtenerRolesUsuarioAplicacionResponse")
public class ObtenerRolesUsuarioAplicacionResponse {

    @XmlElementRef(name = "ObtenerRolesUsuarioAplicacionResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfRolBE> obtenerRolesUsuarioAplicacionResult;

    /**
     * Gets the value of the obtenerRolesUsuarioAplicacionResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRolBE }{@code >}
     *     
     */
    public JAXBElement<ArrayOfRolBE> getObtenerRolesUsuarioAplicacionResult() {
        return obtenerRolesUsuarioAplicacionResult;
    }

    /**
     * Sets the value of the obtenerRolesUsuarioAplicacionResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRolBE }{@code >}
     *     
     */
    public void setObtenerRolesUsuarioAplicacionResult(JAXBElement<ArrayOfRolBE> value) {
        this.obtenerRolesUsuarioAplicacionResult = value;
    }

}
