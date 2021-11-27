package si.vilfa.junglechronicles.Input.Events;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Events
 **/
public abstract class InputEvent implements InputEventProvider
{
    protected int keycode;
    protected char character;
    protected int screenX;
    protected int screenY;
    protected int pointer;
    protected int button;
    protected float amountX;
    protected float amountY;

    @Override
    public abstract InputEventType getInputEventType();

    @Override
    public String toString()
    {
        return "InputEvent{" + "type=" + getInputEventType() + ", keycode=" + keycode
               + ", character=" + character + ", screenX=" + screenX + ", screenY=" + screenY
               + ", pointer=" + pointer + ", button=" + button + ", amountX=" + amountX
               + ", amountY=" + amountY + '}';
    }
}
