
package net.pangea.type;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:xs="http://www.w3.org/2001/XMLSchema" name="type">
 *   &lt;xs:complexType>
 *     &lt;xs:attribute use="required" name="value">
 *       &lt;xs:simpleType>
 *         &lt;!-- Reference to inner class Value -->
 *       &lt;/xs:simpleType>
 *     &lt;/xs:attribute>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Type
{
    private Value value;

    /** 
     * Get the 'value' attribute value.
     * 
     * @return value
     */
    public Value getValue() {
        return value;
    }

    /** 
     * Set the 'value' attribute value.
     * 
     * @param value
     */
    public void setValue(Value value) {
        this.value = value;
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:simpleType xmlns:xs="http://www.w3.org/2001/XMLSchema">
     *   &lt;xs:restriction base="xs:string">
     *     &lt;xs:enumeration value="inhibitor"/>
     *     &lt;xs:enumeration value="normal"/>
     *   &lt;/xs:restriction>
     * &lt;/xs:simpleType>
     * </pre>
     */
    public static enum Value {
        INHIBITOR("inhibitor"), NORMAL("normal");
        private final String value;

        private Value(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }

        public static Value convert(String value) {
            for (Value inst : values()) {
                if (inst.toString().equals(value)) {
                    return inst;
                }
            }
            return null;
        }
    }
}
