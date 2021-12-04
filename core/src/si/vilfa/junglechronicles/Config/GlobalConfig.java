package si.vilfa.junglechronicles.Config;

/**
 * @author luka
 * @date 28/11/2021
 * @package si.vilfa.junglechronicles.Config
 **/
public class GlobalConfig
{
    public enum TiledMapLayer
    {
        OBJECT_LAYER("Objects"), TERRAIN_LAYER("Terrain"), BACKGROUND_LAYER("Background");

        final String layerName;

        TiledMapLayer(String layerName)
        {
            this.layerName = layerName;
        }

        public String getLayerName()
        {
            return layerName;
        }
    }

    public enum TiledObjectProperties
    {
        COLLECTIBLE("isCollectible");

        final String propertyName;

        TiledObjectProperties(String propertyName)
        {
            this.propertyName = propertyName;
        }

        public String getPropertyName()
        {
            return propertyName;
        }
    }
}
