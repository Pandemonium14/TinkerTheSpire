package tinker.parts.frames;

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
}
