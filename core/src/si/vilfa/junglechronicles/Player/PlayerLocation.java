package si.vilfa.junglechronicles.Player;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;

/**
 * @author luka
 * @date 12/12/2021
 * @package si.vilfa.junglechronicles.Player
 **/
public class PlayerLocation implements Location<Vector2>
{
    private final Vector2 position;
    private float orientation;

    public PlayerLocation()
    {
        position = new Vector2();
        orientation = 0f;
    }

    @Override
    public Vector2 getPosition()
    {
        return position;
    }

    @Override
    public float getOrientation()
    {
        return orientation;
    }

    @Override
    public void setOrientation(float orientation)
    {
        this.orientation = orientation;
    }

    @Override
    public float vectorToAngle(Vector2 vector)
    {
        return (float) Math.atan2(-vector.x, vector.y);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        outVector.x = -(float) Math.sin(angle);
        outVector.y = (float) Math.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation()
    {
        return new PlayerLocation();
    }
}
