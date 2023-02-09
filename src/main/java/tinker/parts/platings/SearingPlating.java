package tinker.parts.platings;

import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

public class SearingPlating extends AbstractPart {

    public static final String ID = TinkerMod.makeID("SearingPlating");

    public SearingPlating() {
        super(ID, PartType.PLATING);
    }

    //actual effect of this plating is in canUpgrade method of ContraptionCard

    @Override
    public int costChange() {
        return 1;
    }
}
