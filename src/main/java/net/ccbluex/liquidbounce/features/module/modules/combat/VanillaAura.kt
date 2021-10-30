/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.combat

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.RotationUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.utils.EntityUtils
import net.ccbluex.liquidbounce.value.IntegerValue
import net.minecraft.entity.EntityLivingBase
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.entity.projectile.EntityFireball
import net.minecraft.network.play.client.C02PacketUseEntity
import net.minecraft.network.play.client.C0APacketAnimation
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition
import net.minecraft.network.play.client.C03PacketPlayer

@ModuleInfo(name = "VanillaAura", category = ModuleCategory.COMBAT)
class VanillaAura : Module() {

    private val swingValue = ListValue("Swing", arrayOf("Normal", "Packet", "None"), "Normal")
    private val rotationValue = BoolValue("Rotation", true)
    private val testValue = BoolValue("test", false)
    private val speedValue = IntegerValue("Speed", 1, 1, 35)

    @EventTarget
    private fun onUpdate(event: UpdateEvent) {
        for (entity in mc.theWorld.loadedEntityList) {
            if (mc.thePlayer.getDistanceToEntity(entity) < 15 && entity is EntityLivingBase && EntityUtils.isSelected(entity, true)) {
                if (rotationValue.get()) {
                    RotationUtils.setTargetRotation(RotationUtils.getRotationsNonLivingEntity(entity))
                }
                repeat(speedValue.get()) {
                mc.thePlayer.sendQueue.addToSendQueue(C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK))
                if(testValue.get()){
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(1e+100,0.0 , -999.0, true))
                    mc.netHandler.addToSendQueue(C03PacketPlayer(true))
                }
                if (swingValue.equals("Normal")) {
                    mc.thePlayer.swingItem()
                } else if (swingValue.equals("Packet")) {
                    mc.netHandler.addToSendQueue(C0APacketAnimation())
                }
            }
                break
            }
        }
    }
}
