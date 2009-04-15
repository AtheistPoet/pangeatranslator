
package net.pangea.type;

import net.pangea.type.Graphics;
import net.pangea.type.InfiniteServer;
import net.pangea.type.Name;
import net.pangea.type.Orientation;
import net.pangea.type.Priority;
import net.pangea.type.Rate;
import net.pangea.type.Timed;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.pangea.net/type" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="transition">
 *   &lt;xs:complexType>
 *     &lt;xs:sequence>
 *       &lt;xs:element ref="ns:graphics" minOccurs="0"/>
 *       &lt;xs:element ref="ns:name"/>
 *       &lt;xs:element ref="ns:orientation" minOccurs="0"/>
 *       &lt;xs:element ref="ns:rate" minOccurs="0"/>
 *       &lt;xs:element ref="ns:timed" minOccurs="0"/>
 *       &lt;xs:element ref="ns:infiniteServer" minOccurs="0"/>
 *       &lt;xs:element ref="ns:priority" minOccurs="0"/>
 *     &lt;/xs:sequence>
 *     &lt;xs:attribute type="xs:string" use="required" name="id"/>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Transition
{
    private Graphics graphics;
    private Name name;
    private Orientation orientation;
    private Rate rate;
    private Timed timed;
    private InfiniteServer infiniteServer;
    private Priority priority;
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
     * Get the 'orientation' element value.
     * 
     * @return value
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /** 
     * Set the 'orientation' element value.
     * 
     * @param orientation
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /** 
     * Get the 'rate' element value.
     * 
     * @return value
     */
    public Rate getRate() {
        return rate;
    }

    /** 
     * Set the 'rate' element value.
     * 
     * @param rate
     */
    public void setRate(Rate rate) {
        this.rate = rate;
    }

    /** 
     * Get the 'timed' element value.
     * 
     * @return value
     */
    public Timed getTimed() {
        return timed;
    }

    /** 
     * Set the 'timed' element value.
     * 
     * @param timed
     */
    public void setTimed(Timed timed) {
        this.timed = timed;
    }

    /** 
     * Get the 'infiniteServer' element value.
     * 
     * @return value
     */
    public InfiniteServer getInfiniteServer() {
        return infiniteServer;
    }

    /** 
     * Set the 'infiniteServer' element value.
     * 
     * @param infiniteServer
     */
    public void setInfiniteServer(InfiniteServer infiniteServer) {
        this.infiniteServer = infiniteServer;
    }

    /** 
     * Get the 'priority' element value.
     * 
     * @return value
     */
    public Priority getPriority() {
        return priority;
    }

    /** 
     * Set the 'priority' element value.
     * 
     * @param priority
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
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
