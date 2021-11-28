package si.vilfa.junglechronicles.Graphics;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public interface TimeProvider
{
    float getDeltaTime();

    long getElapsedGameTimeMillis();

    long getElapsedGameTimeSeconds();

    long getStartGameTimeMillis();

    long getStartGameTimeSeconds();
}
