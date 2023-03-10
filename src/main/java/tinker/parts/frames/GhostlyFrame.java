package tinker.parts.frames;

import com.badlogic.gdx.graphics.Color;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

public class GhostlyFrame extends AbstractPart {

    public static final String ID = TinkerMod.makeID("GhostlyFrame");

    public GhostlyFrame() {
        super(ID, PartType.FRAME);
    }

    @Override
    public int potencyChange() {
        return 3;
    }

    @Override
    public int costChange() {
        return 1;
    }

    @Override
    public void onCreation(ContraptionCard c) {
        c.isEthereal = true;
    }

    @Override
    public Color colorToApply() {
        return new Color(0.7f,0f,0.8f,0.5f);
    }
}
