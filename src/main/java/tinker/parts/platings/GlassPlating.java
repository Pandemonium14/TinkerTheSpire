package tinker.parts.platings;

import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

public class GlassPlating extends AbstractPart {

    public static final String ID = TinkerMod.makeID("GlassPlating");

    public GlassPlating() {
        super(ID, PartType.PLATING);
    }

    @Override
    public void applyStats(ContraptionCard c) {
        c.exhaust = true;
    }

    @Override
    public int potencyChange() {
        return 1;
    }

    @Override
    public int costChange() {
        return -1;
    }
}
