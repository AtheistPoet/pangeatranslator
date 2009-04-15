
package net.pangea.type;

import net.pangea.type.Offset;
import net.pangea.type.Position;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.pangea.net/type" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="graphics">
 *   &lt;xs:complexType>
 *     &lt;xs:choice minOccurs="0">
 *       &lt;!-- Reference to inner class GraphicsInner -->
 *     &lt;/xs:choice>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Graphics
{
    private GraphicsInner graphics;

    /** 
     * Get the 'graphics' element value.
     * 
     * @return value
     */
    public GraphicsInner getGraphics() {
        return graphics;
    }

    /** 
     * Set the 'graphics' element value.
     * 
     * @param graphics
     */
    public void setGraphics(GraphicsInner graphics) {
        this.graphics = graphics;
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:choice xmlns:ns="http://www.pangea.net/type" xmlns:xs="http://www.w3.org/2001/XMLSchema" minOccurs="0">
     *   &lt;xs:element ref="ns:offset"/>
     *   &lt;xs:element ref="ns:position"/>
     * &lt;/xs:choice>
     * </pre>
     */
    public static class GraphicsInner
    {
        private int graphicsSelect = -1;
        private static final int OFFSET_CHOICE = 0;
        private static final int POSITION_CHOICE = 1;
        private Offset offset;
        private Position position;

        private void setGraphicsSelect(int choice) {
            if (graphicsSelect == -1) {
                graphicsSelect = choice;
            } else if (graphicsSelect != choice) {
                throw new IllegalStateException(
                        "Need to call clearGraphicsSelect() before changing existing choice");
            }
        }

        /** 
         * Clear the choice selection.
         */
        public void clearGraphicsSelect() {
            graphicsSelect = -1;
        }

        /** 
         * Check if Offset is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifOffset() {
            return graphicsSelect == OFFSET_CHOICE;
        }

        /** 
         * Get the 'offset' element value.
         * 
         * @return value
         */
        public Offset getOffset() {
            return offset;
        }

        /** 
         * Set the 'offset' element value.
         * 
         * @param offset
         */
        public void setOffset(Offset offset) {
            setGraphicsSelect(OFFSET_CHOICE);
            this.offset = offset;
        }

        /** 
         * Check if Position is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifPosition() {
            return graphicsSelect == POSITION_CHOICE;
        }

        /** 
         * Get the 'position' element value.
         * 
         * @return value
         */
        public Position getPosition() {
            return position;
        }

        /** 
         * Set the 'position' element value.
         * 
         * @param position
         */
        public void setPosition(Position position) {
            setGraphicsSelect(POSITION_CHOICE);
            this.position = position;
        }
    }
}
