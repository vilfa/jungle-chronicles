package si.vilfa.junglechronicles.Config;

import si.vilfa.junglechronicles.Level.Scene.AnimatedSceneTile;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;

import java.util.TreeMap;

/**
 * @author luka
 * @date 15/12/2021
 * @package si.vilfa.junglechronicles.Config
 **/
public class Config
{
    private static final int PPU = (int) PhysicsEngine.WORLD_PPU;

    public static TreeMap<Integer, AnimatedSceneTile.AnimationRegion> PLAYER_ANIMATION_SPEC
            = new TreeMap<Integer, AnimatedSceneTile.AnimationRegion>()
    {{
        put(-8, new AnimatedSceneTile.AnimationRegion(0, 0, PPU, PPU, 1.5f, 1.4f));
        put(-7, new AnimatedSceneTile.AnimationRegion(PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(-6, new AnimatedSceneTile.AnimationRegion(2 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(-5, new AnimatedSceneTile.AnimationRegion(3 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(-4, new AnimatedSceneTile.AnimationRegion(4 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(-3, new AnimatedSceneTile.AnimationRegion(5 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(-2, new AnimatedSceneTile.AnimationRegion(6 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(-1, new AnimatedSceneTile.AnimationRegion(7 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(1, new AnimatedSceneTile.AnimationRegion(8 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(2, new AnimatedSceneTile.AnimationRegion(9 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(3, new AnimatedSceneTile.AnimationRegion(10 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(4, new AnimatedSceneTile.AnimationRegion(11 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(5, new AnimatedSceneTile.AnimationRegion(12 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(6, new AnimatedSceneTile.AnimationRegion(13 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(7, new AnimatedSceneTile.AnimationRegion(14 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
        put(8, new AnimatedSceneTile.AnimationRegion(15 * PPU, 0, PPU, PPU, 1.5f, 1.4f));
    }};
}
