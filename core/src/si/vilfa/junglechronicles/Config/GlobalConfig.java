package si.vilfa.junglechronicles.Config;

import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Scene.Objects.CollectibleBlockType;
import si.vilfa.junglechronicles.Scene.Objects.PlayerBlockType;
import si.vilfa.junglechronicles.Scene.Objects.TerrainBlockType;

import java.util.HashMap;

import static si.vilfa.junglechronicles.Scene.Objects.CollectibleBlockType.*;
import static si.vilfa.junglechronicles.Scene.Objects.PlayerBlockType.*;
import static si.vilfa.junglechronicles.Scene.Objects.TerrainBlockType.*;

/**
 * @author luka
 * @date 28/11/2021
 * @package si.vilfa.junglechronicles.Config
 **/
public class GlobalConfig
{
    public static final int RESOLUTION_SCALE_ONE = 128;
    public static final int RESOLUTION_SCALE_TWO = 256;

    public static HashMap<TerrainBlockType, ConfigEntry> TERRAIN_BLOCK_CONFIG
            = new HashMap<TerrainBlockType, ConfigEntry>()
    {{
        put(TERRAIN_TOP_CENTER,
            new ConfigEntry("TerrainTopCenter", new Vector2(0, 0), new Vector2(0, 0)));
        put(TERRAIN_TOP_LEFT,
            new ConfigEntry("TerrainTopLeft",
                            new Vector2(RESOLUTION_SCALE_ONE, 0),
                            new Vector2(RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_TOP_RIGHT,
            new ConfigEntry("TerrainTopRight",
                            new Vector2(2 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(2 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_MID_CENTER,
            new ConfigEntry("TerrainMidCenter",
                            new Vector2(3 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(3 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_MID_LEFT,
            new ConfigEntry("TerrainMidLeft",
                            new Vector2(4 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(4 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_MID_RIGHT,
            new ConfigEntry("TerrainMidRight",
                            new Vector2(5 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(5 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_BOTTOM_CENTER,
            new ConfigEntry("TerrainBottomCenter",
                            new Vector2(6 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(6 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_BOTTOM_LEFT,
            new ConfigEntry("TerrainBottomLeft",
                            new Vector2(7 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(7 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_BOTTOM_RIGHT,
            new ConfigEntry("TerrainBottomRight",
                            new Vector2(8 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(8 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_PLATFORM_CENTER,
            new ConfigEntry("TerrainPlatformCenter",
                            new Vector2(9 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(9 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_PLATFORM_LEFT,
            new ConfigEntry("TerrainPlatformLeft",
                            new Vector2(10 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(10 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_PLATFORM_RIGHT,
            new ConfigEntry("TerrainPlatformRight",
                            new Vector2(11 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(11 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_FLOOR_SLOPE_LEFT,
            new ConfigEntry("TerrainFloorSlopeLeft",
                            new Vector2(12 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(12 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_FLOOR_SLOPE_RIGHT,
            new ConfigEntry("TerrainFloorSlopeRight",
                            new Vector2(13 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(13 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_CEILING_SLOPE_LEFT,
            new ConfigEntry("TerrainCeilingSlopeLeft",
                            new Vector2(14 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(14 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_CEILING_SLOPE_RIGHT,
            new ConfigEntry("TerrainCeilingSlopeRight",
                            new Vector2(15 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(15 * RESOLUTION_SCALE_TWO, 0)));
        put(TERRAIN_CORNER_INNER_BOTTOM_LEFT,
            new ConfigEntry("TerrainCornerInnerBottomLeft",
                            new Vector2(16 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(0, RESOLUTION_SCALE_TWO)));
        put(TERRAIN_CORNER_INNER_BOTTOM_RIGHT,
            new ConfigEntry("TerrainCornerInnerBottomRight",
                            new Vector2(17 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(TERRAIN_CEILING_LEFT,
            new ConfigEntry("TerrainCeilingLeft",
                            new Vector2(18 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(2 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(TERRAIN_CEILING_RIGHT,
            new ConfigEntry("TerrainCeilingRight",
                            new Vector2(19 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(3 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_CENTER,
            new ConfigEntry("MidgroundCenter",
                            new Vector2(20 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(4 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_LEFT,
            new ConfigEntry("MidgroundLeft",
                            new Vector2(21 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(5 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_LEFT_SHADOWED,
            new ConfigEntry("MidgroundLeftShadowed",
                            new Vector2(22 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(6 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_RIGHT,
            new ConfigEntry("MidgroundRight",
                            new Vector2(23 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(7 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_RIGHT_SHADOWED,
            new ConfigEntry("MidgroundRightShadowed",
                            new Vector2(24 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(8 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_SLOPE_LEFT_DOWN,
            new ConfigEntry("MidgroundSlopeLeftDown",
                            new Vector2(25 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(9 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_SLOPE_LEFT_DOWN_SHADOWED,
            new ConfigEntry("MidgroundSlopeLeftDownShadowed",
                            new Vector2(26 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(10 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_SLOPE_LEFT_UP,
            new ConfigEntry("MidgroundSlopeLeftUp",
                            new Vector2(27 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(11 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_SLOPE_LEFT_UP_SHADOWED,
            new ConfigEntry("MidgroundSlopeLeftUpShadowed",
                            new Vector2(28 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(12 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_SLOPE_RIGHT_DOWN,
            new ConfigEntry("MidgroundSlopeRightDown",
                            new Vector2(29 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(13 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_SLOPE_RIGHT_DOWN_SHADOWED,
            new ConfigEntry("MidgroundSlopeRightDownShadowed",
                            new Vector2(30 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(14 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_SLOPE_RIGHT_UP,
            new ConfigEntry("MidgroundSlopeRightUp",
                            new Vector2(31 * RESOLUTION_SCALE_ONE, 0),
                            new Vector2(15 * RESOLUTION_SCALE_TWO, RESOLUTION_SCALE_TWO)));
        put(MIDGROUND_SLOPE_RIGHT_UP_SHADOWED,
            new ConfigEntry("MidgroundSlopeRightUpShadowed",
                            new Vector2(0, RESOLUTION_SCALE_ONE),
                            new Vector2(0, 2 * RESOLUTION_SCALE_TWO)));
    }};

    public static HashMap<CollectibleBlockType, ConfigEntry> COLLECTIBLE_BLOCK_CONFIG
            = new HashMap<CollectibleBlockType, ConfigEntry>()
    {{
        put(COLLECTIBLE_GOLD_COIN,
            new ConfigEntry("GoldCoin",
                            new Vector2(RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(COLLECTIBLE_GOLD_COIN_TWO,
            new ConfigEntry("GoldCoinTwo",
                            new Vector2(2 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(2 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(COLLECTIBLE_GOLD_COIN_FOUR,
            new ConfigEntry("GoldCoinFour",
                            new Vector2(3 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(3 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(COLLECTIBLE_GOLD_COIN_EIGHT,
            new ConfigEntry("GoldCoinEight",
                            new Vector2(4 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(4 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(COLLECTIBLE_GOLD_HEAP,
            new ConfigEntry("GoldHeap",
                            new Vector2(5 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(5 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(COLLECTIBLE_GOLD_CHALICE,
            new ConfigEntry("GoldChalice",
                            new Vector2(6 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(6 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(COLLECTIBLE_GOLD_CHEST,
            new ConfigEntry("GoldChest",
                            new Vector2(7 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(7 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(COLLECTIBLE_GOLD_CROWN,
            new ConfigEntry("GoldCrown",
                            new Vector2(8 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(8 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(COLLECTIBLE_GOLD_INGOT,
            new ConfigEntry("GoldIngot",
                            new Vector2(9 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(9 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(COLLECTIBLE_GOLD_TREASURE,
            new ConfigEntry("GoldTreasure",
                            new Vector2(10 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(10 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
    }};

    public static HashMap<PlayerBlockType, ConfigEntry> PLAYER_BLOCK_CONFIG
            = new HashMap<PlayerBlockType, ConfigEntry>()
    {{
        put(PLAYER_LEFT_ONE,
            new ConfigEntry("PlayerL1",
                            new Vector2(11 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(11 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_LEFT_TWO,
            new ConfigEntry("PlayerL2",
                            new Vector2(12 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(12 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_LEFT_THREE,
            new ConfigEntry("PlayerL3",
                            new Vector2(13 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(13 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_LEFT_FOUR,
            new ConfigEntry("PlayerL4",
                            new Vector2(14 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(14 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_LEFT_FIVE,
            new ConfigEntry("PlayerL5",
                            new Vector2(15 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(15 * RESOLUTION_SCALE_TWO, 2 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_LEFT_SIX,
            new ConfigEntry("PlayerL6",
                            new Vector2(16 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(0, 3 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_LEFT_SEVEN,
            new ConfigEntry("PlayerL7",
                            new Vector2(17 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(RESOLUTION_SCALE_TWO, 3 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_LEFT_EIGHT,
            new ConfigEntry("PlayerL8",
                            new Vector2(18 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(2 * RESOLUTION_SCALE_TWO, 3 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_RIGHT_ONE,
            new ConfigEntry("PlayerR1",
                            new Vector2(19 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(3 * RESOLUTION_SCALE_TWO, 3 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_RIGHT_TWO,
            new ConfigEntry("PlayerR2",
                            new Vector2(20 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(4 * RESOLUTION_SCALE_TWO, 3 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_RIGHT_THREE,
            new ConfigEntry("PlayerR3",
                            new Vector2(21 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(5 * RESOLUTION_SCALE_TWO, 3 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_RIGHT_FOUR,
            new ConfigEntry("PlayerR4",
                            new Vector2(22 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(6 * RESOLUTION_SCALE_TWO, 3 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_RIGHT_FIVE,
            new ConfigEntry("PlayerR5",
                            new Vector2(23 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(7 * RESOLUTION_SCALE_TWO, 3 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_RIGHT_SIX,
            new ConfigEntry("PlayerR6",
                            new Vector2(24 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(8 * RESOLUTION_SCALE_TWO, 3 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_RIGHT_SEVEN,
            new ConfigEntry("PlayerR7",
                            new Vector2(25 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(9 * RESOLUTION_SCALE_TWO, 3 * RESOLUTION_SCALE_TWO)));
        put(PLAYER_RIGHT_EIGHT,
            new ConfigEntry("PlayerR8",
                            new Vector2(26 * RESOLUTION_SCALE_ONE, RESOLUTION_SCALE_ONE),
                            new Vector2(10 * RESOLUTION_SCALE_TWO, 3 * RESOLUTION_SCALE_TWO)));
    }};

    //    public static HashMap<TrapBlockType, ConfigEntry> TRAP_BLOCK_CONFIG
    //            = new HashMap<TrapBlockType, ConfigEntry>()
    //    {{
    //        put(FULL_HEALTH, new ConfigEntry("", )); put(HALF_HEALTH, new ConfigEntry("", ));
    //        put(QUARTER_HEALTH, new ConfigEntry("", , ));
    //    }};

    public static class ConfigEntry
    {
        private final String fileName;
        private final Vector2 atlasLocationScaleOne;
        private final Vector2 atlasLocationScaleTwo;

        public ConfigEntry(String fileName,
                           Vector2 atlasLocationScaleOne,
                           Vector2 atlasLocationScaleTwo)
        {
            this.fileName = fileName;
            this.atlasLocationScaleOne = atlasLocationScaleOne;
            this.atlasLocationScaleTwo = atlasLocationScaleTwo;
        }

        public String getFileName(int scale)
        {
            return String.format("%dx/%s@%d.png", scale, fileName, scale);
        }

        public Vector2 getAtlasLocation(int scale)
        {
            if (scale > 1)
            {
                return atlasLocationScaleTwo;
            } else
            {
                return atlasLocationScaleOne;
            }
        }
    }

}
