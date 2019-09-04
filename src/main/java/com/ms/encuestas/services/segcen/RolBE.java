
package com.ms.encuestas.services.segcen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RolBE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RolBE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AuditoriaCreacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuditoriaModificacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoRol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DescripcionRol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdRol" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NombreRol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SuperRol" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="TipoRol" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="UnidadOrganizacional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RolBE", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", propOrder = {
    "auditoriaCreacion",
    "auditoriaModificacion",
    "codigoRol",
    "descripcionRol",
    "idRol",
    "nombreRol",
    "superRol",
    "tipoRol",
    "unidadOrganizacional"
})
public class RolBE {

    @XmlElementRef(name = "AuditoriaCreacion", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> auditoriaCreacion;
    @XmlElementRef(name = "AuditoriaModificacion", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> auditoriaModificacion;
    @XmlElementRef(name = "CodigoRol", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codigoRol;
    @XmlElementRef(name = "DescripcionRol", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> descripcionRol;
    @XmlElement(name = "IdRol")
    protected Integer idRol;
    @XmlElementRef(name = "NombreRol", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombreRol;
    @XmlElement(name = "SuperRol")
    protected Boolean superRol;
    @XmlElement(name = "TipoRol")
    protected Integer tipoRol;
    @XmlElementRef(name = "UnidadOrganizacional", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> unidadOrganizacional;

    /**
     * Gets the value of the auditoriaCreacion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAuditoriaCreacion() {
        return auditoriaCreacion;
    }

    /**
     * Sets the value of the auditoriaCreacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAuditoriaCreacion(JAXBElement<String> value) {
        this.auditoriaCreacion = value;
    }

    /**
     * Gets the value of the auditoriaModificacion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAuditoriaModificacion() {
        return auditoriaModificacion;
    }

    /**
     * Sets the value of the auditoriaModificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAuditoriaModificacion(JAXBElement<String> value) {
        this.auditoriaModificacion = value;
    }

    /**
     * Gets the value of the codigoRol property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodigoRol() {
        return codigoRol;
    }

    /**
     * Sets the value of the codigoRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodigoRol(JAXBElement<String> value) {
        this.codigoRol = value;
    }

    /**
     * Gets the value of the descripcionRol property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescripcionRol() {
        return descripcionRol;
    }

    /**
     * Sets the value of the descripcionRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescripcionRol(JAXBElement<String> value) {
        this.descripcionRol = value;
    }

    /**
     * Gets the value of the idRol property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdRol() {
        return idRol;
    }

    /**
     * Sets the value of the idRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdRol(Integer value) {
        this.idRol = value;
    }

    /**
     * Gets the value of the nombreRol property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombreRol() {
        return nombreRol;
    }

    /**
     * Sets the value of the nombreRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombreRol(JAXBElement<String> value) {
        this.nombreRol = value;
    }

    /**
     * Gets the value of the superRol property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuperRol() {
        return superRol;
    }

    /**
     * Sets the value of the superRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuperRol(Boolean value) {
        this.superRol = value;
    }

    /**
     * Gets the value of the tipoRol property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTipoRol() {
        return tipoRol;
    }

    /**
     * Sets the value of the tipoRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTipoRol(Integer value) {
        this.tipoRol = value;
    }

    /**
     * Gets the value of the unidadOrganizacional property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    /**
     * Sets the value of the unidadOrganizacional property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUnidadOrganizacional(JAXBElement<String> value) {
        this.unidadOrganizacional = value;
    }

}
