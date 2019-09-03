
package com.ms.encuestas.services.segcen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObjetoBE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjetoBE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AuditoriaCreacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuditoriaModificacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoObjeto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DescripcionAplicacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdAplicacion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="IdObjeto" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="IdObjetoPadre" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NombreObjeto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NombreObjetoPadre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Orden" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TipoObjeto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjetoBE", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", propOrder = {
    "auditoriaCreacion",
    "auditoriaModificacion",
    "codigoObjeto",
    "descripcionAplicacion",
    "idAplicacion",
    "idObjeto",
    "idObjetoPadre",
    "nombreObjeto",
    "nombreObjetoPadre",
    "orden",
    "tipoObjeto"
})
public class ObjetoBE {

    @XmlElementRef(name = "AuditoriaCreacion", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> auditoriaCreacion;
    @XmlElementRef(name = "AuditoriaModificacion", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> auditoriaModificacion;
    @XmlElementRef(name = "CodigoObjeto", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codigoObjeto;
    @XmlElementRef(name = "DescripcionAplicacion", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> descripcionAplicacion;
    @XmlElement(name = "IdAplicacion")
    protected Integer idAplicacion;
    @XmlElement(name = "IdObjeto")
    protected Integer idObjeto;
    @XmlElement(name = "IdObjetoPadre")
    protected Integer idObjetoPadre;
    @XmlElementRef(name = "NombreObjeto", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombreObjeto;
    @XmlElementRef(name = "NombreObjetoPadre", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombreObjetoPadre;
    @XmlElement(name = "Orden")
    protected Integer orden;
    @XmlElementRef(name = "TipoObjeto", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tipoObjeto;

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
     * Gets the value of the descripcionAplicacion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescripcionAplicacion() {
        return descripcionAplicacion;
    }

    /**
     * Sets the value of the descripcionAplicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescripcionAplicacion(JAXBElement<String> value) {
        this.descripcionAplicacion = value;
    }

    /**
     * Gets the value of the idAplicacion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdAplicacion() {
        return idAplicacion;
    }

    /**
     * Sets the value of the idAplicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdAplicacion(Integer value) {
        this.idAplicacion = value;
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
     * Gets the value of the idObjetoPadre property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdObjetoPadre() {
        return idObjetoPadre;
    }

    /**
     * Sets the value of the idObjetoPadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdObjetoPadre(Integer value) {
        this.idObjetoPadre = value;
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
     * Gets the value of the nombreObjetoPadre property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombreObjetoPadre() {
        return nombreObjetoPadre;
    }

    /**
     * Sets the value of the nombreObjetoPadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombreObjetoPadre(JAXBElement<String> value) {
        this.nombreObjetoPadre = value;
    }

    /**
     * Gets the value of the orden property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * Sets the value of the orden property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrden(Integer value) {
        this.orden = value;
    }

    /**
     * Gets the value of the tipoObjeto property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoObjeto() {
        return tipoObjeto;
    }

    /**
     * Sets the value of the tipoObjeto property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoObjeto(JAXBElement<String> value) {
        this.tipoObjeto = value;
    }

}
