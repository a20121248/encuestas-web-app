
package com.ms.encuestas.models.segcen;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ms.encuestas.models.segcen package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _ArrayOfRolBE_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "ArrayOfRolBE");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _UsuarioBE_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "UsuarioBE");
    private final static QName _RolBE_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "RolBE");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _ErrorBE_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "ErrorBE");
    private final static QName _ResultadoValidacionBE_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "ResultadoValidacionBE");
    private final static QName _PermisosLBE_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "PermisosLBE");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _ArrayOfAccionBE_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "ArrayOfAccionBE");
    private final static QName _ArrayOfObjetoBE_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "ArrayOfObjetoBE");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _AccionBE_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "AccionBE");
    private final static QName _ObjetoBE_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "ObjetoBE");
    private final static QName _AccesosBE_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "AccesosBE");
    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _ValidarEstadoCuentaResponseValidarEstadoCuentaResult_QNAME = new QName("http://tempuri.org/", "ValidarEstadoCuentaResult");
    private final static QName _ValidarEstadoCuentaCodigoAplicacion_QNAME = new QName("http://tempuri.org/", "codigoAplicacion");
    private final static QName _ValidarEstadoCuentaIdUsuario_QNAME = new QName("http://tempuri.org/", "idUsuario");
    private final static QName _AccionBECodigoObjeto_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "CodigoObjeto");
    private final static QName _AccionBENombreAccion_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "NombreAccion");
    private final static QName _AccionBECodigoAccion_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "CodigoAccion");
    private final static QName _AccionBENombreObjeto_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "NombreObjeto");
    private final static QName _ObtenerRolesUsuarioAplicacionStrPCodigoAplicacion_QNAME = new QName("http://tempuri.org/", "str_pCodigoAplicacion");
    private final static QName _ObtenerRolesUsuarioAplicacionStrIdUsuario_QNAME = new QName("http://tempuri.org/", "strIdUsuario");
    private final static QName _ObtenerNombreUsuarioStrPIdUsuario_QNAME = new QName("http://tempuri.org/", "str_pIdUsuario");
    private final static QName _VerificaUsuarioObtienePermisosWithoutIPHostNameStrPContrasenia_QNAME = new QName("http://tempuri.org/", "str_pContrasenia");
    private final static QName _VerificaUsuarioObtienePermisosWithoutIPHostNameStrPUsuario_QNAME = new QName("http://tempuri.org/", "str_pUsuario");
    private final static QName _ObjetoBETipoObjeto_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "TipoObjeto");
    private final static QName _ObjetoBENombreObjetoPadre_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "NombreObjetoPadre");
    private final static QName _ObjetoBEAuditoriaModificacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "AuditoriaModificacion");
    private final static QName _ObjetoBEAuditoriaCreacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "AuditoriaCreacion");
    private final static QName _ObjetoBEDescripcionAplicacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "DescripcionAplicacion");
    private final static QName _UsuarioBEPais_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Pais");
    private final static QName _UsuarioBEProvincia_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Provincia");
    private final static QName _UsuarioBEDireccion_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Direccion");
    private final static QName _UsuarioBEApellidoPaterno_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "ApellidoPaterno");
    private final static QName _UsuarioBETelefono_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Telefono");
    private final static QName _UsuarioBEEmailInterno_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "EmailInterno");
    private final static QName _UsuarioBENombres_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Nombres");
    private final static QName _UsuarioBEObservacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Observacion");
    private final static QName _UsuarioBECodigoCadena2_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "CodigoCadena2");
    private final static QName _UsuarioBECodigoCadena1_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "CodigoCadena1");
    private final static QName _UsuarioBENombreJefe_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "NombreJefe");
    private final static QName _UsuarioBEUnidadOrganizacional_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "UnidadOrganizacional");
    private final static QName _UsuarioBEFechaIngreso_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "FechaIngreso");
    private final static QName _UsuarioBEApellidoMaterno_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "ApellidoMaterno");
    private final static QName _UsuarioBEFechaSincronizacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "FechaSincronizacion");
    private final static QName _UsuarioBEFax_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Fax");
    private final static QName _UsuarioBETelefonoMovil_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "TelefonoMovil");
    private final static QName _UsuarioBEFechaBaja_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "FechaBaja");
    private final static QName _UsuarioBEOficinaCompania_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "OficinaCompania");
    private final static QName _UsuarioBEIdUsuario_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "IdUsuario");
    private final static QName _UsuarioBEIdUsuarioJefe_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "IdUsuarioJefe");
    private final static QName _UsuarioBECargoFormal_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "CargoFormal");
    private final static QName _UsuarioBECompania_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Compania");
    private final static QName _UsuarioBEDepartamento_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Departamento");
    private final static QName _UsuarioBENombreCompleto_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "NombreCompleto");
    private final static QName _UsuarioBEIdTrabajador_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "IdTrabajador");
    private final static QName _ObtenerRolesUsuarioAplicacionResponseObtenerRolesUsuarioAplicacionResult_QNAME = new QName("http://tempuri.org/", "ObtenerRolesUsuarioAplicacionResult");
    private final static QName _ResultadoValidacionBEPwdLastChange_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "PwdLastChange");
    private final static QName _ResultadoValidacionBEMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Message");
    private final static QName _AgregarLogLogonStrPHostName_QNAME = new QName("http://tempuri.org/", "str_pHostName");
    private final static QName _AgregarLogLogonStrPIP_QNAME = new QName("http://tempuri.org/", "str_pIP");
    private final static QName _ObtenerUsuarioResponseObtenerUsuarioResult_QNAME = new QName("http://tempuri.org/", "ObtenerUsuarioResult");
    private final static QName _ObtenerAccesosAppResponseObtenerAccesosAppResult_QNAME = new QName("http://tempuri.org/", "ObtenerAccesosAppResult");
    private final static QName _VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResponseVerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResult_QNAME = new QName("http://tempuri.org/", "VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResult");
    private final static QName _RolBEDescripcionRol_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "DescripcionRol");
    private final static QName _RolBENombreRol_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "NombreRol");
    private final static QName _RolBECodigoRol_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "CodigoRol");
    private final static QName _AccesosBEObjetos_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Objetos");
    private final static QName _AccesosBEAcciones_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Acciones");
    private final static QName _ObtenerNombreUsuarioResponseObtenerNombreUsuarioResult_QNAME = new QName("http://tempuri.org/", "ObtenerNombreUsuarioResult");
    private final static QName _ValidarUsuarioAppResponseValidarUsuarioAppResult_QNAME = new QName("http://tempuri.org/", "ValidarUsuarioAppResult");
    private final static QName _VerificaUsuarioObtienePermisosWithoutIPHostNameResponseVerificaUsuarioObtienePermisosWithoutIPHostNameResult_QNAME = new QName("http://tempuri.org/", "VerificaUsuarioObtienePermisosWithoutIPHostNameResult");
    private final static QName _PermisosLBEError_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Error");
    private final static QName _PermisosLBEUsuario_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Usuario");
    private final static QName _ErrorBEMensaje_QNAME = new QName("http://schemas.datacontract.org/2004/07/SegCen.BE", "Mensaje");
    private final static QName _VerificaUsuarioObtienePermisosResponseVerificaUsuarioObtienePermisosResult_QNAME = new QName("http://tempuri.org/", "VerificaUsuarioObtienePermisosResult");
    private final static QName _ObtenerUsuarioStrpIdUsuario_QNAME = new QName("http://tempuri.org/", "strpIdUsuario");
    private final static QName _AgregarLogCambiosStrPValorAnterior_QNAME = new QName("http://tempuri.org/", "str_pValorAnterior");
    private final static QName _AgregarLogCambiosStrPNombreEntidad_QNAME = new QName("http://tempuri.org/", "str_pNombreEntidad");
    private final static QName _AgregarLogCambiosStrPValorNuevo_QNAME = new QName("http://tempuri.org/", "str_pValorNuevo");
    private final static QName _AgregarLogCambiosStrPCampo_QNAME = new QName("http://tempuri.org/", "str_pCampo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ms.encuestas.models.segcen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AgregarLogCambiosResponse }
     * 
     */
    public AgregarLogCambiosResponse createAgregarLogCambiosResponse() {
        return new AgregarLogCambiosResponse();
    }

    /**
     * Create an instance of {@link ObtenerUsuario }
     * 
     */
    public ObtenerUsuario createObtenerUsuario() {
        return new ObtenerUsuario();
    }

    /**
     * Create an instance of {@link RegistrarLogLogoutResponse }
     * 
     */
    public RegistrarLogLogoutResponse createRegistrarLogLogoutResponse() {
        return new RegistrarLogLogoutResponse();
    }

    /**
     * Create an instance of {@link VerificaUsuarioObtienePermisosWithoutIPHostName }
     * 
     */
    public VerificaUsuarioObtienePermisosWithoutIPHostName createVerificaUsuarioObtienePermisosWithoutIPHostName() {
        return new VerificaUsuarioObtienePermisosWithoutIPHostName();
    }

    /**
     * Create an instance of {@link ObtenerAccesosAppResponse }
     * 
     */
    public ObtenerAccesosAppResponse createObtenerAccesosAppResponse() {
        return new ObtenerAccesosAppResponse();
    }

    /**
     * Create an instance of {@link AccesosBE }
     * 
     */
    public AccesosBE createAccesosBE() {
        return new AccesosBE();
    }

    /**
     * Create an instance of {@link ValidarEstadoCuenta }
     * 
     */
    public ValidarEstadoCuenta createValidarEstadoCuenta() {
        return new ValidarEstadoCuenta();
    }

    /**
     * Create an instance of {@link ValidarEstadoCuentaResponse }
     * 
     */
    public ValidarEstadoCuentaResponse createValidarEstadoCuentaResponse() {
        return new ValidarEstadoCuentaResponse();
    }

    /**
     * Create an instance of {@link ResultadoValidacionBE }
     * 
     */
    public ResultadoValidacionBE createResultadoValidacionBE() {
        return new ResultadoValidacionBE();
    }

    /**
     * Create an instance of {@link ObtenerNombreUsuarioResponse }
     * 
     */
    public ObtenerNombreUsuarioResponse createObtenerNombreUsuarioResponse() {
        return new ObtenerNombreUsuarioResponse();
    }

    /**
     * Create an instance of {@link VerificaUsuarioObtienePermisos }
     * 
     */
    public VerificaUsuarioObtienePermisos createVerificaUsuarioObtienePermisos() {
        return new VerificaUsuarioObtienePermisos();
    }

    /**
     * Create an instance of {@link VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResponse }
     * 
     */
    public VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResponse createVerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResponse() {
        return new VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResponse();
    }

    /**
     * Create an instance of {@link PermisosLBE }
     * 
     */
    public PermisosLBE createPermisosLBE() {
        return new PermisosLBE();
    }

    /**
     * Create an instance of {@link ObtenerRolesUsuarioAplicacion }
     * 
     */
    public ObtenerRolesUsuarioAplicacion createObtenerRolesUsuarioAplicacion() {
        return new ObtenerRolesUsuarioAplicacion();
    }

    /**
     * Create an instance of {@link RegistrarLogLogout }
     * 
     */
    public RegistrarLogLogout createRegistrarLogLogout() {
        return new RegistrarLogLogout();
    }

    /**
     * Create an instance of {@link ObtenerAccesosApp }
     * 
     */
    public ObtenerAccesosApp createObtenerAccesosApp() {
        return new ObtenerAccesosApp();
    }

    /**
     * Create an instance of {@link AgregarLogCambios }
     * 
     */
    public AgregarLogCambios createAgregarLogCambios() {
        return new AgregarLogCambios();
    }

    /**
     * Create an instance of {@link AgregarLogLogon }
     * 
     */
    public AgregarLogLogon createAgregarLogLogon() {
        return new AgregarLogLogon();
    }

    /**
     * Create an instance of {@link VerificaUsuarioObtienePermisosResponse }
     * 
     */
    public VerificaUsuarioObtienePermisosResponse createVerificaUsuarioObtienePermisosResponse() {
        return new VerificaUsuarioObtienePermisosResponse();
    }

    /**
     * Create an instance of {@link VerificarExistenciaUsuarioResponse }
     * 
     */
    public VerificarExistenciaUsuarioResponse createVerificarExistenciaUsuarioResponse() {
        return new VerificarExistenciaUsuarioResponse();
    }

    /**
     * Create an instance of {@link VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostName }
     * 
     */
    public VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostName createVerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostName() {
        return new VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostName();
    }

    /**
     * Create an instance of {@link VerificaUsuarioObtienePermisosWithoutIPHostNameResponse }
     * 
     */
    public VerificaUsuarioObtienePermisosWithoutIPHostNameResponse createVerificaUsuarioObtienePermisosWithoutIPHostNameResponse() {
        return new VerificaUsuarioObtienePermisosWithoutIPHostNameResponse();
    }

    /**
     * Create an instance of {@link ObtenerRolesUsuarioAplicacionResponse }
     * 
     */
    public ObtenerRolesUsuarioAplicacionResponse createObtenerRolesUsuarioAplicacionResponse() {
        return new ObtenerRolesUsuarioAplicacionResponse();
    }

    /**
     * Create an instance of {@link ArrayOfRolBE }
     * 
     */
    public ArrayOfRolBE createArrayOfRolBE() {
        return new ArrayOfRolBE();
    }

    /**
     * Create an instance of {@link ObtenerUsuarioResponse }
     * 
     */
    public ObtenerUsuarioResponse createObtenerUsuarioResponse() {
        return new ObtenerUsuarioResponse();
    }

    /**
     * Create an instance of {@link UsuarioBE }
     * 
     */
    public UsuarioBE createUsuarioBE() {
        return new UsuarioBE();
    }

    /**
     * Create an instance of {@link ValidarUsuarioAppResponse }
     * 
     */
    public ValidarUsuarioAppResponse createValidarUsuarioAppResponse() {
        return new ValidarUsuarioAppResponse();
    }

    /**
     * Create an instance of {@link ObtenerNombreUsuario }
     * 
     */
    public ObtenerNombreUsuario createObtenerNombreUsuario() {
        return new ObtenerNombreUsuario();
    }

    /**
     * Create an instance of {@link AgregarLogLogonResponse }
     * 
     */
    public AgregarLogLogonResponse createAgregarLogLogonResponse() {
        return new AgregarLogLogonResponse();
    }

    /**
     * Create an instance of {@link ValidarUsuarioApp }
     * 
     */
    public ValidarUsuarioApp createValidarUsuarioApp() {
        return new ValidarUsuarioApp();
    }

    /**
     * Create an instance of {@link VerificarExistenciaUsuario }
     * 
     */
    public VerificarExistenciaUsuario createVerificarExistenciaUsuario() {
        return new VerificarExistenciaUsuario();
    }

    /**
     * Create an instance of {@link ArrayOfAccionBE }
     * 
     */
    public ArrayOfAccionBE createArrayOfAccionBE() {
        return new ArrayOfAccionBE();
    }

    /**
     * Create an instance of {@link ArrayOfObjetoBE }
     * 
     */
    public ArrayOfObjetoBE createArrayOfObjetoBE() {
        return new ArrayOfObjetoBE();
    }

    /**
     * Create an instance of {@link RolBE }
     * 
     */
    public RolBE createRolBE() {
        return new RolBE();
    }

    /**
     * Create an instance of {@link AccionBE }
     * 
     */
    public AccionBE createAccionBE() {
        return new AccionBE();
    }

    /**
     * Create an instance of {@link ErrorBE }
     * 
     */
    public ErrorBE createErrorBE() {
        return new ErrorBE();
    }

    /**
     * Create an instance of {@link ObjetoBE }
     * 
     */
    public ObjetoBE createObjetoBE() {
        return new ObjetoBE();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRolBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "ArrayOfRolBE")
    public JAXBElement<ArrayOfRolBE> createArrayOfRolBE(ArrayOfRolBE value) {
        return new JAXBElement<ArrayOfRolBE>(_ArrayOfRolBE_QNAME, ArrayOfRolBE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsuarioBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "UsuarioBE")
    public JAXBElement<UsuarioBE> createUsuarioBE(UsuarioBE value) {
        return new JAXBElement<UsuarioBE>(_UsuarioBE_QNAME, UsuarioBE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RolBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "RolBE")
    public JAXBElement<RolBE> createRolBE(RolBE value) {
        return new JAXBElement<RolBE>(_RolBE_QNAME, RolBE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "ErrorBE")
    public JAXBElement<ErrorBE> createErrorBE(ErrorBE value) {
        return new JAXBElement<ErrorBE>(_ErrorBE_QNAME, ErrorBE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoValidacionBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "ResultadoValidacionBE")
    public JAXBElement<ResultadoValidacionBE> createResultadoValidacionBE(ResultadoValidacionBE value) {
        return new JAXBElement<ResultadoValidacionBE>(_ResultadoValidacionBE_QNAME, ResultadoValidacionBE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermisosLBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "PermisosLBE")
    public JAXBElement<PermisosLBE> createPermisosLBE(PermisosLBE value) {
        return new JAXBElement<PermisosLBE>(_PermisosLBE_QNAME, PermisosLBE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAccionBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "ArrayOfAccionBE")
    public JAXBElement<ArrayOfAccionBE> createArrayOfAccionBE(ArrayOfAccionBE value) {
        return new JAXBElement<ArrayOfAccionBE>(_ArrayOfAccionBE_QNAME, ArrayOfAccionBE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfObjetoBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "ArrayOfObjetoBE")
    public JAXBElement<ArrayOfObjetoBE> createArrayOfObjetoBE(ArrayOfObjetoBE value) {
        return new JAXBElement<ArrayOfObjetoBE>(_ArrayOfObjetoBE_QNAME, ArrayOfObjetoBE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccionBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "AccionBE")
    public JAXBElement<AccionBE> createAccionBE(AccionBE value) {
        return new JAXBElement<AccionBE>(_AccionBE_QNAME, AccionBE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjetoBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "ObjetoBE")
    public JAXBElement<ObjetoBE> createObjetoBE(ObjetoBE value) {
        return new JAXBElement<ObjetoBE>(_ObjetoBE_QNAME, ObjetoBE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccesosBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "AccesosBE")
    public JAXBElement<AccesosBE> createAccesosBE(AccesosBE value) {
        return new JAXBElement<AccesosBE>(_AccesosBE_QNAME, AccesosBE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoValidacionBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ValidarEstadoCuentaResult", scope = ValidarEstadoCuentaResponse.class)
    public JAXBElement<ResultadoValidacionBE> createValidarEstadoCuentaResponseValidarEstadoCuentaResult(ResultadoValidacionBE value) {
        return new JAXBElement<ResultadoValidacionBE>(_ValidarEstadoCuentaResponseValidarEstadoCuentaResult_QNAME, ResultadoValidacionBE.class, ValidarEstadoCuentaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "codigoAplicacion", scope = ValidarEstadoCuenta.class)
    public JAXBElement<String> createValidarEstadoCuentaCodigoAplicacion(String value) {
        return new JAXBElement<String>(_ValidarEstadoCuentaCodigoAplicacion_QNAME, String.class, ValidarEstadoCuenta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "idUsuario", scope = ValidarEstadoCuenta.class)
    public JAXBElement<String> createValidarEstadoCuentaIdUsuario(String value) {
        return new JAXBElement<String>(_ValidarEstadoCuentaIdUsuario_QNAME, String.class, ValidarEstadoCuenta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "CodigoObjeto", scope = AccionBE.class)
    public JAXBElement<String> createAccionBECodigoObjeto(String value) {
        return new JAXBElement<String>(_AccionBECodigoObjeto_QNAME, String.class, AccionBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "NombreAccion", scope = AccionBE.class)
    public JAXBElement<String> createAccionBENombreAccion(String value) {
        return new JAXBElement<String>(_AccionBENombreAccion_QNAME, String.class, AccionBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "CodigoAccion", scope = AccionBE.class)
    public JAXBElement<String> createAccionBECodigoAccion(String value) {
        return new JAXBElement<String>(_AccionBECodigoAccion_QNAME, String.class, AccionBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "NombreObjeto", scope = AccionBE.class)
    public JAXBElement<String> createAccionBENombreObjeto(String value) {
        return new JAXBElement<String>(_AccionBENombreObjeto_QNAME, String.class, AccionBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pCodigoAplicacion", scope = ObtenerRolesUsuarioAplicacion.class)
    public JAXBElement<String> createObtenerRolesUsuarioAplicacionStrPCodigoAplicacion(String value) {
        return new JAXBElement<String>(_ObtenerRolesUsuarioAplicacionStrPCodigoAplicacion_QNAME, String.class, ObtenerRolesUsuarioAplicacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "strIdUsuario", scope = ObtenerRolesUsuarioAplicacion.class)
    public JAXBElement<String> createObtenerRolesUsuarioAplicacionStrIdUsuario(String value) {
        return new JAXBElement<String>(_ObtenerRolesUsuarioAplicacionStrIdUsuario_QNAME, String.class, ObtenerRolesUsuarioAplicacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pIdUsuario", scope = ObtenerNombreUsuario.class)
    public JAXBElement<String> createObtenerNombreUsuarioStrPIdUsuario(String value) {
        return new JAXBElement<String>(_ObtenerNombreUsuarioStrPIdUsuario_QNAME, String.class, ObtenerNombreUsuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pCodigoAplicacion", scope = VerificaUsuarioObtienePermisosWithoutIPHostName.class)
    public JAXBElement<String> createVerificaUsuarioObtienePermisosWithoutIPHostNameStrPCodigoAplicacion(String value) {
        return new JAXBElement<String>(_ObtenerRolesUsuarioAplicacionStrPCodigoAplicacion_QNAME, String.class, VerificaUsuarioObtienePermisosWithoutIPHostName.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pContrasenia", scope = VerificaUsuarioObtienePermisosWithoutIPHostName.class)
    public JAXBElement<String> createVerificaUsuarioObtienePermisosWithoutIPHostNameStrPContrasenia(String value) {
        return new JAXBElement<String>(_VerificaUsuarioObtienePermisosWithoutIPHostNameStrPContrasenia_QNAME, String.class, VerificaUsuarioObtienePermisosWithoutIPHostName.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pUsuario", scope = VerificaUsuarioObtienePermisosWithoutIPHostName.class)
    public JAXBElement<String> createVerificaUsuarioObtienePermisosWithoutIPHostNameStrPUsuario(String value) {
        return new JAXBElement<String>(_VerificaUsuarioObtienePermisosWithoutIPHostNameStrPUsuario_QNAME, String.class, VerificaUsuarioObtienePermisosWithoutIPHostName.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "TipoObjeto", scope = ObjetoBE.class)
    public JAXBElement<String> createObjetoBETipoObjeto(String value) {
        return new JAXBElement<String>(_ObjetoBETipoObjeto_QNAME, String.class, ObjetoBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "CodigoObjeto", scope = ObjetoBE.class)
    public JAXBElement<String> createObjetoBECodigoObjeto(String value) {
        return new JAXBElement<String>(_AccionBECodigoObjeto_QNAME, String.class, ObjetoBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "NombreObjetoPadre", scope = ObjetoBE.class)
    public JAXBElement<String> createObjetoBENombreObjetoPadre(String value) {
        return new JAXBElement<String>(_ObjetoBENombreObjetoPadre_QNAME, String.class, ObjetoBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "AuditoriaModificacion", scope = ObjetoBE.class)
    public JAXBElement<String> createObjetoBEAuditoriaModificacion(String value) {
        return new JAXBElement<String>(_ObjetoBEAuditoriaModificacion_QNAME, String.class, ObjetoBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "AuditoriaCreacion", scope = ObjetoBE.class)
    public JAXBElement<String> createObjetoBEAuditoriaCreacion(String value) {
        return new JAXBElement<String>(_ObjetoBEAuditoriaCreacion_QNAME, String.class, ObjetoBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "DescripcionAplicacion", scope = ObjetoBE.class)
    public JAXBElement<String> createObjetoBEDescripcionAplicacion(String value) {
        return new JAXBElement<String>(_ObjetoBEDescripcionAplicacion_QNAME, String.class, ObjetoBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "NombreObjeto", scope = ObjetoBE.class)
    public JAXBElement<String> createObjetoBENombreObjeto(String value) {
        return new JAXBElement<String>(_AccionBENombreObjeto_QNAME, String.class, ObjetoBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Pais", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEPais(String value) {
        return new JAXBElement<String>(_UsuarioBEPais_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Provincia", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEProvincia(String value) {
        return new JAXBElement<String>(_UsuarioBEProvincia_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Direccion", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEDireccion(String value) {
        return new JAXBElement<String>(_UsuarioBEDireccion_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "ApellidoPaterno", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEApellidoPaterno(String value) {
        return new JAXBElement<String>(_UsuarioBEApellidoPaterno_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Telefono", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBETelefono(String value) {
        return new JAXBElement<String>(_UsuarioBETelefono_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "EmailInterno", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEEmailInterno(String value) {
        return new JAXBElement<String>(_UsuarioBEEmailInterno_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Nombres", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBENombres(String value) {
        return new JAXBElement<String>(_UsuarioBENombres_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "AuditoriaModificacion", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEAuditoriaModificacion(String value) {
        return new JAXBElement<String>(_ObjetoBEAuditoriaModificacion_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Observacion", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEObservacion(String value) {
        return new JAXBElement<String>(_UsuarioBEObservacion_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "CodigoCadena2", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBECodigoCadena2(String value) {
        return new JAXBElement<String>(_UsuarioBECodigoCadena2_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "CodigoCadena1", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBECodigoCadena1(String value) {
        return new JAXBElement<String>(_UsuarioBECodigoCadena1_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "NombreJefe", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBENombreJefe(String value) {
        return new JAXBElement<String>(_UsuarioBENombreJefe_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "UnidadOrganizacional", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEUnidadOrganizacional(String value) {
        return new JAXBElement<String>(_UsuarioBEUnidadOrganizacional_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "FechaIngreso", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEFechaIngreso(String value) {
        return new JAXBElement<String>(_UsuarioBEFechaIngreso_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "ApellidoMaterno", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEApellidoMaterno(String value) {
        return new JAXBElement<String>(_UsuarioBEApellidoMaterno_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "FechaSincronizacion", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEFechaSincronizacion(String value) {
        return new JAXBElement<String>(_UsuarioBEFechaSincronizacion_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Fax", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEFax(String value) {
        return new JAXBElement<String>(_UsuarioBEFax_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "TelefonoMovil", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBETelefonoMovil(String value) {
        return new JAXBElement<String>(_UsuarioBETelefonoMovil_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "FechaBaja", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEFechaBaja(String value) {
        return new JAXBElement<String>(_UsuarioBEFechaBaja_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "OficinaCompania", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEOficinaCompania(String value) {
        return new JAXBElement<String>(_UsuarioBEOficinaCompania_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "IdUsuario", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEIdUsuario(String value) {
        return new JAXBElement<String>(_UsuarioBEIdUsuario_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "IdUsuarioJefe", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEIdUsuarioJefe(String value) {
        return new JAXBElement<String>(_UsuarioBEIdUsuarioJefe_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "CargoFormal", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBECargoFormal(String value) {
        return new JAXBElement<String>(_UsuarioBECargoFormal_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "AuditoriaCreacion", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEAuditoriaCreacion(String value) {
        return new JAXBElement<String>(_ObjetoBEAuditoriaCreacion_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Compania", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBECompania(String value) {
        return new JAXBElement<String>(_UsuarioBECompania_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Departamento", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEDepartamento(String value) {
        return new JAXBElement<String>(_UsuarioBEDepartamento_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "NombreCompleto", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBENombreCompleto(String value) {
        return new JAXBElement<String>(_UsuarioBENombreCompleto_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "IdTrabajador", scope = UsuarioBE.class)
    public JAXBElement<String> createUsuarioBEIdTrabajador(String value) {
        return new JAXBElement<String>(_UsuarioBEIdTrabajador_QNAME, String.class, UsuarioBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRolBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ObtenerRolesUsuarioAplicacionResult", scope = ObtenerRolesUsuarioAplicacionResponse.class)
    public JAXBElement<ArrayOfRolBE> createObtenerRolesUsuarioAplicacionResponseObtenerRolesUsuarioAplicacionResult(ArrayOfRolBE value) {
        return new JAXBElement<ArrayOfRolBE>(_ObtenerRolesUsuarioAplicacionResponseObtenerRolesUsuarioAplicacionResult_QNAME, ArrayOfRolBE.class, ObtenerRolesUsuarioAplicacionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "PwdLastChange", scope = ResultadoValidacionBE.class)
    public JAXBElement<String> createResultadoValidacionBEPwdLastChange(String value) {
        return new JAXBElement<String>(_ResultadoValidacionBEPwdLastChange_QNAME, String.class, ResultadoValidacionBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Message", scope = ResultadoValidacionBE.class)
    public JAXBElement<String> createResultadoValidacionBEMessage(String value) {
        return new JAXBElement<String>(_ResultadoValidacionBEMessage_QNAME, String.class, ResultadoValidacionBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pIdUsuario", scope = AgregarLogLogon.class)
    public JAXBElement<String> createAgregarLogLogonStrPIdUsuario(String value) {
        return new JAXBElement<String>(_ObtenerNombreUsuarioStrPIdUsuario_QNAME, String.class, AgregarLogLogon.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pCodigoAplicacion", scope = AgregarLogLogon.class)
    public JAXBElement<String> createAgregarLogLogonStrPCodigoAplicacion(String value) {
        return new JAXBElement<String>(_ObtenerRolesUsuarioAplicacionStrPCodigoAplicacion_QNAME, String.class, AgregarLogLogon.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pHostName", scope = AgregarLogLogon.class)
    public JAXBElement<String> createAgregarLogLogonStrPHostName(String value) {
        return new JAXBElement<String>(_AgregarLogLogonStrPHostName_QNAME, String.class, AgregarLogLogon.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pIP", scope = AgregarLogLogon.class)
    public JAXBElement<String> createAgregarLogLogonStrPIP(String value) {
        return new JAXBElement<String>(_AgregarLogLogonStrPIP_QNAME, String.class, AgregarLogLogon.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsuarioBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ObtenerUsuarioResult", scope = ObtenerUsuarioResponse.class)
    public JAXBElement<UsuarioBE> createObtenerUsuarioResponseObtenerUsuarioResult(UsuarioBE value) {
        return new JAXBElement<UsuarioBE>(_ObtenerUsuarioResponseObtenerUsuarioResult_QNAME, UsuarioBE.class, ObtenerUsuarioResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pIdUsuario", scope = ObtenerAccesosApp.class)
    public JAXBElement<String> createObtenerAccesosAppStrPIdUsuario(String value) {
        return new JAXBElement<String>(_ObtenerNombreUsuarioStrPIdUsuario_QNAME, String.class, ObtenerAccesosApp.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pCodigoAplicacion", scope = ObtenerAccesosApp.class)
    public JAXBElement<String> createObtenerAccesosAppStrPCodigoAplicacion(String value) {
        return new JAXBElement<String>(_ObtenerRolesUsuarioAplicacionStrPCodigoAplicacion_QNAME, String.class, ObtenerAccesosApp.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccesosBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ObtenerAccesosAppResult", scope = ObtenerAccesosAppResponse.class)
    public JAXBElement<AccesosBE> createObtenerAccesosAppResponseObtenerAccesosAppResult(AccesosBE value) {
        return new JAXBElement<AccesosBE>(_ObtenerAccesosAppResponseObtenerAccesosAppResult_QNAME, AccesosBE.class, ObtenerAccesosAppResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pIdUsuario", scope = VerificarExistenciaUsuario.class)
    public JAXBElement<String> createVerificarExistenciaUsuarioStrPIdUsuario(String value) {
        return new JAXBElement<String>(_ObtenerNombreUsuarioStrPIdUsuario_QNAME, String.class, VerificarExistenciaUsuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermisosLBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResult", scope = VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResponse.class)
    public JAXBElement<PermisosLBE> createVerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResponseVerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResult(PermisosLBE value) {
        return new JAXBElement<PermisosLBE>(_VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResponseVerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResult_QNAME, PermisosLBE.class, VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pCodigoAplicacion", scope = ValidarUsuarioApp.class)
    public JAXBElement<String> createValidarUsuarioAppStrPCodigoAplicacion(String value) {
        return new JAXBElement<String>(_ObtenerRolesUsuarioAplicacionStrPCodigoAplicacion_QNAME, String.class, ValidarUsuarioApp.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pContrasenia", scope = ValidarUsuarioApp.class)
    public JAXBElement<String> createValidarUsuarioAppStrPContrasenia(String value) {
        return new JAXBElement<String>(_VerificaUsuarioObtienePermisosWithoutIPHostNameStrPContrasenia_QNAME, String.class, ValidarUsuarioApp.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pUsuario", scope = ValidarUsuarioApp.class)
    public JAXBElement<String> createValidarUsuarioAppStrPUsuario(String value) {
        return new JAXBElement<String>(_VerificaUsuarioObtienePermisosWithoutIPHostNameStrPUsuario_QNAME, String.class, ValidarUsuarioApp.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pHostName", scope = ValidarUsuarioApp.class)
    public JAXBElement<String> createValidarUsuarioAppStrPHostName(String value) {
        return new JAXBElement<String>(_AgregarLogLogonStrPHostName_QNAME, String.class, ValidarUsuarioApp.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pIP", scope = ValidarUsuarioApp.class)
    public JAXBElement<String> createValidarUsuarioAppStrPIP(String value) {
        return new JAXBElement<String>(_AgregarLogLogonStrPIP_QNAME, String.class, ValidarUsuarioApp.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "DescripcionRol", scope = RolBE.class)
    public JAXBElement<String> createRolBEDescripcionRol(String value) {
        return new JAXBElement<String>(_RolBEDescripcionRol_QNAME, String.class, RolBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "UnidadOrganizacional", scope = RolBE.class)
    public JAXBElement<String> createRolBEUnidadOrganizacional(String value) {
        return new JAXBElement<String>(_UsuarioBEUnidadOrganizacional_QNAME, String.class, RolBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "AuditoriaModificacion", scope = RolBE.class)
    public JAXBElement<String> createRolBEAuditoriaModificacion(String value) {
        return new JAXBElement<String>(_ObjetoBEAuditoriaModificacion_QNAME, String.class, RolBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "NombreRol", scope = RolBE.class)
    public JAXBElement<String> createRolBENombreRol(String value) {
        return new JAXBElement<String>(_RolBENombreRol_QNAME, String.class, RolBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "AuditoriaCreacion", scope = RolBE.class)
    public JAXBElement<String> createRolBEAuditoriaCreacion(String value) {
        return new JAXBElement<String>(_ObjetoBEAuditoriaCreacion_QNAME, String.class, RolBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "CodigoRol", scope = RolBE.class)
    public JAXBElement<String> createRolBECodigoRol(String value) {
        return new JAXBElement<String>(_RolBECodigoRol_QNAME, String.class, RolBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfObjetoBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Objetos", scope = AccesosBE.class)
    public JAXBElement<ArrayOfObjetoBE> createAccesosBEObjetos(ArrayOfObjetoBE value) {
        return new JAXBElement<ArrayOfObjetoBE>(_AccesosBEObjetos_QNAME, ArrayOfObjetoBE.class, AccesosBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAccionBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Acciones", scope = AccesosBE.class)
    public JAXBElement<ArrayOfAccionBE> createAccesosBEAcciones(ArrayOfAccionBE value) {
        return new JAXBElement<ArrayOfAccionBE>(_AccesosBEAcciones_QNAME, ArrayOfAccionBE.class, AccesosBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ObtenerNombreUsuarioResult", scope = ObtenerNombreUsuarioResponse.class)
    public JAXBElement<String> createObtenerNombreUsuarioResponseObtenerNombreUsuarioResult(String value) {
        return new JAXBElement<String>(_ObtenerNombreUsuarioResponseObtenerNombreUsuarioResult_QNAME, String.class, ObtenerNombreUsuarioResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ValidarUsuarioAppResult", scope = ValidarUsuarioAppResponse.class)
    public JAXBElement<String> createValidarUsuarioAppResponseValidarUsuarioAppResult(String value) {
        return new JAXBElement<String>(_ValidarUsuarioAppResponseValidarUsuarioAppResult_QNAME, String.class, ValidarUsuarioAppResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pCodigoAplicacion", scope = VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostName.class)
    public JAXBElement<String> createVerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameStrPCodigoAplicacion(String value) {
        return new JAXBElement<String>(_ObtenerRolesUsuarioAplicacionStrPCodigoAplicacion_QNAME, String.class, VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostName.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pContrasenia", scope = VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostName.class)
    public JAXBElement<String> createVerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameStrPContrasenia(String value) {
        return new JAXBElement<String>(_VerificaUsuarioObtienePermisosWithoutIPHostNameStrPContrasenia_QNAME, String.class, VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostName.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pUsuario", scope = VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostName.class)
    public JAXBElement<String> createVerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostNameStrPUsuario(String value) {
        return new JAXBElement<String>(_VerificaUsuarioObtienePermisosWithoutIPHostNameStrPUsuario_QNAME, String.class, VerificaUsuarioObtienePermisosWithoutSoloPermitidasIPHostName.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermisosLBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "VerificaUsuarioObtienePermisosWithoutIPHostNameResult", scope = VerificaUsuarioObtienePermisosWithoutIPHostNameResponse.class)
    public JAXBElement<PermisosLBE> createVerificaUsuarioObtienePermisosWithoutIPHostNameResponseVerificaUsuarioObtienePermisosWithoutIPHostNameResult(PermisosLBE value) {
        return new JAXBElement<PermisosLBE>(_VerificaUsuarioObtienePermisosWithoutIPHostNameResponseVerificaUsuarioObtienePermisosWithoutIPHostNameResult_QNAME, PermisosLBE.class, VerificaUsuarioObtienePermisosWithoutIPHostNameResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Error", scope = PermisosLBE.class)
    public JAXBElement<ErrorBE> createPermisosLBEError(ErrorBE value) {
        return new JAXBElement<ErrorBE>(_PermisosLBEError_QNAME, ErrorBE.class, PermisosLBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAccionBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Acciones", scope = PermisosLBE.class)
    public JAXBElement<ArrayOfAccionBE> createPermisosLBEAcciones(ArrayOfAccionBE value) {
        return new JAXBElement<ArrayOfAccionBE>(_AccesosBEAcciones_QNAME, ArrayOfAccionBE.class, PermisosLBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsuarioBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Usuario", scope = PermisosLBE.class)
    public JAXBElement<UsuarioBE> createPermisosLBEUsuario(UsuarioBE value) {
        return new JAXBElement<UsuarioBE>(_PermisosLBEUsuario_QNAME, UsuarioBE.class, PermisosLBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", name = "Mensaje", scope = ErrorBE.class)
    public JAXBElement<String> createErrorBEMensaje(String value) {
        return new JAXBElement<String>(_ErrorBEMensaje_QNAME, String.class, ErrorBE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pCodigoAplicacion", scope = VerificaUsuarioObtienePermisos.class)
    public JAXBElement<String> createVerificaUsuarioObtienePermisosStrPCodigoAplicacion(String value) {
        return new JAXBElement<String>(_ObtenerRolesUsuarioAplicacionStrPCodigoAplicacion_QNAME, String.class, VerificaUsuarioObtienePermisos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pContrasenia", scope = VerificaUsuarioObtienePermisos.class)
    public JAXBElement<String> createVerificaUsuarioObtienePermisosStrPContrasenia(String value) {
        return new JAXBElement<String>(_VerificaUsuarioObtienePermisosWithoutIPHostNameStrPContrasenia_QNAME, String.class, VerificaUsuarioObtienePermisos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pUsuario", scope = VerificaUsuarioObtienePermisos.class)
    public JAXBElement<String> createVerificaUsuarioObtienePermisosStrPUsuario(String value) {
        return new JAXBElement<String>(_VerificaUsuarioObtienePermisosWithoutIPHostNameStrPUsuario_QNAME, String.class, VerificaUsuarioObtienePermisos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pHostName", scope = VerificaUsuarioObtienePermisos.class)
    public JAXBElement<String> createVerificaUsuarioObtienePermisosStrPHostName(String value) {
        return new JAXBElement<String>(_AgregarLogLogonStrPHostName_QNAME, String.class, VerificaUsuarioObtienePermisos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pIP", scope = VerificaUsuarioObtienePermisos.class)
    public JAXBElement<String> createVerificaUsuarioObtienePermisosStrPIP(String value) {
        return new JAXBElement<String>(_AgregarLogLogonStrPIP_QNAME, String.class, VerificaUsuarioObtienePermisos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermisosLBE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "VerificaUsuarioObtienePermisosResult", scope = VerificaUsuarioObtienePermisosResponse.class)
    public JAXBElement<PermisosLBE> createVerificaUsuarioObtienePermisosResponseVerificaUsuarioObtienePermisosResult(PermisosLBE value) {
        return new JAXBElement<PermisosLBE>(_VerificaUsuarioObtienePermisosResponseVerificaUsuarioObtienePermisosResult_QNAME, PermisosLBE.class, VerificaUsuarioObtienePermisosResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "strpIdUsuario", scope = ObtenerUsuario.class)
    public JAXBElement<String> createObtenerUsuarioStrpIdUsuario(String value) {
        return new JAXBElement<String>(_ObtenerUsuarioStrpIdUsuario_QNAME, String.class, ObtenerUsuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pIdUsuario", scope = AgregarLogCambios.class)
    public JAXBElement<String> createAgregarLogCambiosStrPIdUsuario(String value) {
        return new JAXBElement<String>(_ObtenerNombreUsuarioStrPIdUsuario_QNAME, String.class, AgregarLogCambios.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pCodigoAplicacion", scope = AgregarLogCambios.class)
    public JAXBElement<String> createAgregarLogCambiosStrPCodigoAplicacion(String value) {
        return new JAXBElement<String>(_ObtenerRolesUsuarioAplicacionStrPCodigoAplicacion_QNAME, String.class, AgregarLogCambios.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pValorAnterior", scope = AgregarLogCambios.class)
    public JAXBElement<String> createAgregarLogCambiosStrPValorAnterior(String value) {
        return new JAXBElement<String>(_AgregarLogCambiosStrPValorAnterior_QNAME, String.class, AgregarLogCambios.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pHostName", scope = AgregarLogCambios.class)
    public JAXBElement<String> createAgregarLogCambiosStrPHostName(String value) {
        return new JAXBElement<String>(_AgregarLogLogonStrPHostName_QNAME, String.class, AgregarLogCambios.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pIP", scope = AgregarLogCambios.class)
    public JAXBElement<String> createAgregarLogCambiosStrPIP(String value) {
        return new JAXBElement<String>(_AgregarLogLogonStrPIP_QNAME, String.class, AgregarLogCambios.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pNombreEntidad", scope = AgregarLogCambios.class)
    public JAXBElement<String> createAgregarLogCambiosStrPNombreEntidad(String value) {
        return new JAXBElement<String>(_AgregarLogCambiosStrPNombreEntidad_QNAME, String.class, AgregarLogCambios.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pValorNuevo", scope = AgregarLogCambios.class)
    public JAXBElement<String> createAgregarLogCambiosStrPValorNuevo(String value) {
        return new JAXBElement<String>(_AgregarLogCambiosStrPValorNuevo_QNAME, String.class, AgregarLogCambios.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "str_pCampo", scope = AgregarLogCambios.class)
    public JAXBElement<String> createAgregarLogCambiosStrPCampo(String value) {
        return new JAXBElement<String>(_AgregarLogCambiosStrPCampo_QNAME, String.class, AgregarLogCambios.class, value);
    }

}
