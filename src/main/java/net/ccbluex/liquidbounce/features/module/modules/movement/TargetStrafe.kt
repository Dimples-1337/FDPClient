package net.ccbluex.liquidbounce.features.module.modules.movement

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura
import net.ccbluex.liquidbounce.event.*
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.utils.RotationUtils
import net.ccbluex.liquidbounce.utils.EntityUtils
import net.ccbluex.liquidbounce.utils.render.ColorUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.MathHelper
import org.lwjgl.opengl.GL11
import java.awt.Color
import kotlin.math.cos
import kotlin.math.sin

@ModuleInfo(name = "TargetStrafe", category = ModuleCategory.MOVEMENT)
class TargetStrafe : Module() {
    public val radius = FloatValue("Radius", 2.0f, 0.1f, 4.0f)
    private val render = BoolValue("Render", true)
    private val modeValue = ListValue("KeyMode", arrayOf("Jump", "None"), "None")
    private val safewalk = BoolValue("SafeWalk", true)
    val thirdPerson = BoolValue("ThirdPerson", true)
    private val colorType = ListValue("Color", arrayOf("Custom", "Dynamic", "Slowly", "Rainbow", "Skyrainbow", "AnotherRainbow"), "Custom")
    private val redValue = IntegerValue("Red", 255, 0, 255)
    private val greenValue = IntegerValue("Green", 255, 0, 255)
    private val blueValue = IntegerValue("Blue", 255, 0, 255)
    private val saturationValue = FloatValue("Saturation", 0.7F, 0F, 1F)
    private val brightnessValue = FloatValue("Brightness", 1F, 0F, 1F)
    private val accuracyValue = IntegerValue("Accuracy", 0, 0, 59)
    private val thicknessValue = FloatValue("Thickness", 1F, 0.1F, 5F)
    private val outLine = BoolValue("Outline", true)
    private val alwaysRender = BoolValue("Always-Render", true)
    private val behindTarget = BoolValue("Behind-Test", true)
    private lateinit var killAura: KillAura
    private lateinit var speed: Speed

    var direction: Int = 1
    var lastView: Int = 0
    var hasChangedThirdPerson: Boolean = true

    override fun onInitialize() {
        killAura=LiquidBounce.moduleManager.getModule(KillAura::class.java) as KillAura
        speed=LiquidBounce.moduleManager.getModule(Speed::class.java) as Speed
    }

    override fun onEnable() {
        hasChangedThirdPerson = true
        lastView = mc.gameSettings.thirdPersonView
    }

    @EventTarget
    fun onMotion(event: MotionEvent) {
        if (thirdPerson.get()) { 
            if (canStrafe) {
                if (hasChangedThirdPerson) lastView = mc.gameSettings.thirdPersonView
                mc.gameSettings.thirdPersonView = 1
                hasChangedThirdPerson = false
            } else if (!hasChangedThirdPerson) {
                mc.gameSettings.thirdPersonView = lastView
                hasChangedThirdPerson = true
            }
        }

        if (event.eventState == EventState.PRE) {
            if (mc.thePlayer.isCollidedHorizontally)
                this.direction = -this.direction
            
            if (mc.gameSettings.keyBindLeft.pressed)
                this.direction = 1

            if (mc.gameSettings.keyBindRight.pressed)
                this.direction = -1
        }
    }

    @EventTarget
    fun onMove(event: MoveEvent) {
        if (canStrafe && safewalk.get() && checkVoid())
            event.isSafeWalk = true
    }

    fun strafe(event: MoveEvent, moveSpeed: Double) {
        val target = killAura.target
        if (target == null) {
            return
        }
            
        val rotYaw = RotationUtils.getRotationsEntity(target).yaw

        val targetReduced = MathHelper.wrapAngleTo180_float(MathHelper.wrapAngleTo180_float(target!!.rotationYaw) - 180F).toInt()
        val currentReduced = MathHelper.wrapAngleTo180_float(rotYaw).toInt()

        val prediction = if (currentReduced < targetReduced) 
                             -1.0
                         else if (currentReduced > targetReduced)
                             1.0
                         else 0.0
        
        // behind targetstrafe moment $$$
        if (behindTarget.get()) {
            if (mc.thePlayer.getDistanceToEntity(target) > radius.get())
                MovementUtils.setSpeed(event, moveSpeed, rotYaw, prediction, 1.0)
            else
                MovementUtils.setSpeed(event,  moveSpeed, rotYaw, prediction, 0.0)
        } else { // classic targetstrafe
            if (mc.thePlayer.getDistanceToEntity(target) <= radius.get())
                MovementUtils.setSpeed(event, moveSpeed, rotYaw, direction.toDouble(), 0.0)
            else
                MovementUtils.setSpeed(event, moveSpeed, rotYaw, direction.toDouble(), 1.0)
        }
    }

    val keyMode: Boolean
        get() = when (modeValue.get().toLowerCase()) {
            "jump" -> mc.gameSettings.keyBindJump.isKeyDown
            "none" -> mc.thePlayer.movementInput.moveStrafe != 0f || mc.thePlayer.movementInput.moveForward != 0f
            else -> false
        }

    val canStrafe: Boolean
        get() = (state && checkSpeed() && killAura.state && killAura.target != null && !mc.thePlayer.isSneaking && keyMode)

    public fun checkSpeed(): Boolean = speed.state && speed.typeValue.get().equals("Hypixel", true)

    private fun checkVoid(): Boolean {
        for (x in -1..0) {
            for (z in -1..0) {
                if (isVoid(x, z)) {
                    return true
                }
            }
        }
        return false
    }

    private fun isVoid(X: Int, Z: Int): Boolean {
        val fly = LiquidBounce.moduleManager.getModule(Fly::class.java) as Fly
        if (fly.state) {
            return false
        }
        if (mc.thePlayer.posY < 0.0) {
            return true
        }
        var off = 0
        while (off < mc.thePlayer.posY.toInt() + 2) {
            val bb: AxisAlignedBB = mc.thePlayer.entityBoundingBox.offset(X.toDouble(), (-off).toDouble(), Z.toDouble())
            if (mc.theWorld!!.getCollidingBoundingBoxes(mc.thePlayer as Entity, bb).isEmpty()) {
                off += 2
                continue
            }
            return false
            off += 2
        }
        return true
    }

    @EventTarget
    fun onRender3D(event: Render3DEvent) {
        val target = killAura.target
        if ((canStrafe || (alwaysRender.get() && target != null)) && render.get()) {
            target?:return
            GL11.glPushMatrix()
            GL11.glTranslated(
                target.lastTickPosX + (target.posX - target.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX,
                target.lastTickPosY + (target.posY - target.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY,
                target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ
            )
            GL11.glEnable(GL11.GL_BLEND)
            GL11.glEnable(GL11.GL_LINE_SMOOTH)
            GL11.glDisable(GL11.GL_TEXTURE_2D)
            GL11.glDisable(GL11.GL_DEPTH_TEST)
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
            GL11.glRotatef(90F, 1F, 0F, 0F)

            if (outLine.get()) {
                GL11.glLineWidth(thicknessValue.get() + 1.25F)
                GL11.glColor3f(0F, 0F, 0F)
                GL11.glBegin(GL11.GL_LINE_LOOP)

                for (i in 0..360 step 60 - accuracyValue.get()) { // You can change circle accuracy  (60 - accuracy)
                    GL11.glVertex2f(Math.cos(i * Math.PI / 180.0).toFloat() * radius.get(), (Math.sin(i * Math.PI / 180.0).toFloat() * radius.get()))
                }

                GL11.glEnd()
            }

            val slowly = ColorUtils.slowlyRainbow(System.nanoTime(), index * 30 * rainbowSpeed.get(), saturationValue.get(), brightnessValue.get())
            val skyrainbow -> ColorUtils.skyRainbow(index, saturationValue.get(), brightnessValue.get(), rainbowSpeed.get().toDouble())
            val anotherrainbow = ColorUtils.fade(rectCustomColor,100,index+1).rgb
            val rainbow = ColorUtils.hslRainbow(index+1,indexOffset=100*rainbowSpeed.get()).rgb

            GL11.glLineWidth(thicknessValue.get())
            GL11.glBegin(GL11.GL_LINE_LOOP)

            for (i in 0..360 step 60 - accuracyValue.get()) { // You can change circle accuracy  (60 - accuracy)
                when (colorType.get()) {
                    "custom" -> Color(colorRedValue.get(), colorGreenValue.get(), colorBlueValue.get(), colorAlphaValue.get())
                    "Dynamic" -> if (canStrafe) GL11.glColor4f(0.25f, 1f, 0.25f, 1f) else GL11.glColor4f(1f, 1f, 1f, 1f)
                    "slowly" -> ColorUtils.slowlyRainbow(System.nanoTime(), index * 30 * rainbowSpeed.get(), saturationValue.get(), brightnessValue.get()).rgb
                    "rainbow" -> ColorUtils.hslRainbow(index+1,indexOffset=100*rainbowSpeed.get()).rgb
                    "skyrainbow" -> ColorUtils.skyRainbow(index, saturationValue.get(), brightnessValue.get(), rainbowSpeed.get().toDouble())
                    "anotherrainbow" -> ColorUtils.fade(rectCustomColor,100,index+1).rgb
                }
                GL11.glVertex2f(Math.cos(i * Math.PI / 180.0).toFloat() * radius.get(), (Math.sin(i * Math.PI / 180.0).toFloat() * radius.get()))
            }

            GL11.glEnd()

            GL11.glDisable(GL11.GL_BLEND)
            GL11.glEnable(GL11.GL_TEXTURE_2D)
            GL11.glEnable(GL11.GL_DEPTH_TEST)
            GL11.glDisable(GL11.GL_LINE_SMOOTH)

            GL11.glPopMatrix()

            GlStateManager.resetColor()
            GL11.glColor4f(1F, 1F, 1F, 1F)
        }
    }
}
