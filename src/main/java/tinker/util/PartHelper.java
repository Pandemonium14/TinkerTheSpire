package tinker.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import org.lwjgl.opengl.ARBBufferStorage;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

import java.util.ArrayList;
import java.util.HashMap;

public class PartHelper {

    public static HashMap<String,AbstractPart> coreList = new HashMap<>();
    public static HashMap<String,AbstractPart> frameList = new HashMap<>();
    public static HashMap<String,AbstractPart> platingList = new HashMap<>();

    public static AbstractPart getRandomCore(Random rng) {
        int r = rng.random(coreList.size() - 1);
        return coreList.values().toArray(new AbstractPart[0])[r];
    }
    public static AbstractPart getRandomFrame(Random rng) {
        int r = rng.random(frameList.size() - 1);
        return frameList.values().toArray(new AbstractPart[0])[r];
    }
    public static AbstractPart getRandomPlating(Random rng) {
        int r = rng.random(platingList.size() - 1);
        return platingList.values().toArray(new AbstractPart[0])[r];
    }

    public static AbstractPart getPart(String ID) {
        if (coreList.containsKey(ID)) return coreList.get(ID);
        if (frameList.containsKey(ID)) return frameList.get(ID);
        if (platingList.containsKey(ID)) return platingList.get(ID);
        return null;
    }

    public static ContraptionCard assembleCardFromIds(ArrayList<String> partIds) {
        ArrayList<AbstractPart> parts = new ArrayList<>();
        partIds.forEach((id) -> parts.add(getPart(id)));
        return assembleCard(parts);
    }


    public static ContraptionCard assembleCard(ArrayList<AbstractPart> parts) {
        ContraptionCard resultCard = new ContraptionCard(parts, makeName(parts), makeRawDesc(parts), makeCost(parts), makeType(parts),makeImg(parts),makeTarget(parts));
        for (AbstractPart part : parts) {
            part.onCreation(resultCard);
        }

        int potency = 0;
        for (AbstractPart part : parts) potency += part.potencyChange();
        if (potency < 0) potency = 0;
        resultCard.potency = potency;
        for (AbstractPart part : parts) part.applyStats(resultCard);

        Texture[] textures = ImageMaker.makeImage(parts);
        resultCard.portrait = resultCard.jokePortrait = new TextureAtlas.AtlasRegion(textures[1],0,0,textures[1].getWidth(),textures[1].getHeight());

        return resultCard;
    }

    private static String makeName(ArrayList<AbstractPart> parts) {
        StringBuilder building = new StringBuilder();
        for (AbstractPart part : parts) {
            building.append(part.contraptionStrings[0]);
        }
        for (AbstractPart part : parts) {
            building.append(part.contraptionStrings[1]);
        }
        for (AbstractPart part : parts) {
            building.append(part.contraptionStrings[2]);
        }
        return building.toString();
    }

    private static String makeRawDesc(ArrayList<AbstractPart> parts) {
        StringBuilder building = new StringBuilder();
        for (AbstractPart part : parts) {
            building.append(part.contraptionStrings[3]);
        }
        for (AbstractPart part : parts) {
            building.append(part.contraptionStrings[4]);
        }
        for (AbstractPart part : parts) {
            building.append(part.contraptionStrings[5]);
        }
        return building.toString();
    }

    private static int makeCost(ArrayList<AbstractPart> parts) {
        int cost = 0;
        for (AbstractPart part : parts) cost += part.costChange();
        if (cost < 0) cost = 0;
        return cost;
    }

    private static String makeImg(ArrayList<AbstractPart> parts) {
        return TinkerMod.MISSING;
    }

    private static AbstractCard.CardType makeType(ArrayList<AbstractPart> parts) {
        AbstractCard.CardType type = AbstractCard.CardType.SKILL;
        for (AbstractPart part : parts) {
            if (part.leastRestrictedAcceptableType() == AbstractCard.CardType.ATTACK) {
                type = AbstractCard.CardType.ATTACK;
            }
        }
        return type;
    }

    private static AbstractCard.CardTarget makeTarget(ArrayList<AbstractPart> parts) {
        boolean mustTarget = false;
        boolean targetsSelf = false;
        boolean targetsAll = false;
        for (AbstractPart part : parts) {
            AbstractCard.CardTarget t = part.minimalTarget();
            if (t == AbstractCard.CardTarget.ENEMY || t == AbstractCard.CardTarget.SELF_AND_ENEMY) {
                mustTarget = true;
            }
            if (t == AbstractCard.CardTarget.SELF || t == AbstractCard.CardTarget.SELF_AND_ENEMY || t == AbstractCard.CardTarget.ALL) {
                targetsSelf = true;
            }
            if (t == AbstractCard.CardTarget.ALL || t == AbstractCard.CardTarget.ALL_ENEMY) {
                targetsAll = true;
            }
        }
        if (mustTarget && targetsSelf) return AbstractCard.CardTarget.SELF_AND_ENEMY;
        if (mustTarget && !targetsSelf) return AbstractCard.CardTarget.ENEMY;
        if (!mustTarget && targetsAll && targetsSelf) return AbstractCard.CardTarget.ALL;
        if (!mustTarget && targetsAll && !targetsSelf) return AbstractCard.CardTarget.ALL_ENEMY;
        if (!mustTarget && !targetsAll && targetsSelf) return AbstractCard.CardTarget.SELF;
        return AbstractCard.CardTarget.NONE;
    }

    public static AbstractPart.PartType rollTypeForMap() {
        int r = AbstractDungeon.mapRng.random(99);
        if (r < 40) {
            return null;
        } else if (r < 60) {
            return AbstractPart.PartType.FRAME;
        } else if (r < 80) {
            return AbstractPart.PartType.CORE;
        } else {
            return AbstractPart.PartType.PLATING;
        }
    }

    public static void setPartsOnContraption(ContraptionCard c, ArrayList<AbstractPart> parts) {
        setPartsOnContraption(c,parts,true);
    }

    public static void setPartsOnContraption(ContraptionCard c, ArrayList<AbstractPart> parts, boolean makeImage) {
        c.parts.clear();
        c.parts.addAll(parts);
        c.name = makeName(parts);
        c.rawDescription = makeRawDesc(parts);
        c.initializeDescription();
        c.cost = makeCost(parts);
        c.costForTurn = c.cost;
        c.type = makeType(parts);
        c.target = makeTarget(parts);
        c.setPortraitTextures(makeImg(parts),makeImg(parts));
        c.setDisplayRarity(AbstractCard.CardRarity.SPECIAL);

        int potency = 0;
        for (AbstractPart part : parts) potency += part.potencyChange();
        if (potency < 0) potency = 0;
        c.potency = potency;
        for (AbstractPart part : parts) part.applyStats(c);
        for (int i = 0; i < c.timesUpgraded; i++) {
            for (AbstractPart part : parts) part.onUpgradeCard(c);
        }
        if (makeImage) {
            Texture[] textures = ImageMaker.makeImage(parts);
            c.portrait = c.jokePortrait = new TextureAtlas.AtlasRegion(textures[1], 0, 0, textures[1].getWidth(), textures[1].getHeight());
        }
    }
}
