package net.ccbluex.liquidbounce.features.module.modules.client

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import org.lwjgl.input.Keyboard

@ModuleInfo(name = "Test", category = ModuleCategory.CLIENT, canEnable = false)
class Test : Module() {
    override fun onEnable() {
        mc.displayGuiScreen(LiquidBounce.keyBindManager)
    }
}