package si.vilfa.junglechronicles.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import si.vilfa.junglechronicles.Input.Devices.IDeviceInput;
import si.vilfa.junglechronicles.Input.Events.IInputEventReceiver;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Input
 **/
public abstract class TargetInputProcessor<T extends IInputEventReceiver> implements IDeviceInput, IInputTarget<T>, InputProcessor
{
	protected final Input input = Gdx.input;
	protected final T target;

	public TargetInputProcessor(T target)
	{
		this.target = target;
	}

	@Override
	public abstract boolean keyDown(int keycode);

	@Override
	public abstract boolean keyUp(int keycode);

	@Override
	public abstract boolean keyTyped(char character);

	@Override
	public abstract boolean touchDown(int screenX, int screenY, int pointer, int button);

	@Override
	public abstract boolean touchUp(int screenX, int screenY, int pointer, int button);

	@Override
	public abstract boolean touchDragged(int screenX, int screenY, int pointer);

	@Override
	public abstract boolean mouseMoved(int screenX, int screenY);

	@Override
	public abstract boolean scrolled(float amountX, float amountY);

	@Override
	public abstract T getTarget();

	@Override
	public boolean hasHardwareKeyboard()
	{
		return this.input.isPeripheralAvailable(Input.Peripheral.HardwareKeyboard);
	}

	@Override
	public boolean hasOnScreenKeyboard()
	{
		return this.input.isPeripheralAvailable(Input.Peripheral.OnscreenKeyboard);
	}

	@Override
	public void showOnScreenKeyboard()
	{
		this.input.setOnscreenKeyboardVisible(true);
	}

	@Override
	public void hideOnScreenKeyboard()
	{
		this.input.setOnscreenKeyboardVisible(false);
	}

	@Override
	public boolean hasMouse()
	{
		return this.input.isPeripheralAvailable(Input.Peripheral.MultitouchScreen);
	}

	@Override
	public boolean hasMultitouchScreen()
	{
		return this.input.isPeripheralAvailable(Input.Peripheral.MultitouchScreen);
	}
}
