package tinker.parts;

import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tinker.TinkerMod;
import tinker.parts.platings.SearingPlating;
import tinker.util.ImageMaker;
import tinker.util.PartHelper;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ContraptionCard extends CustomCard {

    public static final String ID = TinkerMod.makeID("ContraptionCard");

    public int potency;

    public Texture portraitImage = null;

    public final ArrayList<AbstractPart> parts = new ArrayList<>();

    public ContraptionCard() {
        super(ID,"", TinkerMod.MISSING, -2, "Empty contraption Card", CardType.SKILL, CardColor.COLORLESS,CardRarity.SPECIAL, CardTarget.NONE);
    }

    public ContraptionCard(ArrayList<AbstractPart> parts, String name, String rawDesc, int cost, CardType type, String img, CardTarget target) {
        super(ID, name, img, cost, rawDesc, type, CardColor.COLORLESS, CardRarity.SPECIAL, target);
        this.parts.addAll(parts);
    }

    @Override
    public void upgrade() {
        upgradeName();
        forAllParts((p) -> p.onUpgradeCard(this));
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        forAllParts((part) ->part.useEffect(abstractPlayer, abstractMonster, this));
    }

    @Override
    public boolean canUpgrade() {
        return super.canUpgrade() || parts.stream().anyMatch((part) -> SearingPlating.ID.equals(part.part_id));
    }

    @Override
    public void triggerWhenDrawn() {
        forAllParts((part) -> part.triggerWhenDrawn(this));
    }

    //util methods
    public void makeMultiDamage() {
        isMultiDamage = true;
    }


    @Override
    public AbstractCard makeCopy() {
        AbstractCard newCard = super.makeCopy();
        PartHelper.setPartsOnContraption((ContraptionCard) newCard, parts, false);
        newCard.portrait = newCard.jokePortrait = this.portrait;
        return newCard;
    }

    private void forAllParts(Consumer<AbstractPart> consumer) {
        for (AbstractPart part : parts) {
            consumer.accept(part);
        }
    }

    @Override
    protected Texture getPortraitImage() {
        return ImageMaker.makeLargeImage(parts);
    }

    public static class ContraptionSave implements CustomSavable<ArrayList<ArrayList<String>>> {


        @Override
        public ArrayList<ArrayList<String>> onSave() {
            ArrayList<ArrayList<String>> saves = new ArrayList<>();
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                if (card instanceof ContraptionCard) {
                    ContraptionCard cCard = (ContraptionCard) card;
                    ArrayList<String> partsIds = new ArrayList<>();
                    cCard.parts.forEach((p) -> partsIds.add(p.part_id));
                    saves.add(partsIds);
                }
            }
            return saves;
        }

        @Override
        public void onLoad(ArrayList<ArrayList<String>> saves) {
            int nbOfContraptions = 0;
            ArrayList<Integer> indexesToReplace = new ArrayList<>();
            for (int i = 0; i< AbstractDungeon.player.masterDeck.group.size(); i++) {
                AbstractCard card = AbstractDungeon.player.masterDeck.group.get(i);
                if (card instanceof ContraptionCard) {
                    indexesToReplace.add(i);
                }
            }
            if (saves != null && indexesToReplace.size() <= saves.size()) {
                for (int i : indexesToReplace) {
                    ContraptionCard blankCard = (ContraptionCard) AbstractDungeon.player.masterDeck.group.get(i);
                    ArrayList<AbstractPart> partsToApply = new ArrayList<>();
                    for (String id : saves.get(nbOfContraptions)) {
                        partsToApply.add(PartHelper.getPart(id));
                    }
                    nbOfContraptions++;
                    PartHelper.setPartsOnContraption(blankCard, partsToApply);
                }
            }
        }
    }
}
