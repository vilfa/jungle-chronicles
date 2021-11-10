package si.vilfa.junglechronicles.Input.Processors;

import si.vilfa.junglechronicles.Input.Events.*;
import si.vilfa.junglechronicles.Input.TargetInputProcessor;
import si.vilfa.junglechronicles.Player.Human.Player;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Processors
 **/
public class PlayerInputProcessor extends TargetInputProcessor<Player>
{
	public PlayerInputProcessor(Player target)
	{
		super(target);
	}

	@Override
	public boolean keyDown(int keycode)
	{
		this.target.handleKeyDown(new KeyDownInputEvent(keycode));
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		this.target.handleKeyUp(new KeyUpInputEvent(keycode));
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		this.target.handleKeyTyped(new KeyTypedInputEvent(character));
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		this.target.handleTouchDown(new TouchDownInputEvent(screenX, screenY, pointer, button));
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		this.target.handleTouchUp(new TouchUpInputEvent(screenX, screenY, pointer, button));
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		this.target.handleTouchDragged(new TouchDraggedInputEvent(screenX, screenY, pointer));
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		this.target.handleMouseMoved(new MouseMovedInputEvent(screenX, screenY));
		return true;
	}

	@Override
	public boolean scrolled(float amountX, float amountY)
	{
		this.target.handleScrolled(new ScrolledInputEvent(amountX, amountY));
		return true;
	}

	@Override
	public Player getTarget()
	{
		return this.target;
	}
}
