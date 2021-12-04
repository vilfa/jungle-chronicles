package si.vilfa.junglechronicles.Level;

/**
 * @author luka
 * @date 04/12/2021
 * @package si.vilfa.junglechronicles.Level
 **/
public class LevelMap
{
    public enum LevelMapLayer
    {
        OBJECT_LAYER("Objects"), TERRAIN_LAYER("Terrain"), BACKGROUND_LAYER("Background");

        final String layerName;

        LevelMapLayer(String layerName)
        {
            this.layerName = layerName;
        }

        public String getLayerName()
        {
            return layerName;
        }
    }

    public enum LevelObjectProperties
    {
        COLLECTIBLE("isCollectible"),
        COLLECTIBLE_POINTS("collectiblePoints"),
        TRAP("isTrap"),
        TRAP_HEALTH_POINTS("trapHealthPoints");

        final String propertyName;

        LevelObjectProperties(String propertyName)
        {
            this.propertyName = propertyName;
        }

        public String getPropertyName()
        {
            return propertyName;
        }
    }
}
