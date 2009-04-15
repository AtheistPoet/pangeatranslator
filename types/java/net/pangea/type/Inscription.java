
package net.pangea.type;

import net.pangea.type.Graphics;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.pangea.net/type" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="inscription">
 *   &lt;xs:complexType>
 *     &lt;xs:sequence>
 *       &lt;xs:element type="xs:string" name="value"/>
 *       &lt;xs:element ref="ns:graphics" minOccurs="0"/>
 *     &lt;/xs:sequence>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Inscription
{
    private String value;
    private Graphics graphics;

    /** 
     * Get the 'value' element value.
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }

    /** 
     * Set the 'value' element value.
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /** 
     * Get the 'graphics' element value.
     * 
     * @return value
     */
    public Graphics getGraphics() {
        return graphics;
    }

    /** 
     * Set the 'graphics' element value.
     * 
     * @param graphics
     */
    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }
}
