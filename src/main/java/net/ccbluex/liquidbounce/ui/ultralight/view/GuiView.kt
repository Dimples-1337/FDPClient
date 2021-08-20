package net.ccbluex.liquidbounce.ui.ultralight.view

import net.ccbluex.liquidbounce.ui.ultralight.UltralightEngine
import net.minecraft.client.gui.GuiScreen

class GuiView(private val url: String) : GuiScreen() {
    lateinit var view: View

    override fun initGui() {
        view=View()
        view.loadURL(url)
        UltralightEngine.registerView(view)
    }

    override fun drawScreen(p_drawScreen_1_: Int, p_drawScreen_2_: Int, p_drawScreen_3_: Float) {
        view.render()
    }

    override fun onGuiClosed() {
        UltralightEngine.unregisterView(view)
    }
}