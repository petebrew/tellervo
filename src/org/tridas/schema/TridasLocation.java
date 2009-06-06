//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-793 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.04 at 07:43:30 PM PDT 
//


package org.tridas.schema;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


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
 *         &lt;element ref="{http://www.tridas.org/1.2}locationGeometry"/>
 *         &lt;element ref="{http://www.tridas.org/1.2}locationType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tridas.org/1.2}locationPrecision" minOccurs="0"/>
 *         &lt;element ref="{http://www.tridas.org/1.2}locationComment" minOccurs="0"/>
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
    "locationGeometry",
    "locationType",
    "locationPrecision",
    "locationComment"
})
@XmlRootElement(name = "location")
public class TridasLocation
    implements Serializable
{

    @XmlElement(required = true)
    protected TridasLocationGeometry locationGeometry;
    protected NormalTridasLocationType locationType;
    protected String locationPrecision;
    protected String locationComment;

    /**
     * Gets the value of the locationGeometry property.
     * 
     * @return
     *     possible object is
     *     {@link TridasLocationGeometry }
     *     
     */
    public TridasLocationGeometry getLocationGeometry() {
        return locationGeometry;
    }

    /**
     * Sets the value of the locationGeometry property.
     * 
     * @param value
     *     allowed object is
     *     {@link TridasLocationGeometry }
     *     
     */
    public void setLocationGeometry(TridasLocationGeometry value) {
        this.locationGeometry = value;
    }

    public boolean isSetLocationGeometry() {
        return (this.locationGeometry!= null);
    }

    /**
     * Gets the value of the locationType property.
     * 
     * @return
     *     possible object is
     *     {@link NormalTridasLocationType }
     *     
     */
    public NormalTridasLocationType getLocationType() {
        return locationType;
    }

    /**
     * Sets the value of the locationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link NormalTridasLocationType }
     *     
     */
    public void setLocationType(NormalTridasLocationType value) {
        this.locationType = value;
    }

    public boolean isSetLocationType() {
        return (this.locationType!= null);
    }

    /**
     * Gets the value of the locationPrecision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationPrecision() {
        return locationPrecision;
    }

    /**
     * Sets the value of the locationPrecision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationPrecision(String value) {
        this.locationPrecision = value;
    }

    public boolean isSetLocationPrecision() {
        return (this.locationPrecision!= null);
    }

    /**
     * Gets the value of the locationComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationComment() {
        return locationComment;
    }

    /**
     * Sets the value of the locationComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationComment(String value) {
        this.locationComment = value;
    }

    public boolean isSetLocationComment() {
        return (this.locationComment!= null);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
