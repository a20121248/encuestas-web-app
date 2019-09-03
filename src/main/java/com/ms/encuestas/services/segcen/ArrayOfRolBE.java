
package com.ms.encuestas.services.segcen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRolBE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRolBE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RolBE" type="{http://schemas.datacontract.org/2004/07/SegCen.BE}RolBE" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRolBE", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", propOrder = {
    "rolBE"
})
public class ArrayOfRolBE {

    @XmlElement(name = "RolBE", nillable = true)
    protected List<RolBE> rolBE;

    /**
     * Gets the value of the rolBE property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rolBE property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRolBE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RolBE }
     * 
     * 
     */
    public List<RolBE> getRolBE() {
        if (rolBE == null) {
            rolBE = new ArrayList<RolBE>();
        }
        return this.rolBE;
    }

}
