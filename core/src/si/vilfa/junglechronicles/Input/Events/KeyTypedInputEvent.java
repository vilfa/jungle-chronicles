package si.vilfa.junglechronicles.Input.Events;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Events
 **/
public class KeyTypedInputEvent extends InputEvent
{
	public KeyTypedInputEvent(char character)
	{
		super();
		this.character = character;
	}

	public char getCharacter()
	{
		return character;
	}

	@Override
	public InputEventType getInputEventType()
	{
		return InputEventType.KEY_TYPED;
	}
}
