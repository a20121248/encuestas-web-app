
package com.ms.encuestas.models.segcen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccesosBE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccesosBE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Acciones" type="{http://schemas.datacontract.org/2004/07/SegCen.BE}ArrayOfAccionBE" minOccurs="0"/>
 *         &lt;element name="Objetos" type="{http://schemas.datacontract.org/2004/07/SegCen.BE}ArrayOfObjetoBE" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccesosBE", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", propOrder = {
    "acciones",
    "objetos"
})
public class AccesosBE {

    @XmlElementRef(name = "Acciones", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfAccionBE> acciones;
    @XmlElementRef(name = "Objetos", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfObjetoBE> objetos;

    /**
     * Gets the value of the acciones property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfAccionBE }{@code >}
     *     
     */
    public JAXBElement<ArrayOfAccionBE> getAcciones() {
        return acciones;
    }

    /**
     * Sets the value of the acciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfAccionBE }{@code >}
     *     
     */
    public void setAcciones(JAXBElement<ArrayOfAccionBE> value) {
        this.acciones = value;
    }

    /**
     * Gets the value of the objetos property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfObjetoBE }{@code >}
     *     
     */
    public JAXBElement<ArrayOfObjetoBE> getObjetos() {
        return objetos;
    }

    /**
     * Sets the value of the objetos property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfObjetoBE }{@code >}
     *     
     */
    public void setObjetos(JAXBElement<ArrayOfObjetoBE> value) {
        this.objetos = value;
    }

}
