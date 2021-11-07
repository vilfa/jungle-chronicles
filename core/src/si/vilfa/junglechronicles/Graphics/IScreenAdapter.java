package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public interface IScreenAdapter
{
	Camera getCamera();

	Viewport getViewport();
}
