package net.ccbluex.liquidbounce.features.module.modules.movement.flys.vanilla

import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.flys.FlyMode
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.minecraft.network.play.client.C03PacketPlayer

class VanillaFly : FlyMode("Vanilla") {
    private val vanillaSpeedValue = FloatValue("${valuePrefix}Speed", 2f, 0f, 5f)
    private val vanillaKickBypassValue = BoolValue("${valuePrefix}KickBypass", false)

    private var packets=0

    override fun onEnable() {
        packets=0
    }

    override fun onUpdate(event: UpdateEvent) {
        mc.thePlayer.capabilities.isFlying = false
        mc.thePlayer.motionY = 0.0
        mc.thePlayer.motionX = 0.0
        mc.thePlayer.motionZ = 0.0
        if (mc.gameSettings.keyBindJump.isKeyDown)
            mc.thePlayer.motionY += vanillaSpeedValue.get() * 0.5
        if (mc.gameSettings.keyBindSneak.isKeyDown)
            mc.thePlayer.motionY -= vanillaSpeedValue.get() * 0.5

        MovementUtils.strafe(vanillaSpeedValue.get())
    }

    override fun onPacket(event: PacketEvent) {
        val packet=event.packet

        if(packet is C03PacketPlayer) {
            packets++
            if(packets>40 && vanillaKickBypassValue.get())
                MovementUtils.handleVanillaKickBypass()
        }
    }
}