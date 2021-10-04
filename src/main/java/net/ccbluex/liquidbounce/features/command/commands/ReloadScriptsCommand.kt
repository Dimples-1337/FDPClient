/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.command.commands

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.features.command.Command
import net.ccbluex.liquidbounce.features.command.CommandManager
import net.ccbluex.liquidbounce.features.module.modules.misc.KillInsults
import net.ccbluex.liquidbounce.ui.cape.GuiCapeManager
import net.ccbluex.liquidbounce.ui.font.Fonts

class ReloadCommand : Command("reloadScripts", emptyArray()) {
    /**
     * Execute commands with provided [args]
     */
    override fun execute(args: Array<String>) {
            LiquidBounce.commandManager.commands.clear();
            LiquidBounce.commandManager.registerCommands();
            LiquidBounce.isStarting = true
            scriptManager.disableScripts();
            scriptManager.unloadScripts();
            for(module in LiquidBounce.moduleManager.modules)
                LiquidBounce.moduleManager.generateCommand(module) 
            scriptManager.loadScripts();
            scriptManager.enableScripts();
            LiquidBounce.configManager.load(LiquidBounce.configManager.nowConfig,false)
            LiquidBounce.isStarting = false
            chat("Reloaded.")
        System.gc()
    }
}
