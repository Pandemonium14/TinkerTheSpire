package tinker.ui;

import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Texture;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.util.TexLoader;

public class PartPouch extends TopPanelItem {

    public static Texture TEX = TexLoader.getTexture(TinkerMod.makeImagePath("icon/PartPouch.png"));
    public final boolean[] partsGot = new boolean[3];

    public PartPouch() {
        super(TEX, TinkerMod.makeID("PartPouch"));
    }

    @Override
    protected void onClick() {

    }

    public boolean hasPart(AbstractPart.PartType type) {
        int index = type.ordinal();
        return partsGot[index];
    }

    public void addPart(AbstractPart.PartType type) {
        partsGot[type.ordinal()] = true;
    }

    public void clearParts() {
        partsGot[0] = false;
        partsGot[1] = false;
        partsGot[2] = false;
    }
}
