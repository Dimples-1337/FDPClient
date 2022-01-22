/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Render3DEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.FloatValue
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11
import java.lang.Math.cos
import java.lang.Math.sin

@ModuleInfo(name = "KillESP", category = ModuleCategory.RENDER)
class KillESP : Module() {

    private val r = FloatValue("R", 1F, 0F, 1F)
    private val g = FloatValue("G", 1F, 0F, 1F)
    private val b = FloatValue("B", 1F, 0F, 1F)

    private val al = FloatValue("Alpha", 0.7F, 0F, 1F)

    private var markEntity: EntityLivingBase? = null
    private val markTimer = MSTimer()

    @EventTarget
    fun onRender3D(event: Render3DEvent) {
        markEntity = (LiquidBounce.moduleManager[KillAura::class.java] as KillAura).target

        if (markEntity != null) {
            if (markTimer.hasTimePassed(500) || markEntity!!.isDead) {
                markTimer.reset()
                markEntity = null
                return
            }

            // Can mark
            val drawTime = (System.currentTimeMillis() % 2000).toInt()
            val drawMode = drawTime > 1000
            var drawPercent = drawTime / 1000F

            // True when goes up
            if (!drawMode) {
                drawPercent=1-drawPercent
            } else {
                drawPercent-=1
            }

            val points = mutableListOf<Vec3>()
            val bb = markEntity!!.entityBoundingBox
            val radius = bb.maxX - bb.minX
            val height = bb.maxY - bb.minY
            val posX = markEntity!!.lastTickPosX + (markEntity!!.posX - markEntity!!.lastTickPosX) * mc.timer.renderPartialTicks
            var posY = markEntity!!.lastTickPosY + (markEntity!!.posY - markEntity!!.lastTickPosY) * mc.timer.renderPartialTicks

            if (drawMode) {
                posY-=0.5
            } else {
                posY+=0.5
            }

            val posZ = markEntity!!.lastTickPosZ + (markEntity!!.posZ - markEntity!!.lastTickPosZ) * mc.timer.renderPartialTicks

            for (i in 0..360 step 7) {
                points.add(Vec3(posX - sin(i * Math.PI / 180F) * radius,posY+height*drawPercent,posZ + cos(i * Math.PI / 180F) * radius))
            }

            points.add(points[0])

            // Draw
            mc.entityRenderer.disableLightmap()
            GL11.glPushMatrix()
            GL11.glDisable(GL11.GL_TEXTURE_2D)
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
            GL11.glEnable(GL11.GL_LINE_SMOOTH)
            GL11.glEnable(GL11.GL_BLEND)
            GL11.glDisable(GL11.GL_DEPTH_TEST)
            GL11.glBegin(GL11.GL_LINE_STRIP)

            for (i in 0..20) {
                var moveFace = (height / 60F) * i

                if (drawMode) {
                    moveFace = -moveFace
                }

                val firstPoint=points[0]

                GL11.glVertex3d(
                        firstPoint.xCoord - mc.renderManager.viewerPosX, firstPoint.yCoord - moveFace - mc.renderManager.viewerPosY,
                        firstPoint.zCoord - mc.renderManager.viewerPosZ)
                GL11.glColor4f(r.get(), g.get(), b.get(), al.get() * (i / 20F))

                for (vec3 in points) {
                    GL11.glVertex3d(
                            vec3.xCoord - mc.renderManager.viewerPosX, vec3.yCoord - moveFace - mc.renderManager.viewerPosY,
                            vec3.zCoord - mc.renderManager.viewerPosZ
                    )
                }
                GL11.glColor4f(0F,0F,0F,0F)
            }

            GL11.glEnd()
            GL11.glEnable(GL11.GL_DEPTH_TEST)
            GL11.glDisable(GL11.GL_LINE_SMOOTH)
            GL11.glDisable(GL11.GL_BLEND)
            GL11.glEnable(GL11.GL_TEXTURE_2D)
            GL11.glPopMatrix()
        }
    }
}
