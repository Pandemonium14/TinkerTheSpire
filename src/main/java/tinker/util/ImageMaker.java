package tinker.util;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;

import java.util.ArrayList;

public class ImageMaker {

    private static ShaderProgram shader;

    private static FrameBuffer largeBuffer;
    private static SpriteBatch largeSb;
    private static OrthographicCamera largeCam = new OrthographicCamera(500, 380);

    private static FrameBuffer smallBuffer;
    private static SpriteBatch smallSb;
    private static OrthographicCamera smallCam = new OrthographicCamera(250,190);

    private static void setupShader(Color rColor, Color lColor, float angle) {
        if (shader == null) {
            shader = new ShaderProgram(TinkerMod.makePath("shader/splitWheelVertex.vs"),
                    TinkerMod.makePath("shader/splitWheelFragment.fs"));
            if (!shader.isCompiled()) {
                System.err.println(shader.getLog());
            }
            if (shader.getLog().length() > 0) {
                System.out.println(shader.getLog());
            }
            BaseMod.logger.info("============ Just initialized rainbow Shader");
        }
        shader.setUniformf("u_rRed", rColor.r);
        shader.setUniformf("u_rGreen", rColor.g);
        shader.setUniformf("u_rBlue", rColor.b);
        shader.setUniformf("u_lRed", lColor.r);
        shader.setUniformf("u_lGreen", lColor.g);
        shader.setUniformf("u_lBlue", lColor.b);
        shader.setUniformf("u_splitAngle", angle);
    }

    public static Texture[] makeImage(ArrayList<AbstractPart> parts) {
        AbstractPart core = null;
        AbstractPart frame = null;
        AbstractPart plating = null;
        for (AbstractPart p : parts) {
            switch (p.type) {
                case PLATING: {
                    plating = p;
                    break;
                }
                case CORE: {
                    core = p;
                    break;
                }
                case FRAME: {
                    frame = p;
                    break;
                }
            }
        }
        if (core != null && frame != null && plating != null) {
            return makeImage(core,frame,plating);
        } else {
            return new Texture[]{TexLoader.getTexture(TinkerMod.MISSING),TexLoader.getTexture(TinkerMod.MISSING)};
        }
    }

    public static Texture[] makeImage(AbstractPart core, AbstractPart frame, AbstractPart plating) {
        setupShader(frame.colorToApply(),plating.colorToApply(), core.splitAngle());

        beginLargeBuffer();
        largeSb.setShader(shader);
        largeSb.draw(core.basePortrait(),0,0);
        endLargeBuffer();
        TextureRegion large_image = new TextureRegion(largeBuffer.getColorBufferTexture());
        BaseMod.logger.info(large_image.getRegionWidth() +" , "+ large_image.getRegionHeight());
        large_image.flip(false,true);

        beginSmallBuffer();
        smallSb.draw(large_image,0,0,250,190);
        endSmallBuffer();

        TextureRegion small_image = new TextureRegion(smallBuffer.getColorBufferTexture());
        small_image.flip(false,true);

        Texture[] textures = new Texture[2];
        textures[0] = large_image.getTexture();
        textures[1] = small_image.getTexture();
        return textures;
    }

    public static Texture makeLargeImage(ArrayList<AbstractPart> parts) {
        AbstractPart core = null;
        AbstractPart frame = null;
        AbstractPart plating = null;
        for (AbstractPart p : parts) {
            switch (p.type) {
                case PLATING: {
                    plating = p;
                    break;
                }
                case CORE: {
                    core = p;
                    break;
                }
                case FRAME: {
                    frame = p;
                    break;
                }
            }
        }
        if (core != null && frame != null && plating != null) {
            return makeLargeImage(core,frame,plating);
        } else {
            return TexLoader.getTexture(TinkerMod.MISSING);
        }
    }

    public static Texture makeLargeImage(AbstractPart core, AbstractPart frame, AbstractPart plating) {
        setupShader(frame.colorToApply(),plating.colorToApply(), core.splitAngle());

        beginLargeBuffer();
        largeSb.setShader(shader);
        largeSb.draw(core.basePortrait(),0,0);
        endLargeBuffer();
        TextureRegion large_image = new TextureRegion(largeBuffer.getColorBufferTexture());
        BaseMod.logger.info(large_image.getRegionWidth() +" , "+ large_image.getRegionHeight());
        large_image.flip(false,true);

        return large_image.getTexture();
    }

    private static void beginLargeBuffer() {
        if (largeBuffer == null) {
            largeBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,500,380,false);
        }
        if (largeSb == null) {
            largeSb = new SpriteBatch();
            largeSb.setProjectionMatrix(largeCam.combined);
            largeSb.setColor(Color.WHITE);
        }
        largeSb.begin();
        largeBuffer.begin();
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true, true, true, true);
    }

    private static void endLargeBuffer() {
        largeSb.flush();
        largeBuffer.end();
        largeSb.end();
    }

    private static void beginSmallBuffer() {
        if (smallBuffer == null) {
            smallBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,250,190,false);
        }
        if (smallSb == null) {
            smallSb = new SpriteBatch();
            smallSb.setProjectionMatrix(smallCam.combined);
            smallSb.setColor(Color.WHITE);
        }
        smallSb.begin();
        smallBuffer.begin();
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true, true, true, true);
    }

    private static void endSmallBuffer() {
        smallSb.flush();
        smallBuffer.end();
        smallSb.end();
    }
}
