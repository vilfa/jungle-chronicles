package si.vilfa.junglechronicles.Input.Events;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Events
 **/
public class MouseMovedInputEvent extends InputEvent
{
	public MouseMovedInputEvent(int screenX, int screenY)
	{
		this.screenX = screenX;
		this.screenY = screenY;
	}

	public int getScreenX()
	{
		return this.screenX;
	}

	public int getScreenY()
	{
		return this.screenY;
	}

	@Override
	public InputEventType getInputEventType()
	{
		return InputEventType.MOUSE_MOVED;
	}
}
