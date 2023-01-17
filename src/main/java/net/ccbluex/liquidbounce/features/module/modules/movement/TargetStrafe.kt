
package net.ccbluex.liquidbounce.features.module.modules.movement

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.*
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer
import net.ccbluex.liquidbounce.features.module.modules.client.HUD
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.utils.RotationUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.minecraft.entity.EntityLivingBase
import org.lwjgl.opengl.GL11
import java.awt.Color
import kotlin.math.cos
import kotlin.math.sin

@ModuleInfo(name = "TargetStrafe", category = ModuleCategory.MOVEMENT)
class TargetStrafe : Module() {
    private val radiusValue = FloatValue("Radius", 2.0f, 0.1f, 4.0f)
    private val holdSpaceValue = BoolValue("HoldSpace", false)
    private val onlySpeedValue = BoolValue("OnlySpeed", false)
    private val onlyGroundValue = BoolValue("OnlyGround", false)
    private val renderValue = BoolValue("Render", true)
    private val combatCheckValue = BoolValue("combatCheck", false)
    private val voidCheckValue = BoolValue("voidCheck", true)
    private var direction = true
    private var yaw = 0f

    @EventTarget
    fun onMotion(event: MotionEvent) {
        if (event.eventState === EventState.PRE) {
            if(voidCheckValue.get() && isVoid()){
                direction = !direction
                return
            }
            if (mc.gameSettings.keyBindLeft.isKeyDown) {
                direction = true
            } else if (mc.gameSettings.keyBindRight.isKeyDown) {
                direction = false
            } else if (mc.thePlayer.isCollidedHorizontally) {
                direction = !direction
            }
        }
    }

    @EventTarget
    fun strafe(event: MoveEvent) {
        val target = LiquidBounce.combatManager.target
        if (canStrafe(target)) {   
            if(mc.thePlayer.onGround || !onlyGroundValue.get()) {
                yaw = RotationUtils.getRotationsEntity(target).yaw
            }
            MovementUtils.setSpeed(
                event,
                MovementUtils.getSpeed().toDouble(),
                yaw,
                if (direction) 0.8 else -0.8,
                if (mc.thePlayer.getDistanceToEntity(target) <= radiusValue.get()) 0.2 else 0.9
            )
        }
    }

    fun isVoid(): Boolean {
        val pos = FallingPlayer(mc.thePlayer.posX + mc.thePlayer.motionX * 2,
            mc.thePlayer.posY,
            mc.thePlayer.posZ + mc.thePlayer.motionZ * 2,
            mc.thePlayer.motionX * 1.5, mc.thePlayer.motionY * -0.5, mc.thePlayer.motionY * 1.5, mc.thePlayer.rotationYaw, 0f, 0f, 0f).findCollision(60)
        return (pos != null && pos.y < (mc.thePlayer.posY - 7))
    }

    @EventTarget
    fun onRender3D(event: Render3DEvent) {
        val target = LiquidBounce.combatManager.target
        if (renderValue.get() && canStrafe(target)) {
            GL11.glDisable(3553)
            GL11.glEnable(2848)
            GL11.glEnable(2881)
            GL11.glEnable(2832)
            GL11.glEnable(3042)
            GL11.glBlendFunc(770, 771)
            GL11.glHint(3154, 4354)
            GL11.glHint(3155, 4354)
            GL11.glHint(3153, 4354)
            GL11.glDisable(2929)
            GL11.glDepthMask(false)
            GL11.glLineWidth(3f)

            GL11.glBegin(3)
            val x = target!!.lastTickPosX + (target.posX - target.lastTickPosX) * event.partialTicks - mc.renderManager.viewerPosX
            val y = target.lastTickPosY + (target.posY - target.lastTickPosY) * event.partialTicks - mc.renderManager.viewerPosY
            val z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * event.partialTicks - mc.renderManager.viewerPosZ
            val radius = radiusValue.get()
            for (i in 0..360 step 5) {
                RenderUtils.glColor(Color.getHSBColor(if (i <180) { HUD.rainbowStart.get() + (HUD.rainbowStop.get() - HUD.rainbowStart.get()) * (i / 180f) } else { HUD.rainbowStart.get() + (HUD.rainbowStop.get() - HUD.rainbowStart.get()) * (-(i-360) / 180f) }, 0.7f, 1.0f))
                GL11.glVertex3d(x - sin(i * Math.PI / 180F) * radius, y, z + cos(i * Math.PI / 180F) * radius)
            }
            GL11.glEnd()

            GL11.glDepthMask(true)
            GL11.glEnable(2929)
            GL11.glDisable(2848)
            GL11.glDisable(2881)
            GL11.glEnable(2832)
            GL11.glEnable(3553)
        }
    }

    private fun canStrafe(target: EntityLivingBase?): Boolean {
        return target != null &&
                (!holdSpaceValue.get() || mc.thePlayer.movementInput.jump) &&
                (!onlySpeedValue.get() || LiquidBounce.moduleManager[Speed::class.java]!!.state) &&
                (!combatCheckValue.get() || LiquidBounce.combatManager.inCombat)
    }
}
