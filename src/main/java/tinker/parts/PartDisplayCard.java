package tinker.parts;

import basemod.abstracts.CustomCard;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tinker.TinkerMod;
import tinker.actions.AssembleCampfireEffect;
import tinker.actions.AssembleFromRandomAction;
import tinker.parts.frames.HeavyFrame;
import tinker.util.TexLoader;


@NoCompendium
public class PartDisplayCard extends CustomCard {

    public static final String ID = TinkerMod.makeID("PartDisplayCard");
    public AbstractPart part;
    private static final Texture iconTex = TexLoader.getTexture(TinkerMod.makeImagePath("icon/largeLevelIcon.png"));

    public PartDisplayCard() {
        super(ID,"", TinkerMod.MISSING, -2, "Part display card with no part (Does nothing, but defaulted to Heavy Frame for null safety)", CardType.SKILL, CardColor.COLORLESS,CardRarity.SPECIAL, CardTarget.NONE);
        part = new HeavyFrame();
    }

    public PartDisplayCard(AbstractPart part) {
        super(ID,part.previewStrings[0], TinkerMod.MISSING, part.costChange(), part.previewStrings[1], CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.part = part;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void onChoseThisOption() {
        if (AssembleFromRandomAction.isActive) {
            AssembleFromRandomAction.receivePart(part);
        }
        if (AssembleCampfireEffect.isActive) {
            AssembleCampfireEffect.receivePart(part);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new PartDisplayCard(part);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (part.potencyChange() != 0) {
            ExtraIcons.icon(iconTex).text(String.valueOf(part.potencyChange())).textColor(part.potencyChange() < 0 ? Settings.RED_TEXT_COLOR : Settings.CREAM_COLOR).render(this);
        }
    }

}
