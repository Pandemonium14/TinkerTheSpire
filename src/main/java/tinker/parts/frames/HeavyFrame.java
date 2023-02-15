package tinker.parts.frames;

import com.badlogic.gdx.graphics.Color;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;

public class HeavyFrame extends AbstractPart {

    public static final String ID = TinkerMod.makeID("HeavyFrame");

    public HeavyFrame() {
        super(ID, PartType.FRAME);
    }

    @Override
    public int potencyChange() {
        return 3;
    }

    @Override
    public int costChange() {
        return 2;
    }

    @Override
    public Color colorToApply() {
        return new Color(0.7f,0.7f,0.8f,0.5f);
    }
}
