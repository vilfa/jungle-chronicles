package si.vilfa.junglechronicles.Scene.Objects;

/**
 * @author luka
 * @date 27/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public enum CollectibleBlockType implements BlockType
{
    COLLECTIBLE_GOLD_COIN(1),
    COLLECTIBLE_GOLD_COIN_TWO(2),
    COLLECTIBLE_GOLD_COIN_FOUR(4),
    COLLECTIBLE_GOLD_COIN_EIGHT(8),
    COLLECTIBLE_GOLD_HEAP(10),
    COLLECTIBLE_GOLD_CHALICE(4),
    COLLECTIBLE_GOLD_CHEST(12),
    COLLECTIBLE_GOLD_CROWN(8),
    COLLECTIBLE_GOLD_INGOT(6),
    COLLECTIBLE_GOLD_TREASURE(14);

    private final int points;

    CollectibleBlockType(int points)
    {
        this.points = points;
    }

    public static CollectibleBlockType getDefault()
    {
        return COLLECTIBLE_GOLD_COIN;
    }

    public int getPoints()
    {
        return points;
    }
}
