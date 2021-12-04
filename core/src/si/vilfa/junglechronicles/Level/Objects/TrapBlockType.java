package si.vilfa.junglechronicles.Level.Objects;

/**
 * @author luka
 * @date 27/11/2021
 * @package si.vilfa.junglechronicles.Level.Objects
 **/
public enum TrapBlockType implements BlockType
{
    // TODO Do this differently / Get rid of this.

    FULL_HEALTH(100), THREE_QUARTER_HEALTH(75), HALF_HEALTH(50), QUARTER_HEALTH(25);

    private final int healthPoints;

    TrapBlockType(int healthPoints)
    {
        this.healthPoints = healthPoints;
    }

    public static TrapBlockType getDefault()
    {
        return FULL_HEALTH;
    }

    public int getHealthPoints()
    {
        return healthPoints;
    }
}
