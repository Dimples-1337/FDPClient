package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.*
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.extensions.getDistanceToEntityBox
import net.ccbluex.liquidbounce.utils.EntityUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.item.EntityArmorStand

@ModuleInfo(name = "NoRender", description = "Increase FPS by decreasing visible entities.", category = ModuleCategory.RENDER)
class NoRender : Module() {

    private val itemsValue = BoolValue("Items", true)
    private val playersValue = BoolValue("Players", true)
    private val mobsValue = BoolValue("Mobs", true)
    private val animalsValue = BoolValue("Animals", true)
    private val armorStandValue = BoolValue("ArmorStand", true)
    private val allValue = BoolValue("All", true)
    private val autoResetValue = BoolValue("AutoReset", true)
    private val maxRenderRange = FloatValue("MaxRenderRange", 4F, 0F, 16F)

    @EventTarget
    fun onMotion(event: MotionEvent) {
    	for (en in mc.theWorld.loadedEntityList) {
    		val entity = en!! as Entity
    		if ((allValue.get()
                ||(itemsValue.get() && entity is EntityItem)
    			|| (playersValue.get() && entity is EntityPlayer)
    			|| (mobsValue.get() && EntityUtils.isMob(entity))
    			|| (animalsValue.get() && EntityUtils.isAnimal(entity))
                || (armorStandValue.get() && entity is EntityArmorStand))
    			&& entity != mc.thePlayer!!)
    			entity.renderDistanceWeight = if (mc.thePlayer.getDistanceToEntityBox(entity).toFloat() <= maxRenderRange.get()) 1.0 else 0.0
            else if (autoResetValue.get())
                entity.renderDistanceWeight = 1.0
    	}
    }

 	override fun onDisable() {
 		for (en in mc.theWorld.loadedEntityList) {
 			val entity = en!! as Entity
 			if (entity != mc.thePlayer!! && entity.renderDistanceWeight <= 0.0)
 				entity.renderDistanceWeight = 1.0
 		}
 	}

}
