package tinker.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import org.lwjgl.opengl.ARBBufferStorage;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

import java.util.ArrayList;

public class PartHelper {

    public static ArrayList<AbstractPart> coreList = new ArrayList<>();
    public static ArrayList<AbstractPart> frameList = new ArrayList<>();
    public static ArrayList<AbstractPart> platingList = new ArrayList<>();

    public static AbstractPart getRandomCore(Random rng) {
        int r = rng.random(coreList.size() - 1);
        return coreList.get(r);
    }
    public static AbstractPart getRandomFrame(Random rng) {
        int r = rng.random(frameList.size() - 1);
        return frameList.get(r);
    }
    public static AbstractPart getRandomPlating(Random rng) {
        int r = rng.random(platingList.size() - 1);
        return platingList.get(r);
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
        if (r < 70) {
            return null;
        } else if (r < 80) {
            return AbstractPart.PartType.FRAME;
        } else if (r < 90) {
            return AbstractPart.PartType.CORE;
        } else {
            return AbstractPart.PartType.PLATING;
        }
    }
}
