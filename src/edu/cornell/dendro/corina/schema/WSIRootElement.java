//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-793 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.27 at 11:05:46 AM PDT 
//


package edu.cornell.dendro.corina.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * <p>Java class for corina element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="corina">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;element ref="{http://dendro.cornell.edu/schema/corina/1.0}request"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element ref="{http://dendro.cornell.edu/schema/corina/1.0}header"/>
 *             &lt;choice>
 *               &lt;element ref="{http://dendro.cornell.edu/schema/corina/1.0}content" minOccurs="0"/>
 *               &lt;element ref="{http://dendro.cornell.edu/schema/corina/1.0}help" minOccurs="0"/>
 *             &lt;/choice>
 *           &lt;/sequence>
 *         &lt;/choice>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "request",
    "header",
    "content",
    "help"
})
@XmlRootElement(name = "corina")
public class WSIRootElement {

    protected WSIRequest request;
    protected WSIHeader header;
    protected WSIContent content;
    protected WSIHelp help;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link WSIRequest }
     *     
     */
    public WSIRequest getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSIRequest }
     *     
     */
    public void setRequest(WSIRequest value) {
        this.request = value;
    }

    public boolean isSetRequest() {
        return (this.request!= null);
    }

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link WSIHeader }
     *     
     */
    public WSIHeader getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSIHeader }
     *     
     */
    public void setHeader(WSIHeader value) {
        this.header = value;
    }

    public boolean isSetHeader() {
        return (this.header!= null);
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link WSIContent }
     *     
     */
    public WSIContent getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSIContent }
     *     
     */
    public void setContent(WSIContent value) {
        this.content = value;
    }

    public boolean isSetContent() {
        return (this.content!= null);
    }

    /**
     * Gets the value of the help property.
     * 
     * @return
     *     possible object is
     *     {@link WSIHelp }
     *     
     */
    public WSIHelp getHelp() {
        return help;
    }

    /**
     * Sets the value of the help property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSIHelp }
     *     
     */
    public void setHelp(WSIHelp value) {
        this.help = value;
    }

    public boolean isSetHelp() {
        return (this.help!= null);
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
