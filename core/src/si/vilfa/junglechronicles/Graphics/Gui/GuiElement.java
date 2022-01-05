package si.vilfa.junglechronicles.Graphics.Gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Gameplay.Game;

/**
 * @author luka
 * @date 02/01/2022
 * @package si.vilfa.junglechronicles.Graphics.Gui
 **/
public abstract class GuiElement extends GameComponent
{
    protected final Game game;
    protected final Skin skin;

    public GuiElement(Game game)
    {
        super(0, true);
        this.game = game;
        skin = new Skin(Gdx.files.internal("Graphics/Gui/flat-earth/skin/flat-earth-ui.json"));
    }

    public abstract Actor getActor();

    protected abstract void initializeElement();

    @Override
    public abstract void update();

    @Override
    public void dispose()
    {
        skin.dispose();
    }

    public enum HudSprite
    {
        ZERO(0, "hud0"),
        ONE(1, "hud1"),
        TWO(2, "hud2"),
        THREE(3, "hud3"),
        FOUR(4, "hud4"),
        FIVE(5, "hud5"),
        SIX(6, "hud6"),
        SEVEN(7, "hud7"),
        EIGHT(8, "hud8"),
        NINE(9, "hud9"),
        HEART_EMPTY(0, "hudHeart_empty"),
        HEART_HALF(50, "hudHeart_half"),
        HEART_FULL(100, "hudHeart_full"),
        JEWEL_BLUE_FULL(-1, "hudJewel_blue"),
        JEWEL_BLUE_EMPTY(-1, "hudJewel_blue_empty"),
        JEWEL_GREEN_FULL(-1, "hudJewel_green"),
        JEWEL_GREEN_EMPTY(-1, "hudJewel_green_empty"),
        JEWEL_RED_FULL(-1, "hudJewel_red"),
        JEWEL_RED_EMPTY(-1, "hudJewel_red_empty"),
        JEWEL_YELLOW_FULL(-1, "hudJewel_yellow"),
        JEWEL_YELLOW_EMPTY(-1, "hudJewel_yellow_empty"),
        KEY_BLUE_FULL(-1, "hudKey_blue"),
        KEY_BLUE_EMPTY(-1, "hudKey_blue_empty"),
        KEY_GREEN_FULL(-1, "hudKey_green"),
        KEY_GREEN_EMPTY(-1, "hudKey_green_empty"),
        KEY_RED_FULL(-1, "hudKey_red"),
        KEY_RED_EMPTY(-1, "hudKey_red_empty"),
        KEY_YELLOW_FULL(-1, "hudKey_yellow"),
        KEY_YELLOW_EMPTY(-1, "hudKey_yellow_empty");

        private final int numericValue;
        private final String regionName;

        HudSprite(int numericValue, String regionName)
        {
            this.numericValue = numericValue;
            this.regionName = regionName;
        }

        public static HudSprite[] getDigits()
        {
            return new HudSprite[]{ ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE };
        }

        public static HudSprite[] getLives()
        {
            return new HudSprite[]{ HEART_FULL, HEART_HALF, HEART_EMPTY };
        }

        public int getNumericValue()
        {
            return numericValue;
        }

        public String getRegionName()
        {
            return regionName;
        }
    }
}
