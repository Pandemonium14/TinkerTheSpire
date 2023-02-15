package tinker.parts.frames;

import com.badlogic.gdx.graphics.Color;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

public class StableFrame extends AbstractPart {

    public static final String ID = TinkerMod.makeID("StableFrame");

    public StableFrame() {
        super(ID, AbstractPart.PartType.FRAME);
    }

    @Override
    public int potencyChange() {
        return 2;
    }

    @Override
    public int costChange() {
        return 1;
    }

    @Override
    public void onCreation(ContraptionCard c) {
        c.selfRetain = true;
    }

    @Override
    public Color colorToApply() {
        return new Color(0f,0f,0.8f,0.5f);
    }
}
