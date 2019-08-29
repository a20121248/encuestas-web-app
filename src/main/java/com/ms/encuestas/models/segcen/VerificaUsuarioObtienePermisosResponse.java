
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
 *         &lt;element name="VerificaUsuarioObtienePermisosResult" type="{http://schemas.datacontract.org/2004/07/SegCen.BE}PermisosLBE" minOccurs="0"/>
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
    "verificaUsuarioObtienePermisosResult"
})
@XmlRootElement(name = "VerificaUsuarioObtienePermisosResponse")
public class VerificaUsuarioObtienePermisosResponse {

    @XmlElementRef(name = "VerificaUsuarioObtienePermisosResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<PermisosLBE> verificaUsuarioObtienePermisosResult;

    /**
     * Gets the value of the verificaUsuarioObtienePermisosResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PermisosLBE }{@code >}
     *     
     */
    public JAXBElement<PermisosLBE> getVerificaUsuarioObtienePermisosResult() {
        return verificaUsuarioObtienePermisosResult;
    }

    /**
     * Sets the value of the verificaUsuarioObtienePermisosResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PermisosLBE }{@code >}
     *     
     */
    public void setVerificaUsuarioObtienePermisosResult(JAXBElement<PermisosLBE> value) {
        this.verificaUsuarioObtienePermisosResult = value;
    }

}
