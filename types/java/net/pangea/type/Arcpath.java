
package net.pangea.type;

import java.math.BigDecimal;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:xs="http://www.w3.org/2001/XMLSchema" name="arcpath">
 *   &lt;xs:complexType>
 *     &lt;xs:attribute type="xs:decimal" name="y"/>
 *     &lt;xs:attribute type="xs:decimal" name="x"/>
 *     &lt;xs:attribute type="xs:string" use="required" name="id"/>
 *     &lt;xs:attribute type="xs:boolean" use="required" name="curvePoint"/>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Arcpath
{
    private BigDecimal y;
    private BigDecimal x;
    private String id;
    private boolean curvePoint;

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

    /** 
     * Get the 'id' attribute value.
     * 
     * @return value
     */
    public String getId() {
        return id;
    }

    /** 
     * Set the 'id' attribute value.
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /** 
     * Get the 'curvePoint' attribute value.
     * 
     * @return value
     */
    public boolean getCurvePoint() {
        return curvePoint;
    }

    /** 
     * Set the 'curvePoint' attribute value.
     * 
     * @param curvePoint
     */
    public void setCurvePoint(boolean curvePoint) {
        this.curvePoint = curvePoint;
    }
}
