package tinker.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;


public class NegativeEnergyPatches {

    @SpirePatch2(clz = AbstractCard.class, method = "renderEnergy")
    public static class PatchOne {
        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {
                public void edit(FieldAccess f)
                        throws CannotCompileException {
                    if (f.getClassName().equals(AbstractCard.class.getName())
                            && f.getFieldName().equals("cost"))
                        f.replace("{$_ = $0 instanceof tinker.parts.PartDisplayCard ? $proceed() + 2 : $proceed();}");
                }
            };
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "getCost")
    public static class PatchTwo {
        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {
                public void edit(FieldAccess f)
                        throws CannotCompileException {
                    if (f.getClassName().equals(AbstractCard.class.getName())
                            && f.getFieldName().equals("cost"))
                        f.replace("{$_ = $0 instanceof tinker.parts.PartDisplayCard ? $proceed() + 2 : $proceed();}");
                }
            };
        }
    }

}
