package tinker.ui;

import basemod.TopPanelItem;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.util.TexLoader;

public class PartPouch extends TopPanelItem implements CustomSavable<int[]> {

    public static Texture TEX = TexLoader.getTexture(TinkerMod.makeImagePath("icon/PartPouch.png"));
    public static final int[] partsGot = new int[3];
    private static final UIStrings strings = CardCrawlGame.languagePack.getUIString(TinkerMod.makeID("PartPouch"));

    public PartPouch() {
        super(TEX, TinkerMod.makeID("PartPouch"));
    }

    @Override
    protected void onClick() {

    }

    public static boolean hasPart(AbstractPart.PartType type) {
        int index = type.ordinal();
        return partsGot[index]> 0;
    }

    public static void addPart(AbstractPart.PartType type) {
        partsGot[type.ordinal()] += 1;
    }

    public static void clearParts() {
        partsGot[0] = 0;
        partsGot[1] = 0;
        partsGot[2] = 0;
    }

    @Override
    protected void onHover() {
        super.onHover();
        String tipBody = strings.EXTRA_TEXT[0];
        tipBody += partsGot[0] + strings.EXTRA_TEXT[partsGot[0] > 1 ? 1+3 : 1];
        tipBody += partsGot[1] + strings.EXTRA_TEXT[partsGot[1] > 1 ? 2+3 : 2];
        tipBody += partsGot[2] + strings.EXTRA_TEXT[partsGot[2] > 1 ? 3+3 : 3];
        TipHelper.renderGenericTip(x, y - Settings.HEIGHT / 26.0f, strings.TEXT[0], tipBody);
    }

    @Override
    public int[] onSave() {
        return partsGot;
    }

    @Override
    public void onLoad(int[] ints) {
        if (ints != null && ints.length >=3) {
            System.arraycopy(ints, 0, partsGot, 0, 3);
        }
    }
}
