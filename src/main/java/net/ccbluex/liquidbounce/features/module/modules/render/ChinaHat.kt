/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Render3DEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer
import net.ccbluex.liquidbounce.utils.render.ColorUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.value.IntegerValue
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.AxisAlignedBB
import org.lwjgl.opengl.GL11
import org.lwjgl.util.glu.Cylinder
import org.lwjgl.util.glu.GLU
import java.awt.Color

@ModuleInfo(name = "ChinaHat", category = ModuleCategory.RENDER)
class ChinaHat : Module() {

   private val colorModeValue = ListValue("Color", arrayOf("Custom", "Slowly", "Rainbow"), "Custom")
	private val colorRedValue = IntegerValue("Red", 255, 0, 255)
    private val colorGreenValue = IntegerValue("Green", 255, 0, 255)
    private val colorBlueValue = IntegerValue("Blue", 255, 0, 255)
    private val colorAlphaValue = IntegerValue("Alpha", 200, 0, 255)
    
    //Rainbow thingy
    private val saturationValue = FloatValue("Saturation", 1f, 0f, 1f).displayable { colorModeValue.equals("Slowly") }
    private val brightnessValue = FloatValue("Brightness", 1f, 0f, 1f).displayable { colorModeValue.equals("Slowly") }
    
    //Options
    private val spaceValue = IntegerValue("Color-Space", 0, 0, 200)
    private val noFirstPerson = BoolValue("NoFirstPerson", true)

    @EventTarget
    fun onRender3d(event: Render3DEvent) {
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

        int realIndex = 0;

        pre3D();
        GL11.glTranslated(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ)
		GL11.glBegin(GL11.GL_POLYGON)
        
        GL11.glColor4f(r, g, b, al)
        GL11.glVertex3d(posX, posY + height + 0.3F, posZ)

        for (int i = 0; i <= 360; i += 1) {
			double posX2 = posX - Math.sin(i * Math.PI / 180) * radius
			double posZ2 = posZ + Math.cos(i * Math.PI / 180) * radius

			GL11.glVertex3d(posX2, posY + height, posZ2)

            if (spaceValue.get() > 0) {
                Color colour2 = getColor(entity, realIndex * spaceValue.get())
                float r2 = colour2.getRed() / 255.0F
                float g2 = colour2.getGreen() / 255.0F
                float b2 = colour2.getBlue() / 255.0F

                GL11.glColor4f(r2, g2, b2, al)
            }
            
            realIndex++
		}

        GL11.glVertex3d(posX, posY + height + 0.3F, posZ)

        GL11.glEnd()
        post3D()
    }

	private val chinahatColor: Color
        get() =
            when (colorModeValue.get().lowercase()) {
                "custom" -> Color(colorRedValue.get(), colorGreenValue.get(), colorBlueValue.get(), colorAlphaValue.get())
                "slowly" -> ColorUtils.reAlpha(ColorUtils.slowlyRainbow(System.nanoTime(), 0, saturationValue.get(), brightnessValue.get()),colorAlphaValue.get())
                "rainbow" -> ColorUtils.rainbowWithAlpha(colorAlphaValue.get())
                else -> Color.WHITE
		}
	}

    public static void pre3D() {
        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glShadeModel(GL11.GL_SMOOTH)
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_DEPTH_TEST)
        GL11.glDisable(GL11.GL_LIGHTING)
        GL11.glDepthMask(false)
    }

    public static void post3D() {
        GL11.glDepthMask(true)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_BLEND)
        GL11.glPopMatrix()
        GL11.glColor4f(1, 1, 1, 1)
    }

}
