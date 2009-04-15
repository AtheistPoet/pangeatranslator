
package net.pangea.type;

import java.util.ArrayList;
import java.util.List;
import net.pangea.type.Arcpath;
import net.pangea.type.Graphics;
import net.pangea.type.Inscription;
import net.pangea.type.Tagged;
import net.pangea.type.Type;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.pangea.net/type" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="arc">
 *   &lt;xs:complexType>
 *     &lt;xs:sequence>
 *       &lt;xs:element ref="ns:graphics" minOccurs="0"/>
 *       &lt;xs:element ref="ns:inscription" minOccurs="0"/>
 *       &lt;xs:element ref="ns:tagged" minOccurs="0"/>
 *       &lt;xs:element ref="ns:arcpath" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;xs:element ref="ns:type"/>
 *     &lt;/xs:sequence>
 *     &lt;xs:attribute type="xs:string" use="required" name="target"/>
 *     &lt;xs:attribute type="xs:string" use="required" name="source"/>
 *     &lt;xs:attribute type="xs:string" use="required" name="id"/>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Arc
{
    private Graphics graphics;
    private Inscription inscription;
    private Tagged tagged;
    private List<Arcpath> arcpathList = new ArrayList<Arcpath>();
    private Type type;
    private String target;
    private String source;
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
     * Get the 'inscription' element value.
     * 
     * @return value
     */
    public Inscription getInscription() {
        return inscription;
    }

    /** 
     * Set the 'inscription' element value.
     * 
     * @param inscription
     */
    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    /** 
     * Get the 'tagged' element value.
     * 
     * @return value
     */
    public Tagged getTagged() {
        return tagged;
    }

    /** 
     * Set the 'tagged' element value.
     * 
     * @param tagged
     */
    public void setTagged(Tagged tagged) {
        this.tagged = tagged;
    }

    /** 
     * Get the list of 'arcpath' element items.
     * 
     * @return list
     */
    public List<Arcpath> getArcpaths() {
        return arcpathList;
    }

    /** 
     * Set the list of 'arcpath' element items.
     * 
     * @param list
     */
    public void setArcpaths(List<Arcpath> list) {
        arcpathList = list;
    }

    /** 
     * Get the 'type' element value.
     * 
     * @return value
     */
    public Type getType() {
        return type;
    }

    /** 
     * Set the 'type' element value.
     * 
     * @param type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /** 
     * Get the 'target' attribute value.
     * 
     * @return value
     */
    public String getTarget() {
        return target;
    }

    /** 
     * Set the 'target' attribute value.
     * 
     * @param target
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /** 
     * Get the 'source' attribute value.
     * 
     * @return value
     */
    public String getSource() {
        return source;
    }

    /** 
     * Set the 'source' attribute value.
     * 
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
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
