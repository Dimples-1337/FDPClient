package net.ccbluex.liquidbounce.features.special

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Listenable
import net.ccbluex.liquidbounce.event.PacketEvent
import net.minecraft.network.handshake.client.C00Handshake
import net.ccbluex.liquidbounce.utils.PacketUtils
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition
import net.minecraft.network.play.server.S40PacketDisconnect
import net.minecraft.util.ChatComponentText
object ServerSpoof : Listenable {
    var enable = false
    var address = "redesky.com"

    @EventTarget
    fun onPacket(event: PacketEvent) {
        if (enable && event.packet is C00Handshake) {
            val packet = event.packet
            val ipList = address.split(":").toTypedArray()
            if(packet.ip == "59.111."+"137.99"){
                packet.port = 0
                PacketUtils.handlePacket(S40PacketDisconnect(ChatComponentText("客户端验证失败，请重启启动器")))
            }
            packet.ip = ipList[0]
            if (ipList.size > 1) {
                packet.port = ipList[1].toInt()
            } else {
                packet.port = 25565
            }
        }
    }

    override fun handleEvents() = true
}