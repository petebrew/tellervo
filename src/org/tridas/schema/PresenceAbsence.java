//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-793 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.04 at 07:43:30 PM PDT 
//


package org.tridas.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for presenceAbsence.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="presenceAbsence">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="present"/>
 *     &lt;enumeration value="absent"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "presenceAbsence")
@XmlEnum
public enum PresenceAbsence {

    @XmlEnumValue("present")
    PRESENT("present"),
    @XmlEnumValue("absent")
    ABSENT("absent");
    private final String value;

    PresenceAbsence(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PresenceAbsence fromValue(String v) {
        for (PresenceAbsence c: PresenceAbsence.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
