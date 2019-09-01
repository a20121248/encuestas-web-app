
package com.ms.encuestas.services.segcen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccionBE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccionBE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Auditable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CodigoAccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoObjeto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdAccion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="IdObjeto" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="IdTipoAccion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NombreAccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NombreObjeto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Permitido" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SecuenciaAccion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccionBE", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", propOrder = {
    "auditable",
    "codigoAccion",
    "codigoObjeto",
    "idAccion",
    "idObjeto",
    "idTipoAccion",
    "nombreAccion",
    "nombreObjeto",
    "permitido",
    "secuenciaAccion"
})
public class AccionBE {

    @XmlElement(name = "Auditable")
    protected Boolean auditable;
    @XmlElementRef(name = "CodigoAccion", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codigoAccion;
    @XmlElementRef(name = "CodigoObjeto", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codigoObjeto;
    @XmlElement(name = "IdAccion")
    protected Integer idAccion;
    @XmlElement(name = "IdObjeto")
    protected Integer idObjeto;
    @XmlElement(name = "IdTipoAccion")
    protected Integer idTipoAccion;
    @XmlElementRef(name = "NombreAccion", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombreAccion;
    @XmlElementRef(name = "NombreObjeto", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombreObjeto;
    @XmlElement(name = "Permitido")
    protected Boolean permitido;
    @XmlElement(name = "SecuenciaAccion")
    protected Integer secuenciaAccion;

    /**
     * Gets the value of the auditable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAuditable() {
        return auditable;
    }

    /**
     * Sets the value of the auditable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAuditable(Boolean value) {
        this.auditable = value;
    }

    /**
     * Gets the value of the codigoAccion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodigoAccion() {
        return codigoAccion;
    }

    /**
     * Sets the value of the codigoAccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodigoAccion(JAXBElement<String> value) {
        this.codigoAccion = value;
    }

    /**
     * Gets the value of the codigoObjeto property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodigoObjeto() {
        return codigoObjeto;
    }

    /**
     * Sets the value of the codigoObjeto property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodigoObjeto(JAXBElement<String> value) {
        this.codigoObjeto = value;
    }

    /**
     * Gets the value of the idAccion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdAccion() {
        return idAccion;
    }

    /**
     * Sets the value of the idAccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdAccion(Integer value) {
        this.idAccion = value;
    }

    /**
     * Gets the value of the idObjeto property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdObjeto() {
        return idObjeto;
    }

    /**
     * Sets the value of the idObjeto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdObjeto(Integer value) {
        this.idObjeto = value;
    }

    /**
     * Gets the value of the idTipoAccion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdTipoAccion() {
        return idTipoAccion;
    }

    /**
     * Sets the value of the idTipoAccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdTipoAccion(Integer value) {
        this.idTipoAccion = value;
    }

    /**
     * Gets the value of the nombreAccion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombreAccion() {
        return nombreAccion;
    }

    /**
     * Sets the value of the nombreAccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombreAccion(JAXBElement<String> value) {
        this.nombreAccion = value;
    }

    /**
     * Gets the value of the nombreObjeto property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombreObjeto() {
        return nombreObjeto;
    }

    /**
     * Sets the value of the nombreObjeto property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombreObjeto(JAXBElement<String> value) {
        this.nombreObjeto = value;
    }

    /**
     * Gets the value of the permitido property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPermitido() {
        return permitido;
    }

    /**
     * Sets the value of the permitido property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPermitido(Boolean value) {
        this.permitido = value;
    }

    /**
     * Gets the value of the secuenciaAccion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSecuenciaAccion() {
        return secuenciaAccion;
    }

    /**
     * Sets the value of the secuenciaAccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSecuenciaAccion(Integer value) {
        this.secuenciaAccion = value;
    }

}
