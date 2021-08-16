package net.ccbluex.liquidbounce.features.module.modules.client

import net.minecraft.client.gui.GuiGameOver
import net.minecraft.client.gui.GuiMainMenu
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.event.Listenable

internal object AntiDeathScreen : Module(
    name = "AntiDeathScreen",
    description = "Fixes random death screen glitches",
    category = Category.CLIENT
) {
    init {
        listener<GuiEvent.Displayed> {
            if (it.screen !is GuiGameOver) return@listener
            if (mc.player.health > 0) {
                mc.player.respawnPlayer()
                mc.displayGuiScreen(null)
            }
        }
    }
}
