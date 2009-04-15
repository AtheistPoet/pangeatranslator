
package net.pangea.type;

import java.math.BigDecimal;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:xs="http://www.w3.org/2001/XMLSchema" name="labels">
 *   &lt;xs:complexType>
 *     &lt;xs:sequence>
 *       &lt;xs:element type="xs:string" name="text"/>
 *     &lt;/xs:sequence>
 *     &lt;xs:attribute type="xs:decimal" name="y"/>
 *     &lt;xs:attribute type="xs:decimal" name="x"/>
 *     &lt;xs:attribute type="xs:decimal" use="required" name="width"/>
 *     &lt;xs:attribute type="xs:decimal" use="required" name="height"/>
 *     &lt;xs:attribute type="xs:boolean" use="required" name="border"/>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Labels
{
    private String text;
    private BigDecimal y;
    private BigDecimal x;
    private BigDecimal width;
    private BigDecimal height;
    private boolean border;

    /** 
     * Get the 'text' element value.
     * 
     * @return value
     */
    public String getText() {
        return text;
    }

    /** 
     * Set the 'text' element value.
     * 
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

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
     * Get the 'width' attribute value.
     * 
     * @return value
     */
    public BigDecimal getWidth() {
        return width;
    }

    /** 
     * Set the 'width' attribute value.
     * 
     * @param width
     */
    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    /** 
     * Get the 'height' attribute value.
     * 
     * @return value
     */
    public BigDecimal getHeight() {
        return height;
    }

    /** 
     * Set the 'height' attribute value.
     * 
     * @param height
     */
    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    /** 
     * Get the 'border' attribute value.
     * 
     * @return value
     */
    public boolean getBorder() {
        return border;
    }

    /** 
     * Set the 'border' attribute value.
     * 
     * @param border
     */
    public void setBorder(boolean border) {
        this.border = border;
    }
}
