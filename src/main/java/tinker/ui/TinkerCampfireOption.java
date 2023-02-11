package tinker.ui;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;
import tinker.TinkerMod;
import tinker.actions.AssembleCampfireEffect;
import tinker.parts.AbstractPart;
import tinker.util.TexLoader;

public class TinkerCampfireOption extends AbstractCampfireOption {

    private static final Texture tex = TexLoader.getTexture(TinkerMod.makeImagePath("ui/CampfireTinker.png"));
    private static final UIStrings strings = CardCrawlGame.languagePack.getUIString(TinkerMod.makeID("TinkerCampfireOption"));

    public TinkerCampfireOption() {
        img = tex;
        label = strings.TEXT[0];
        description = strings.EXTRA_TEXT[0];
        boolean isMissingAPart = false;
        for (AbstractPart.PartType type : AbstractPart.PartType.values()) {
            if (!PartPouch.hasPart(type)) {
                isMissingAPart = true;
                break;
            }
        }
        if (isMissingAPart) {
            usable = false;
            description = strings.EXTRA_TEXT[1];
        }

    }


    @Override
    public void useOption() {
        if (this.usable) {// 26
            AbstractDungeon.effectList.add(new AssembleCampfireEffect());// 27
        }
    }

    @Override
    public void update() {
        super.update();
        boolean isMissingAPart = false;
        for (AbstractPart.PartType type : AbstractPart.PartType.values()) {
            if (!PartPouch.hasPart(type)) {
                isMissingAPart = true;
                break;
            }
        }
        if (isMissingAPart) {
            usable = false;
        }
    }
}
