
package Domain;

/**
 * A pattern peg is of one color and can be visible or not
 *
 * @author Samuel Gomez
 */
public class PatternPeg {

    private Integer color;
    private Boolean visible;

    public PatternPeg(Integer _color, Boolean _visible)
    {
        color = _color;
        visible = _visible;
    }


    
    /*
     * 
     * color
     * 
     */
     
    public Integer getColor()
    {
        return color;
    }

    public void setColor(Integer _color)
    {
        color = _color;
    }



    /*
     *
     * visible
     *
     */

    public Boolean getVisible()
    {
        return visible;
    }

    public void setVisible(Boolean _visible)
    {
        visible = _visible;
    }
}
