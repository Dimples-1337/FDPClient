/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 * 
 * This code belongs to WYSI-Foundation. Please give credits when using this in your repository.
 */
package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;
import java.awt.Color;

@ModuleInfo(name = "AsianHat", spacedName = "Asian Hat", description = "Yep. China Hat.", category = ModuleCategory.RENDER)
public class AsianHat extends Module {

    private final ListValue colorModeValue = new ListValue("Color", new String[] {"Custom", "Rainbow", "Sky", "LiquidSlowly", "Fade", "Mixer"}, "Custom");
	private final IntegerValue colorRedValue = new IntegerValue("Red", 255, 0, 255);
	private final IntegerValue colorGreenValue = new IntegerValue("Green", 255, 0, 255);
	private final IntegerValue colorBlueValue = new IntegerValue("Blue", 255, 0, 255);
	private final IntegerValue colorAlphaValue = new IntegerValue("Alpha", 255, 0, 255);
	private final FloatValue saturationValue = new FloatValue("Saturation", 1F, 0F, 1F);
	private final FloatValue brightnessValue = new FloatValue("Brightness", 1F, 0F, 1F);
	private final IntegerValue mixerSecondsValue = new IntegerValue("Seconds", 2, 1, 10);
    private final IntegerValue spaceValue = new IntegerValue("Color-Space", 0, 0, 200);
    private final BoolValue noFirstPerson = new BoolValue("NoFirstPerson", true);
    private final BoolValue hatBorder = new BoolValue("HatBorder", true);
    private final IntegerValue borderAlphaValue = new IntegerValue("BorderAlpha", 255, 0, 255);
    private final FloatValue borderWidthValue = new FloatValue("BorderWidth", 1F, 0.1F, 4F);

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        EntityLivingBase entity = mc.thePlayer;
        if (entity == null || (noFirstPerson.get() && mc.gameSettings.thirdPersonView == 0)) return;

        final AxisAlignedBB bb = entity.getEntityBoundingBox();
        double radius = bb.maxX - bb.minX;
		double height = bb.maxY - bb.minY;
		double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks;
	    double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks;
	    double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks;

        Color colour = getColor(entity, 0);
        float r = colour.getRed() / 255.0F;
        float g = colour.getGreen() / 255.0F;
        float b = colour.getBlue() / 255.0F;
        float al = colorAlphaValue.get() / 255.0F;

        pre3D();
        GL11.glTranslated(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ);
		GL11.glBegin(GL11.GL_POLYGON);
        
        GL11.glColor4f(r, g, b, al);
        GL11.glVertex3d(posX, posY + height + 0.3F, posZ);

        for (int i = 0; i <= 360; i += 1) {
			double posX2 = posX - Math.sin(i * Math.PI / 180) * radius;
			double posZ2 = posZ + Math.cos(i * Math.PI / 180) * radius;

			GL11.glVertex3d(posX2, posY + height, posZ2);

            if (spaceValue.get() > 0 && !colorModeValue.get().equalsIgnoreCase("Custom")) {
                Color colour2 = getColor(entity, i * spaceValue.get());
                float r2 = colour2.getRed() / 255.0F;
                float g2 = colour2.getGreen() / 255.0F;
                float b2 = colour2.getBlue() / 255.0F;

                GL11.glColor4f(r2, g2, b2, al);
            }
		}

        GL11.glVertex3d(posX, posY + height + 0.3F, posZ);

        GL11.glEnd();

        if (hatBorder.get()) {
            float lineAlp = borderAlphaValue.get() / 255.0F;

            GL11.glLineWidth(borderWidthValue.get());
            GL11.glBegin(GL11.GL_LINE_LOOP);

            GL11.glColor4f(r, g, b, lineAlp);
            
            for (int j = 0; j <= 360; j += 1) {
			    double posX2 = posX - Math.sin(j * Math.PI / 180) * radius;
			    double posZ2 = posZ + Math.cos(j * Math.PI / 180) * radius;

			    GL11.glVertex3d(posX2, posY + height, posZ2);

                if (spaceValue.get() > 0 && !colorModeValue.get().equalsIgnoreCase("Custom")) {
                    Color colour2 = getColor(entity, j * spaceValue.get());
                    float r2 = colour2.getRed() / 255.0F;
                    float g2 = colour2.getGreen() / 255.0F;
                    float b2 = colour2.getBlue() / 255.0F;

                    GL11.glColor4f(r2, g2, b2, lineAlp);
                }
		    }

            GL11.glEnd();
        }

        post3D();
    }

	public final Color getColor(final Entity ent, final int index) {
		switch (colorModeValue.get()) {
			case "Custom":
				return new Color(colorRedValue.get(), colorGreenValue.get(), colorBlueValue.get());
			case "Rainbow":
			 	return new Color(RenderUtils.getRainbowOpaque(mixerSecondsValue.get(), saturationValue.get(), brightnessValue.get(), index));
			case "Sky":
				return RenderUtils.skyRainbow(index, saturationValue.get(), brightnessValue.get());
			case "LiquidSlowly":
				return ColorUtils.LiquidSlowly(System.nanoTime(), index, saturationValue.get(), brightnessValue.get());
			case "Mixer":
				return ColorMixer.getMixedColor(index, mixerSecondsValue.get());
			default:
				return ColorUtils.fade(new Color(colorRedValue.get(), colorGreenValue.get(), colorBlueValue.get()), index, 100);
		}
	}

    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glDisable(2884);
    }

    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
    }

}
