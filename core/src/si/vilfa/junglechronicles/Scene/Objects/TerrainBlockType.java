package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * @author luka
 * @date 27/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public enum TerrainBlockType
{
    TERRAIN_TOP_CENTER("TerrainTopCenter", new Vector2(0, 0), new Vector2(0, 0)),
    TERRAIN_TOP_LEFT("TerrainTopLeft",
                     new Vector2(TerrainBlockType.resolutionScaleOne, 0),
                     new Vector2(TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_TOP_RIGHT("TerrainTopRight",
                      new Vector2(2 * TerrainBlockType.resolutionScaleOne, 0),
                      new Vector2(2 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_MID_CENTER("TerrainMidCenter",
                       new Vector2(3 * TerrainBlockType.resolutionScaleOne, 0),
                       new Vector2(3 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_MID_LEFT("TerrainMidLeft",
                     new Vector2(4 * TerrainBlockType.resolutionScaleOne, 0),
                     new Vector2(4 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_MID_RIGHT("TerrainMidRight",
                      new Vector2(5 * TerrainBlockType.resolutionScaleOne, 0),
                      new Vector2(5 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_BOTTOM_CENTER("TerrainBottomCenter",
                          new Vector2(6 * TerrainBlockType.resolutionScaleOne, 0),
                          new Vector2(6 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_BOTTOM_LEFT("TerrainBottomLeft",
                        new Vector2(7 * TerrainBlockType.resolutionScaleOne, 0),
                        new Vector2(7 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_BOTTOM_RIGHT("TerrainBottomRight",
                         new Vector2(8 * TerrainBlockType.resolutionScaleOne, 0),
                         new Vector2(8 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_PLATFORM_CENTER("TerrainPlatformCenter",
                            new Vector2(9 * TerrainBlockType.resolutionScaleOne, 0),
                            new Vector2(9 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_PLATFORM_LEFT("TerrainPlatformLeft",
                          new Vector2(10 * TerrainBlockType.resolutionScaleOne, 0),
                          new Vector2(10 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_PLATFORM_RIGHT("TerrainPlatformRight",
                           new Vector2(11 * TerrainBlockType.resolutionScaleOne, 0),
                           new Vector2(11 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_FLOOR_SLOPE_LEFT("TerrainFloorSlopeLeft",
                             new Vector2(12 * TerrainBlockType.resolutionScaleOne, 0),
                             new Vector2(12 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_FLOOR_SLOPE_RIGHT("TerrainFloorSlopeRight",
                              new Vector2(13 * TerrainBlockType.resolutionScaleOne, 0),
                              new Vector2(13 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_CEILING_SLOPE_LEFT("TerrainCeilingSlopeLeft",
                               new Vector2(14 * TerrainBlockType.resolutionScaleOne, 0),
                               new Vector2(14 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_CEILING_SLOPE_RIGHT("TerrainCeilingSlopeRight",
                                new Vector2(15 * TerrainBlockType.resolutionScaleOne, 0),
                                new Vector2(15 * TerrainBlockType.resolutionScaleTwo, 0)),
    TERRAIN_CORNER_INNER_BOTTOM_RIGHT("TerrainCornerInnerBottomRight",
                                      new Vector2(16 * TerrainBlockType.resolutionScaleOne, 0),
                                      new Vector2(0, TerrainBlockType.resolutionScaleTwo)),
    TERRAIN_CORNER_INNER_BOTTOM_LEFT("TerrainCornerInnerBottomLeft",
                                     new Vector2(17 * TerrainBlockType.resolutionScaleOne, 0),
                                     new Vector2(TerrainBlockType.resolutionScaleTwo,
                                                 TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_CENTER("MidgroundCenter",
                     new Vector2(18 * TerrainBlockType.resolutionScaleOne, 0),
                     new Vector2(2 * TerrainBlockType.resolutionScaleTwo,
                                 TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_LEFT("MidgroundLeft",
                   new Vector2(19 * TerrainBlockType.resolutionScaleOne, 0),
                   new Vector2(3 * TerrainBlockType.resolutionScaleTwo,
                               TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_LEFT_SHADOWED("MidgroundLeftShadowed",
                            new Vector2(20 * TerrainBlockType.resolutionScaleOne, 0),
                            new Vector2(4 * TerrainBlockType.resolutionScaleTwo,
                                        TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_RIGHT("MidgroundRight",
                    new Vector2(21 * TerrainBlockType.resolutionScaleOne, 0),
                    new Vector2(5 * TerrainBlockType.resolutionScaleTwo,
                                TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_RIGHT_SHADOWED("MidgroundRightShadowed",
                             new Vector2(22 * TerrainBlockType.resolutionScaleOne, 0),
                             new Vector2(6 * TerrainBlockType.resolutionScaleTwo,
                                         TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_SLOPE_LEFT_DOWN("MidgroundSlopeLeftDown",
                              new Vector2(23 * TerrainBlockType.resolutionScaleOne, 0),
                              new Vector2(7 * TerrainBlockType.resolutionScaleTwo,
                                          TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_SLOPE_LEFT_DOWN_SHADOWED("MidgroundSlopeLeftDownShadowed",
                                       new Vector2(24 * TerrainBlockType.resolutionScaleOne, 0),
                                       new Vector2(8 * TerrainBlockType.resolutionScaleTwo,
                                                   TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_SLOPE_LEFT_UP("MidgroundSlopeLeftUp",
                            new Vector2(25 * TerrainBlockType.resolutionScaleOne, 0),
                            new Vector2(9 * TerrainBlockType.resolutionScaleTwo,
                                        TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_SLOPE_LEFT_UP_SHADOWED("MidgroundSlopeLeftUpShadowed",
                                     new Vector2(26 * TerrainBlockType.resolutionScaleOne, 0),
                                     new Vector2(10 * TerrainBlockType.resolutionScaleTwo,
                                                 TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_SLOPE_RIGHT_DOWN("MidgroundSlopeRightDown",
                               new Vector2(27 * TerrainBlockType.resolutionScaleOne, 0),
                               new Vector2(11 * TerrainBlockType.resolutionScaleTwo,
                                           TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_SLOPE_RIGHT_DOWN_SHADOWED("MidgroundSlopeRightDownShadowed",
                                        new Vector2(28 * TerrainBlockType.resolutionScaleOne, 0),
                                        new Vector2(11 * TerrainBlockType.resolutionScaleTwo,
                                                    TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_SLOPE_RIGHT_UP("MidgroundSlopeRightUp",
                             new Vector2(29 * TerrainBlockType.resolutionScaleOne, 0),
                             new Vector2(12 * TerrainBlockType.resolutionScaleTwo,
                                         TerrainBlockType.resolutionScaleTwo)),
    MIDGROUND_SLOPE_RIGHT_UP_SHADOWED("MidgroundSlopeRightUpShadowed",
                                      new Vector2(30 * TerrainBlockType.resolutionScaleOne, 0),
                                      new Vector2(13 * TerrainBlockType.resolutionScaleTwo,
                                                  TerrainBlockType.resolutionScaleTwo));

    private static final int resolutionScaleOne = 128;
    private static final int resolutionScaleTwo = 256;
    private final String fileName;
    private final Vector2 atlasLocScaleOne;
    private final Vector2 atlasLocScaleTwo;

    TerrainBlockType(String fileName, Vector2 atlasLocScaleOne, Vector2 atlasLocScaleTwo)
    {
        this.fileName = fileName;
        this.atlasLocScaleOne = atlasLocScaleOne;
        this.atlasLocScaleTwo = atlasLocScaleTwo;
    }

    public String fileName(int scale)
    {
        return String.format("%s@%dx.png", fileName, scale);
    }

    public Vector2 atlasLocation(int scale)
    {
        if (scale > 1)
        {
            return atlasLocScaleTwo;
        } else
        {
            return atlasLocScaleOne;
        }
    }
}
