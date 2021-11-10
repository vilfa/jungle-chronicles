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
		this.screenX = screenX;
		this.screenY = screenY;
		this.pointer = pointer;
	}

	public int getScreenX()
	{
		return this.screenX;
	}

	public int getScreenY()
	{
		return this.screenY;
	}

	public int getPointer()
	{
		return this.pointer;
	}

	@Override
	public InputEventType getInputEventType()
	{
		return InputEventType.TOUCH_DRAGGED;
	}
}
