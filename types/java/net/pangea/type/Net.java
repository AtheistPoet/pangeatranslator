
package net.pangea.type;

import java.util.ArrayList;
import java.util.List;
import net.pangea.type.Arc;
import net.pangea.type.Labels;
import net.pangea.type.Place;
import net.pangea.type.Transition;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.pangea.net/type" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="net">
 *   &lt;xs:complexType>
 *     &lt;xs:sequence>
 *       &lt;xs:element ref="ns:labels" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;xs:element ref="ns:place" maxOccurs="unbounded"/>
 *       &lt;xs:element ref="ns:transition" maxOccurs="unbounded"/>
 *       &lt;xs:element ref="ns:arc" maxOccurs="unbounded"/>
 *     &lt;/xs:sequence>
 *     &lt;xs:attribute type="xs:string" use="required" name="type"/>
 *     &lt;xs:attribute type="xs:string" use="required" name="id"/>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Net
{
    private List<Labels> labelList = new ArrayList<Labels>();
    private List<Place> placeList = new ArrayList<Place>();
    private List<Transition> transitionList = new ArrayList<Transition>();
    private List<Arc> arcList = new ArrayList<Arc>();
    private String type;
    private String id;

    /** 
     * Get the list of 'labels' element items.
     * 
     * @return list
     */
    public List<Labels> getLabels() {
        return labelList;
    }

    /** 
     * Set the list of 'labels' element items.
     * 
     * @param list
     */
    public void setLabels(List<Labels> list) {
        labelList = list;
    }

    /** 
     * Get the list of 'place' element items.
     * 
     * @return list
     */
    public List<Place> getPlaces() {
        return placeList;
    }

    /** 
     * Set the list of 'place' element items.
     * 
     * @param list
     */
    public void setPlaces(List<Place> list) {
        placeList = list;
    }

    /** 
     * Get the list of 'transition' element items.
     * 
     * @return list
     */
    public List<Transition> getTransitions() {
        return transitionList;
    }

    /** 
     * Set the list of 'transition' element items.
     * 
     * @param list
     */
    public void setTransitions(List<Transition> list) {
        transitionList = list;
    }

    /** 
     * Get the list of 'arc' element items.
     * 
     * @return list
     */
    public List<Arc> getArcs() {
        return arcList;
    }

    /** 
     * Set the list of 'arc' element items.
     * 
     * @param list
     */
    public void setArcs(List<Arc> list) {
        arcList = list;
    }

    /** 
     * Get the 'type' attribute value.
     * 
     * @return value
     */
    public String getType() {
        return type;
    }

    /** 
     * Set the 'type' attribute value.
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
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
