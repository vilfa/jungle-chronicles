package si.vilfa.junglechronicles.Graphics;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public interface WindowAdapter
{
	void resize(int width, int height);

	float getAspectRatio();

	void setAspectRatio(float aspectRatio);
}
