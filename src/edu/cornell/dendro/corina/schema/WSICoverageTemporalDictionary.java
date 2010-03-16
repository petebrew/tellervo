
package edu.cornell.dendro.corina.schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jvnet.jaxb2_commons.lang.CopyTo;
import org.jvnet.jaxb2_commons.lang.Copyable;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.builder.CopyBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBCopyBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBEqualsBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBHashCodeBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBToStringBuilder;
import org.tridas.schema.ControlledVoc;


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
 *         &lt;element name="coverageTemporal" type="{http://www.tridas.org/1.2.1}controlledVoc" maxOccurs="unbounded" minOccurs="0"/>
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
    "coverageTemporals"
})
@XmlRootElement(name = "coverageTemporalDictionary")
public class WSICoverageTemporalDictionary implements Serializable, CopyTo, Copyable, Equals, HashCode, ToString
{

    private final static long serialVersionUID = 1001L;
    @XmlElement(name = "coverageTemporal")
    protected List<ControlledVoc> coverageTemporals;

    /**
     * Gets the value of the coverageTemporals property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coverageTemporals property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCoverageTemporals().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ControlledVoc }
     * 
     * 
     */
    public List<ControlledVoc> getCoverageTemporals() {
        if (coverageTemporals == null) {
            coverageTemporals = new ArrayList<ControlledVoc>();
        }
        return this.coverageTemporals;
    }

    public boolean isSetCoverageTemporals() {
        return ((this.coverageTemporals!= null)&&(!this.coverageTemporals.isEmpty()));
    }

    public void unsetCoverageTemporals() {
        this.coverageTemporals = null;
    }

    /**
     * Sets the value of the coverageTemporals property.
     * 
     * @param coverageTemporals
     *     allowed object is
     *     {@link ControlledVoc }
     *     
     */
    public void setCoverageTemporals(List<ControlledVoc> coverageTemporals) {
        this.coverageTemporals = coverageTemporals;
    }

    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof WSICoverageTemporalDictionary)) {
            equalsBuilder.appendSuper(false);
            return ;
        }
        if (this == object) {
            return ;
        }
        final WSICoverageTemporalDictionary that = ((WSICoverageTemporalDictionary) object);
        equalsBuilder.append(this.getCoverageTemporals(), that.getCoverageTemporals());
    }

    public boolean equals(Object object) {
        if (!(object instanceof WSICoverageTemporalDictionary)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final EqualsBuilder equalsBuilder = new JAXBEqualsBuilder();
        equals(object, equalsBuilder);
        return equalsBuilder.isEquals();
    }

    public void hashCode(HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder.append(this.getCoverageTemporals());
    }

    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

    public void toString(ToStringBuilder toStringBuilder) {
        {
            List<ControlledVoc> theCoverageTemporals;
            theCoverageTemporals = this.getCoverageTemporals();
            toStringBuilder.append("coverageTemporals", theCoverageTemporals);
        }
    }

    public String toString() {
        final ToStringBuilder toStringBuilder = new JAXBToStringBuilder(this);
        toString(toStringBuilder);
        return toStringBuilder.toString();
    }

    public Object copyTo(Object target, CopyBuilder copyBuilder) {
        final WSICoverageTemporalDictionary copy = ((target == null)?((WSICoverageTemporalDictionary) createCopy()):((WSICoverageTemporalDictionary) target));
        if (this.isSetCoverageTemporals()) {
            List<ControlledVoc> sourceCoverageTemporals;
            sourceCoverageTemporals = this.getCoverageTemporals();
            @SuppressWarnings("unchecked")
            List<ControlledVoc> copyCoverageTemporals = ((List<ControlledVoc> ) copyBuilder.copy(sourceCoverageTemporals));
            copy.setCoverageTemporals(copyCoverageTemporals);
        } else {
            copy.unsetCoverageTemporals();
        }
        return copy;
    }

    public Object copyTo(Object target) {
        final CopyBuilder copyBuilder = new JAXBCopyBuilder();
        return copyTo(target, copyBuilder);
    }

    public Object createCopy() {
        return new WSICoverageTemporalDictionary();
    }

}