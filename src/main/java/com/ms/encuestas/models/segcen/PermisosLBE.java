
package com.ms.encuestas.models.segcen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PermisosLBE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PermisosLBE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Acciones" type="{http://schemas.datacontract.org/2004/07/SegCen.BE}ArrayOfAccionBE" minOccurs="0"/>
 *         &lt;element name="Error" type="{http://schemas.datacontract.org/2004/07/SegCen.BE}ErrorBE" minOccurs="0"/>
 *         &lt;element name="Usuario" type="{http://schemas.datacontract.org/2004/07/SegCen.BE}UsuarioBE" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PermisosLBE", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", propOrder = {
    "acciones",
    "error",
    "usuario"
})
public class PermisosLBE {

    @XmlElementRef(name = "Acciones", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfAccionBE> acciones;
    @XmlElementRef(name = "Error", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<ErrorBE> error;
    @XmlElementRef(name = "Usuario", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<UsuarioBE> usuario;

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
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ErrorBE }{@code >}
     *     
     */
    public JAXBElement<ErrorBE> getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ErrorBE }{@code >}
     *     
     */
    public void setError(JAXBElement<ErrorBE> value) {
        this.error = value;
    }

    /**
     * Gets the value of the usuario property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link UsuarioBE }{@code >}
     *     
     */
    public JAXBElement<UsuarioBE> getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link UsuarioBE }{@code >}
     *     
     */
    public void setUsuario(JAXBElement<UsuarioBE> value) {
        this.usuario = value;
    }

}
