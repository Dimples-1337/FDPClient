/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.minecraft.network.play.client.C03PacketPlayer

class WatchDoggyHop : SpeedMode("WatchDoggyHop") {
    
    private var canBoost = 0
    private var enabled = false

    override fun onEnable() {
        enabled = true
        canBoost = 0
        mc.timer.timerSpeed = 1f
        super.onEnable()
    }

    override fun onDisable() {
        enabled = false
        mc.timer.timerSpeed = 1f
        super.onDisable()
    }
    
    override fun onUpdate() {
        if (MovementUtils.isMoving() && enabled) {
            if (mc.thePlayer.onGround) {
                mc.timer.timerSpeed = 0.5684284f
                mc.thePlayer.jump()
                if (canBoost < 1) {
                    canBoost = 1
                }
                MovementUtils.strafe()
            } else {
                if (canBoost > 0) {
                    mc.timer.timerSpeed = 1.2315716f
                    canBoost -= 1
                    MovementUtils.strafe()
                } else {
                    mc.timer.timerSpeed = 1f
                    MovementUtils.strafe()
                }
            }
        } else {
            mc.timer.timerSpeed = 1f
            mc.thePlayer.motionX = 0.0
            mc.thePlayer.motionZ = 0.0
            mc.thePlayer.speedInAir = 0.02f
        }
    }

    override fun onPacket(event: PacketEvent) {
        val packet = event.packet
        if (packet is C03PacketPlayer) {
            canBoost = 2
        }
    }
}
