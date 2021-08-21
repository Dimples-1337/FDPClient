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
    val tagColorModeValue = ListValue("Tag-Color", arrayOf("Custom", "Random", "Rainbow", "AnotherRainbow", "RiseRainbow", "SkyRainbow"), "Custom")
    val redValue = IntegerValue("Red", 255, 0, 255)
	val greenValue = IntegerValue("Green", 255, 0, 255)
	val blueValue = IntegerValue("Blue", 255, 0, 255)
    val alphaValue = IntegerValue("Alpha", 255, 0, 255)
    val brightnessValue = FloatValue("Brightness", 1F, 0F, 1F)
    val saturationValue = FloatValue("Saturation", 1F, 0F, 1F)
}
