
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
 *         &lt;element name="VerificaUsuarioObtienePermisosWithoutIPHostNameResult" type="{http://schemas.datacontract.org/2004/07/SegCen.BE}PermisosLBE" minOccurs="0"/>
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
    "verificaUsuarioObtienePermisosWithoutIPHostNameResult"
})
@XmlRootElement(name = "VerificaUsuarioObtienePermisosWithoutIPHostNameResponse")
public class VerificaUsuarioObtienePermisosWithoutIPHostNameResponse {

    @XmlElementRef(name = "VerificaUsuarioObtienePermisosWithoutIPHostNameResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<PermisosLBE> verificaUsuarioObtienePermisosWithoutIPHostNameResult;

    /**
     * Gets the value of the verificaUsuarioObtienePermisosWithoutIPHostNameResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PermisosLBE }{@code >}
     *     
     */
    public JAXBElement<PermisosLBE> getVerificaUsuarioObtienePermisosWithoutIPHostNameResult() {
        return verificaUsuarioObtienePermisosWithoutIPHostNameResult;
    }

    /**
     * Sets the value of the verificaUsuarioObtienePermisosWithoutIPHostNameResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PermisosLBE }{@code >}
     *     
     */
    public void setVerificaUsuarioObtienePermisosWithoutIPHostNameResult(JAXBElement<PermisosLBE> value) {
        this.verificaUsuarioObtienePermisosWithoutIPHostNameResult = value;
    }

}
