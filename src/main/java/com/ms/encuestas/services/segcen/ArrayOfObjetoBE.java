
package com.ms.encuestas.services.segcen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfObjetoBE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfObjetoBE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ObjetoBE" type="{http://schemas.datacontract.org/2004/07/SegCen.BE}ObjetoBE" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfObjetoBE", namespace = "http://schemas.datacontract.org/2004/07/SegCen.BE", propOrder = {
    "objetoBE"
})
public class ArrayOfObjetoBE {

    @XmlElement(name = "ObjetoBE", nillable = true)
    protected List<ObjetoBE> objetoBE;

    /**
     * Gets the value of the objetoBE property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objetoBE property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjetoBE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjetoBE }
     * 
     * 
     */
    public List<ObjetoBE> getObjetoBE() {
        if (objetoBE == null) {
            objetoBE = new ArrayList<ObjetoBE>();
        }
        return this.objetoBE;
    }

}
