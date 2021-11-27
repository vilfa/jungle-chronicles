package si.vilfa.junglechronicles.Input.Events;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Events
 **/
public class TouchDraggedInputEvent extends InputEvent
{
    public TouchDraggedInputEvent(int screenX, int screenY, int pointer)
    {
        super();
        this.screenX = screenX;
        this.screenY = screenY;
        this.pointer = pointer;
    }

    public int getScreenX()
    {
        return screenX;
    }

    public int getScreenY()
    {
        return screenY;
    }

    public int getPointer()
    {
        return pointer;
    }

    @Override
    public InputEventType getInputEventType()
    {
        return InputEventType.TOUCH_DRAGGED;
    }
}
