package si.vilfa.junglechronicles.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import si.vilfa.junglechronicles.Input.Devices.IMultitouchScreen;
import si.vilfa.junglechronicles.Input.Devices.IOnScreenKeyboard;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Input
 **/
public class MobileInputProcessor implements IMultitouchScreen, IOnScreenKeyboard, InputProcessor
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
	public boolean hasMultitouchScreen()
	{
		return this.input.isPeripheralAvailable(Input.Peripheral.MultitouchScreen);
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
}
