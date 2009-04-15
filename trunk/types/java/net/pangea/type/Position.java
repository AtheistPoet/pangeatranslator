
package net.pangea.type;

import java.math.BigDecimal;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:xs="http://www.w3.org/2001/XMLSchema" name="position">
 *   &lt;xs:complexType>
 *     &lt;xs:attribute type="xs:decimal" name="y"/>
 *     &lt;xs:attribute type="xs:decimal" name="x"/>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Position
{
    private BigDecimal y;
    private BigDecimal x;

    /** 
     * Get the 'y' attribute value.
     * 
     * @return value
     */
    public BigDecimal getY() {
        return y;
    }

    /** 
     * Set the 'y' attribute value.
     * 
     * @param y
     */
    public void setY(BigDecimal y) {
        this.y = y;
    }

    /** 
     * Get the 'x' attribute value.
     * 
     * @return value
     */
    public BigDecimal getX() {
        return x;
    }

    /** 
     * Set the 'x' attribute value.
     * 
     * @param x
     */
    public void setX(BigDecimal x) {
        this.x = x;
    }
}
