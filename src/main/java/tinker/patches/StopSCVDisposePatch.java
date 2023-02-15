package tinker.patches;


import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import tinker.parts.ContraptionCard;

import java.util.ArrayList;

@SpirePatch2(clz = SingleCardViewPopup.class, method = "close")
@SpirePatch2(clz = SingleCardViewPopup.class, method = "updateBetaArtToggler")
public class StopSCVDisposePatch {

    @SpireInstrumentPatch
    public static ExprEditor stopDisposingIfContraption() {
        return new ExprEditor() {
            public void edit(FieldAccess f)
                    throws CannotCompileException
            {
                if (f.getClassName().equals(SingleCardViewPopup.class.getName())
                        && f.getFieldName().equals("portraitImg"))
                    f.replace("{$_ = card instanceof " + ContraptionCard.class.getName() + " ? null : $proceed($$);}");
            }
        };
    }
}
