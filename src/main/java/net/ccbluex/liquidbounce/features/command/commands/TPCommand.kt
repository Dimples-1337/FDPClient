package net.ccbluex.liquidbounce.features.command.commands

import net.ccbluex.liquidbounce.features.command.Command
import kotlin.math.cos
import kotlin.math.sin

class ClipCommand : Command("tp", emptyArray()) {
    override fun execute(args: Array<String>) {
        if (args.size > 2) {
            val tpX: Double
            val tpY: Double
            val tpZ: Double
            try {
                tpX = args[1].toDouble()
                tpY = args[2].toDouble()
                tpZ = args[3].toDouble()
            } catch (e: NumberFormatException) {
                chatSyntaxError()
                return
            }

mc.thePlayer.setPosition(tpX, tpY, tpZ)

            return
        }

        chatSyntax("tp <x> <y> <z>")
    }

    override fun tabComplete(args: Array<String>): List<String> {
        return emptyList()

    }
}
