
package com.ms.encuestas.models.segcen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UsuarioBE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UsuarioBE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ApellidoMaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApellidoPaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuditoriaCreacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuditoriaModificacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bloqueado" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CargoFormal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodUsuario" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CodigoCadena1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoCadena2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoIntermediario" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CodigoNumerico1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CodigoNumerico2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Compania" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Departamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmailInterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaBaja" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaIngreso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaSincronizacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdTrabajador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdUsuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdUsuarioJefe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NombreCompleto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NombreJefe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Observacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OficinaCompania" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pais" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Provincia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SincronizacionPendiente" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TelefonoMovil" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "UsuarioBE", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", propOrder = {
    "apellidoMaterno",
    "apellidoPaterno",
    "auditoriaCreacion",
    "auditoriaModificacion",
    "bloqueado",
    "cargoFormal",
    "codUsuario",
    "codigoCadena1",
    "codigoCadena2",
    "codigoIntermediario",
    "codigoNumerico1",
    "codigoNumerico2",
    "compania",
    "departamento",
    "direccion",
    "emailInterno",
    "fax",
    "fechaBaja",
    "fechaIngreso",
    "fechaSincronizacion",
    "idTrabajador",
    "idUsuario",
    "idUsuarioJefe",
    "nombreCompleto",
    "nombreJefe",
    "nombres",
    "observacion",
    "oficinaCompania",
    "pais",
    "provincia",
    "sincronizacionPendiente",
    "telefono",
    "telefonoMovil",
    "unidadOrganizacional"
})
public class UsuarioBE {

    @XmlElementRef(name = "ApellidoMaterno", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> apellidoMaterno;
    @XmlElementRef(name = "ApellidoPaterno", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> apellidoPaterno;
    @XmlElementRef(name = "AuditoriaCreacion", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> auditoriaCreacion;
    @XmlElementRef(name = "AuditoriaModificacion", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> auditoriaModificacion;
    @XmlElement(name = "Bloqueado")
    protected Boolean bloqueado;
    @XmlElementRef(name = "CargoFormal", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cargoFormal;
    @XmlElement(name = "CodUsuario")
    protected Integer codUsuario;
    @XmlElementRef(name = "CodigoCadena1", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codigoCadena1;
    @XmlElementRef(name = "CodigoCadena2", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codigoCadena2;
    @XmlElement(name = "CodigoIntermediario")
    protected Integer codigoIntermediario;
    @XmlElement(name = "CodigoNumerico1")
    protected Integer codigoNumerico1;
    @XmlElement(name = "CodigoNumerico2")
    protected Integer codigoNumerico2;
    @XmlElementRef(name = "Compania", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> compania;
    @XmlElementRef(name = "Departamento", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> departamento;
    @XmlElementRef(name = "Direccion", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> direccion;
    @XmlElementRef(name = "EmailInterno", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> emailInterno;
    @XmlElementRef(name = "Fax", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fax;
    @XmlElementRef(name = "FechaBaja", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fechaBaja;
    @XmlElementRef(name = "FechaIngreso", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fechaIngreso;
    @XmlElementRef(name = "FechaSincronizacion", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fechaSincronizacion;
    @XmlElementRef(name = "IdTrabajador", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idTrabajador;
    @XmlElementRef(name = "IdUsuario", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idUsuario;
    @XmlElementRef(name = "IdUsuarioJefe", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idUsuarioJefe;
    @XmlElementRef(name = "NombreCompleto", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombreCompleto;
    @XmlElementRef(name = "NombreJefe", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombreJefe;
    @XmlElementRef(name = "Nombres", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombres;
    @XmlElementRef(name = "Observacion", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> observacion;
    @XmlElementRef(name = "OficinaCompania", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> oficinaCompania;
    @XmlElementRef(name = "Pais", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> pais;
    @XmlElementRef(name = "Provincia", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> provincia;
    @XmlElement(name = "SincronizacionPendiente")
    protected Boolean sincronizacionPendiente;
    @XmlElementRef(name = "Telefono", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> telefono;
    @XmlElementRef(name = "TelefonoMovil", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> telefonoMovil;
    @XmlElementRef(name = "UnidadOrganizacional", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", type = JAXBElement.class, required = false)
    protected JAXBElement<String> unidadOrganizacional;

    /**
     * Gets the value of the apellidoMaterno property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Sets the value of the apellidoMaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setApellidoMaterno(JAXBElement<String> value) {
        this.apellidoMaterno = value;
    }

    /**
     * Gets the value of the apellidoPaterno property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Sets the value of the apellidoPaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setApellidoPaterno(JAXBElement<String> value) {
        this.apellidoPaterno = value;
    }

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
     * Gets the value of the bloqueado property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBloqueado() {
        return bloqueado;
    }

    /**
     * Sets the value of the bloqueado property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBloqueado(Boolean value) {
        this.bloqueado = value;
    }

    /**
     * Gets the value of the cargoFormal property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCargoFormal() {
        return cargoFormal;
    }

    /**
     * Sets the value of the cargoFormal property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCargoFormal(JAXBElement<String> value) {
        this.cargoFormal = value;
    }

    /**
     * Gets the value of the codUsuario property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodUsuario() {
        return codUsuario;
    }

    /**
     * Sets the value of the codUsuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodUsuario(Integer value) {
        this.codUsuario = value;
    }

    /**
     * Gets the value of the codigoCadena1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodigoCadena1() {
        return codigoCadena1;
    }

    /**
     * Sets the value of the codigoCadena1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodigoCadena1(JAXBElement<String> value) {
        this.codigoCadena1 = value;
    }

    /**
     * Gets the value of the codigoCadena2 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodigoCadena2() {
        return codigoCadena2;
    }

    /**
     * Sets the value of the codigoCadena2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodigoCadena2(JAXBElement<String> value) {
        this.codigoCadena2 = value;
    }

    /**
     * Gets the value of the codigoIntermediario property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodigoIntermediario() {
        return codigoIntermediario;
    }

    /**
     * Sets the value of the codigoIntermediario property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodigoIntermediario(Integer value) {
        this.codigoIntermediario = value;
    }

    /**
     * Gets the value of the codigoNumerico1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodigoNumerico1() {
        return codigoNumerico1;
    }

    /**
     * Sets the value of the codigoNumerico1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodigoNumerico1(Integer value) {
        this.codigoNumerico1 = value;
    }

    /**
     * Gets the value of the codigoNumerico2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodigoNumerico2() {
        return codigoNumerico2;
    }

    /**
     * Sets the value of the codigoNumerico2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodigoNumerico2(Integer value) {
        this.codigoNumerico2 = value;
    }

    /**
     * Gets the value of the compania property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCompania() {
        return compania;
    }

    /**
     * Sets the value of the compania property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCompania(JAXBElement<String> value) {
        this.compania = value;
    }

    /**
     * Gets the value of the departamento property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDepartamento() {
        return departamento;
    }

    /**
     * Sets the value of the departamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDepartamento(JAXBElement<String> value) {
        this.departamento = value;
    }

    /**
     * Gets the value of the direccion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDireccion() {
        return direccion;
    }

    /**
     * Sets the value of the direccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDireccion(JAXBElement<String> value) {
        this.direccion = value;
    }

    /**
     * Gets the value of the emailInterno property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEmailInterno() {
        return emailInterno;
    }

    /**
     * Sets the value of the emailInterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEmailInterno(JAXBElement<String> value) {
        this.emailInterno = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFax(JAXBElement<String> value) {
        this.fax = value;
    }

    /**
     * Gets the value of the fechaBaja property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFechaBaja() {
        return fechaBaja;
    }

    /**
     * Sets the value of the fechaBaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFechaBaja(JAXBElement<String> value) {
        this.fechaBaja = value;
    }

    /**
     * Gets the value of the fechaIngreso property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * Sets the value of the fechaIngreso property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFechaIngreso(JAXBElement<String> value) {
        this.fechaIngreso = value;
    }

    /**
     * Gets the value of the fechaSincronizacion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFechaSincronizacion() {
        return fechaSincronizacion;
    }

    /**
     * Sets the value of the fechaSincronizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFechaSincronizacion(JAXBElement<String> value) {
        this.fechaSincronizacion = value;
    }

    /**
     * Gets the value of the idTrabajador property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdTrabajador() {
        return idTrabajador;
    }

    /**
     * Sets the value of the idTrabajador property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdTrabajador(JAXBElement<String> value) {
        this.idTrabajador = value;
    }

    /**
     * Gets the value of the idUsuario property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdUsuario() {
        return idUsuario;
    }

    /**
     * Sets the value of the idUsuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdUsuario(JAXBElement<String> value) {
        this.idUsuario = value;
    }

    /**
     * Gets the value of the idUsuarioJefe property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdUsuarioJefe() {
        return idUsuarioJefe;
    }

    /**
     * Sets the value of the idUsuarioJefe property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdUsuarioJefe(JAXBElement<String> value) {
        this.idUsuarioJefe = value;
    }

    /**
     * Gets the value of the nombreCompleto property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Sets the value of the nombreCompleto property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombreCompleto(JAXBElement<String> value) {
        this.nombreCompleto = value;
    }

    /**
     * Gets the value of the nombreJefe property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombreJefe() {
        return nombreJefe;
    }

    /**
     * Sets the value of the nombreJefe property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombreJefe(JAXBElement<String> value) {
        this.nombreJefe = value;
    }

    /**
     * Gets the value of the nombres property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombres() {
        return nombres;
    }

    /**
     * Sets the value of the nombres property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombres(JAXBElement<String> value) {
        this.nombres = value;
    }

    /**
     * Gets the value of the observacion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getObservacion() {
        return observacion;
    }

    /**
     * Sets the value of the observacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setObservacion(JAXBElement<String> value) {
        this.observacion = value;
    }

    /**
     * Gets the value of the oficinaCompania property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOficinaCompania() {
        return oficinaCompania;
    }

    /**
     * Sets the value of the oficinaCompania property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOficinaCompania(JAXBElement<String> value) {
        this.oficinaCompania = value;
    }

    /**
     * Gets the value of the pais property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPais() {
        return pais;
    }

    /**
     * Sets the value of the pais property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPais(JAXBElement<String> value) {
        this.pais = value;
    }

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProvincia() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProvincia(JAXBElement<String> value) {
        this.provincia = value;
    }

    /**
     * Gets the value of the sincronizacionPendiente property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSincronizacionPendiente() {
        return sincronizacionPendiente;
    }

    /**
     * Sets the value of the sincronizacionPendiente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSincronizacionPendiente(Boolean value) {
        this.sincronizacionPendiente = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTelefono(JAXBElement<String> value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the telefonoMovil property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTelefonoMovil() {
        return telefonoMovil;
    }

    /**
     * Sets the value of the telefonoMovil property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTelefonoMovil(JAXBElement<String> value) {
        this.telefonoMovil = value;
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
