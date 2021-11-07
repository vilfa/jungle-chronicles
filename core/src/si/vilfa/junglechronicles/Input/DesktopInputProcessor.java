package si.vilfa.junglechronicles.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Input
 **/
public class DesktopInputProcessor implements IDesktopInputProcessor, InputProcessor
{
	protected final Input input = Gdx.input;

	@Override
	public boolean keyDown(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY)
	{
		return false;
	}

	@Override
	public boolean hasHardwareKeyboard()
	{
		return this.input.isPeripheralAvailable(Input.Peripheral.HardwareKeyboard);
	}

	@Override
	public boolean hasMouse()
	{
		return this.input.isPeripheralAvailable(Input.Peripheral.MultitouchScreen);
	}
}
