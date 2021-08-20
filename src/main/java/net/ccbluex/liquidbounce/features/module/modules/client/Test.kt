package net.ccbluex.liquidbounce.features.module.modules.client

import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.ui.ultralight.view.GuiView

@ModuleInfo(name = "Test", category = ModuleCategory.CLIENT, canEnable = false)
class Test : Module() {
    override fun onEnable() {
        mc.displayGuiScreen(GuiView("https://getfdp.today"))
    }
}