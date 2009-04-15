
package net.pangea.type;

import net.pangea.type.Net;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.pangea.net/type" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="pnml">
 *   &lt;xs:complexType>
 *     &lt;xs:sequence>
 *       &lt;xs:element ref="ns:net"/>
 *     &lt;/xs:sequence>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Pnml
{
    private Net pnml;

    /** 
     * Get the 'net' element value.
     * 
     * @return value
     */
    public Net getPnml() {
        return pnml;
    }

    /** 
     * Set the 'net' element value.
     * 
     * @param pnml
     */
    public void setPnml(Net pnml) {
        this.pnml = pnml;
    }
}
