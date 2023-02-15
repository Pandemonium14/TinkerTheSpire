package tinker.parts.platings;

import com.badlogic.gdx.graphics.Color;
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

    @Override
    public Color colorToApply() {
        return new Color(1f,0.7f,0f,0.7f);
    }
}
