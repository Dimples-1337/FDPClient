package net.ccbluex.liquidbounce.features.module.modules.client

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.ui.ultralight.view.DynamicGuiView
import net.ccbluex.liquidbounce.ui.ultralight.view.GuiView
import net.ccbluex.liquidbounce.value.TextValue

@ModuleInfo(name = "Test", category = ModuleCategory.CLIENT)
class Test : Module() {
    private val value=TextValue("URL","https://getfdp.today")
    private var guiView: GuiView? = null

    override fun onEnable() {
        guiView=DynamicGuiView(value.get())
        mc.displayGuiScreen(guiView)
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent){
        if(mc.currentScreen==null){
            guiView ?: return
            guiView!!.destroy()
            state=false
        }
    }
}