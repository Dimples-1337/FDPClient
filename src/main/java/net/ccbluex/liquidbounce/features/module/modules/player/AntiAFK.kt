/*
 *
 *  * MotherF▉▉▉▉▉▉▉▉▉Client Hacked Client
 *  * A shit open source mixin-based injection hacked client for Minecraft using Minecraft Forge based on LiquidBounce.
 *  * DeleteFDP.today
 *
 */

package net.ccbluex.liquidbounce.features.module.modules.player

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer
import net.ccbluex.liquidbounce.utils.misc.RandomUtils
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.client.settings.GameSettings
import net.minecraft.client.settings.KeyBinding

@ModuleInfo(name = "AntiAFK", category = ModuleCategory.PLAYER)
class AntiAFK : Module() {

    private val swingDelayTimer = MSTimer()
    private val delayTimer = MSTimer()

    private val modeValue = ListValue("Mode", arrayOf("Old", "test", "Random", "Custom"), "Random")

    private val swingDelayValue = IntegerValue("SwingDelay", 100, 0, 1000)
    private val rotationDelayValue = IntegerValue("RotationDelay", 100, 0, 1000)
    private val rotationAngleValue = FloatValue("RotationAngle", 1f, -180F, 180F)

    private val jumpValue = BoolValue("Jump", true)
    private val moveValue = BoolValue("Move", true)
    private val rotateValue = BoolValue("Rotate", true)
    private val swingValue = BoolValue("Swing", true)

    private var shouldMove = false
    private var randomTimerDelay = 500L

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        when (modeValue.get().toLowerCase()) {
            "old" -> {
                mc.gameSettings.keyBindForward.pressed = true

                if (delayTimer.hasTimePassed(500)) {
                    mc.thePlayer.rotationYaw += 180F
                    delayTimer.reset()
                }
            }
            "test" -> {
                if (RandomUtils.nextInt(0, 8) == 3) mc.thePlayer.swingItem()
                if (RandomUtils.nextInt(0, 10) == 3 && mc.thePlayer.ticksExisted % 150 == 0 && mc.thePlayer.onGround) mc.thePlayer.jump()
                //if(mc.player.ticksExisted % 500 == 0){mc.thePlayer.sendChatMessage(RandomUtils.randomString(1))}
                val pos = FallingPlayer(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.motionX * 1.1, mc.thePlayer.motionY * 1.1, mc.thePlayer.motionY * 1.1, 0f, 0f, 0f, 0f).findCollision(60)
                if (pos != null && pos.y < (mc.thePlayer.posY - 7)) {
                    mc.thePlayer.rotationYaw += RandomUtils.nextFloat(0F, 90F)
                    mc.thePlayer.rotationPitch += RandomUtils.nextFloat(-10F, 10F)
                    return
                }
                mc.gameSettings.keyBindForward.pressed = true
                if (delayTimer.hasTimePassed(RandomUtils.nextInt(340, 685)
                        .toLong()) || mc.thePlayer.isCollidedHorizontally) {
                    mc.thePlayer.rotationYaw += RandomUtils.nextFloat(-180F, 180F)
                    mc.thePlayer.rotationPitch += RandomUtils.nextFloat(-10F, 10F)
                    delayTimer.reset()
                }
            }
            "random" -> {
                KeyBinding.setKeyBindState(getRandomMoveKeyBind(), shouldMove)
                if (!delayTimer.hasTimePassed(randomTimerDelay)) return
                shouldMove = false
                randomTimerDelay = 500L
                when (RandomUtils.nextInt(0, 6)) {
                    0 -> {
                        if (mc.thePlayer.onGround) mc.thePlayer.jump()
                        delayTimer.reset()
                    }
                    1 -> {
                        if (!mc.thePlayer.isSwingInProgress) mc.thePlayer.swingItem()
                        delayTimer.reset()
                    }
                    2 -> {
                        randomTimerDelay = RandomUtils.nextInt(0, 1000).toLong()
                        shouldMove = true
                        delayTimer.reset()
                    }
                    3 -> {
                        mc.thePlayer.inventory.currentItem = RandomUtils.nextInt(0, 9)
                        mc.thePlayer.Controller.updateController()
                        delayTimer.reset()
                    }
                    4 -> {
                        mc.thePlayer.rotationYaw += RandomUtils.nextFloat(-180.0F, 180.0F)
                        delayTimer.reset()
                    }
                    5 -> {
                        if (mc.thePlayer.rotationPitch <= -90 || mc.thePlayer.rotationPitch >= 90) mc.thePlayer.rotationPitch = 0F
                        mc.thePlayer.rotationPitch += RandomUtils.nextFloat(-10.0F, 10.0F)
                        delayTimer.reset()
                    }
                }
            }
            "custom" -> {
                if (moveValue.get())
                    mc.gameSettings.keyBindForward.pressed = true

                if (jumpValue.get() && mc.thePlayer.onGround)
                    mc.thePlayer.jump()

                if (rotateValue.get() && delayTimer.hasTimePassed(rotationDelayValue.get().toLong())) {
                    mc.thePlayer.rotationYaw += rotationAngleValue.get()
                    if (mc.thePlayer.rotationPitch <= -90 || mc.thePlayer.rotationPitch >= 90) mc.thePlayer.rotationPitch = 0F
                    mc.thePlayer.rotationPitch += RandomUtils.nextFloat(0F, 1F) * 2 - 1
                    delayTimer.reset()
                }

                if (swingValue.get() && !mc.thePlayer.isSwingInProgress && swingDelayTimer.hasTimePassed(swingDelayValue.get()
                        .toLong())) {
                    mc.thePlayer.swingItem()
                    swingDelayTimer.reset()
                }
            }
        }
    }

    private fun getRandomMoveKeyBind(): Int {
        when (RandomUtils.nextInt(0, 4)) {
            0 -> return mc.gameSettings.keyBindRight.keyCode
            1 -> return mc.gameSettings.keyBindLeft.keyCode
            2 -> return mc.gameSettings.keyBindBack.keyCode
            3 -> return mc.gameSettings.keyBindForward.keyCode
            else -> return 0
        }
    }

    override fun onDisable() {
        if (!GameSettings.isKeyDown(mc.gameSettings.keyBindForward))
            mc.gameSettings.keyBindForward.pressed = false
    }
}