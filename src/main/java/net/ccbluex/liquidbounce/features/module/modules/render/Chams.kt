/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.value.BoolValue

@ModuleInfo(name = "Chams", category = ModuleCategory.RENDER)
class Chams : Module() {
    val targetsValue = BoolValue("Targets", true)
    val chestsValue = BoolValue("Chests", true)
    val itemsValue = BoolValue("Items", true)
    val legacyMode = BoolValue("Legacy-Mode", false)
    val texturedValue = BoolValue("Textured", true)
    val colorModeValue = ListValue("Text-Color", arrayOf("Custom", "Random", "Rainbow", "AnotherRainbow", "RiseRainbow", "SkyRainbow"), "RiseRainbow")
        private val red = IntegerValue("Red", 0, 0, 255)
        private val green = IntegerValue("Green", 0, 0, 255)
        private val blue = IntegerValue("Blue", 0, 0, 255)
    private val alpha = IntegerValue("Alpha", 0, 0, 255)
}
