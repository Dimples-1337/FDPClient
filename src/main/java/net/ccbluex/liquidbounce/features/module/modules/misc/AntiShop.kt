package net.ccbluex.liquidbounce.features.module.modules.misc

import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.entity.EntityLivingBase

@ModuleInfo(name = "AntiShop", category = ModuleCategory.MISC)
object AntiShop: Module() {
    val modeValue = ListValue("Mode", arrayOf("BlocksMC"), "BlocksMC")
    var enabled: Boolean = false

    override fun onEnable() { this.enabled = true }
    override fun onDisable() { this.enabled = false }

    override val tag: String
        get() = this.modeValue.get()

    fun isShop(target: EntityLivingBase): Boolean {
        // if this.enabled == false then directly return true
        if (!this.enabled) return true
        return when (this.modeValue.get().lowercase()) {
            "blocksmc" -> {
                "CIT-.*".toRegex().matches(input = target.name)
            }
            else -> false
        }
    }
}
