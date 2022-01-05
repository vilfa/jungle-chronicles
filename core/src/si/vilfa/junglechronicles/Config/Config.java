package si.vilfa.junglechronicles.Config;

import com.badlogic.gdx.utils.Array;

import java.util.Objects;

/**
 * @author luka
 * @date 15/12/2021
 * @package si.vilfa.junglechronicles.Config
 **/
public class Config
{
    public static final Array<Pair<Integer>> RESOLUTIONS = new Array<>();

    static
    {
        RESOLUTIONS.add(new Pair<>(1280, 720));
        RESOLUTIONS.add(new Pair<>(1366, 768));
        RESOLUTIONS.add(new Pair<>(1440, 900));
        RESOLUTIONS.add(new Pair<>(1600, 900));
        RESOLUTIONS.add(new Pair<>(1920, 1080));
        RESOLUTIONS.add(new Pair<>(2560, 1440));
    }

    public static class Pair<T>
    {
        public T one;
        public T two;

        public Pair(T one, T two)
        {
            this.one = one;
            this.two = two;
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(one, two);
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?> pair = (Pair<?>) o;
            return one.equals(pair.one) && two.equals(pair.two);
        }

        @Override
        public String toString()
        {
            return "Pair{" + "one=" + one + ", two=" + two + '}';
        }
    }
}
