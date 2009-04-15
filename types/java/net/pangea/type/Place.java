
package net.pangea.type;

import net.pangea.type.Capacity;
import net.pangea.type.Graphics;
import net.pangea.type.InitialMarking;
import net.pangea.type.Name;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.pangea.net/type" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="place">
 *   &lt;xs:complexType>
 *     &lt;xs:sequence>
 *       &lt;xs:element ref="ns:graphics" minOccurs="0"/>
 *       &lt;xs:element ref="ns:name"/>
 *       &lt;xs:element ref="ns:initialMarking" minOccurs="0"/>
 *       &lt;xs:element ref="ns:capacity" minOccurs="0"/>
 *     &lt;/xs:sequence>
 *     &lt;xs:attribute type="xs:string" use="required" name="id"/>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Place
{
    private Graphics graphics;
    private Name name;
    private InitialMarking initialMarking;
    private Capacity capacity;
    private String id;

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

    /** 
     * Get the 'name' element value.
     * 
     * @return value
     */
    public Name getName() {
        return name;
    }

    /** 
     * Set the 'name' element value.
     * 
     * @param name
     */
    public void setName(Name name) {
        this.name = name;
    }

    /** 
     * Get the 'initialMarking' element value.
     * 
     * @return value
     */
    public InitialMarking getInitialMarking() {
        return initialMarking;
    }

    /** 
     * Set the 'initialMarking' element value.
     * 
     * @param initialMarking
     */
    public void setInitialMarking(InitialMarking initialMarking) {
        this.initialMarking = initialMarking;
    }

    /** 
     * Get the 'capacity' element value.
     * 
     * @return value
     */
    public Capacity getCapacity() {
        return capacity;
    }

    /** 
     * Set the 'capacity' element value.
     * 
     * @param capacity
     */
    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
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
}
