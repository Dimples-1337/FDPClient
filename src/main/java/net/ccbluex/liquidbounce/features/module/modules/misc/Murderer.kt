package net.ccbluex.liquidbounce.features.module.modules.misc

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Render3DEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11
import java.awt.Color

@ModuleInfo(name = "Murderer", category = ModuleCategory.MISC)
class Murderer: Module() {
    private val mode = ListValue("Mode", arrayOf("ESPBox", "Tracers"), "ESPBox")
    private val boxWidth = FloatValue("Box-Width", 3f, .5f, 5f).displayable { mode.equals("ESPBox") }
    private val thickness = FloatValue("Thickness", 2f, 1f, 5f).displayable { mode.equals("Tracers") }

    private lateinit var allPlayers: ArrayList<EntityPlayer>
    private val murderer: ArrayList<EntityPlayer> = ArrayList()
    private val detective: ArrayList<EntityPlayer> = ArrayList()
    private val player: ArrayList<EntityPlayer> = ArrayList()

    override val tag: String
        get() = mode.get()

    @EventTarget
    fun draw(event: Render3DEvent) {
        this.refreshRule()
        when (mode.get().lowercase()) {
            "espbox" -> {
                for (m in murderer) RenderUtils.drawEntityBox(m, Color(255, 0, 0), false, true, boxWidth.get())
                for (d in detective) RenderUtils.drawEntityBox(d, Color(0, 0, 255), false, true, boxWidth.get())
                for (p in player) RenderUtils.drawEntityBox(p, Color(0, 255, 0), false, true, boxWidth.get())
            }
            "tracers" -> {
                val drawTracers: (EntityPlayer) -> Unit = {
                    if (murderer.contains(it)) this.drawTraces(it, Color(255, 0, 0))
                    if (detective.contains(it)) this.drawTraces(it, Color(0, 0, 255))
                    if (player.contains(it)) this.drawTraces(it, Color(0, 255, 0))
                }
                for (ps in this.allPlayers) drawTracers(ps)
            }
        }
    }

    private fun refreshRule() {
        this.allPlayers = mc.theWorld.loadedEntityList.filterIsInstance<EntityPlayer>().toCollection(ArrayList())
        for (e in this.allPlayers) {
            val inv: InventoryPlayer = e.inventory
            val has: (Item) -> Boolean = { item -> inv.mainInventory.any { it.item == item } }

            if (has(Items.diamond_sword)) if (!murderer.contains(e)) murderer.add(e)
            if (has(Items.bow) && !has(Items.diamond_sword)) if (!detective.contains(e)) detective.add(e)
            if (!player.contains(e)) player.add(e)
        }
    }

    // from net.ccbluex.liquidbounce.features.module.modules.render.Tracers
    private fun drawTraces(entity: Entity, color: Color) {
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glEnable(GL11.GL_LINE_SMOOTH)
        GL11.glLineWidth(thickness.get())
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_DEPTH_TEST)
        GL11.glDepthMask(false)

        GL11.glBegin(GL11.GL_LINES)

        val x = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks -
                mc.renderManager.renderPosX)
        val y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks -
                mc.renderManager.renderPosY)
        val z = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks -
                mc.renderManager.renderPosZ)

        val eyeVector = Vec3(0.0, 0.0, 1.0)
            .rotatePitch((-Math.toRadians(mc.thePlayer.rotationPitch.toDouble())).toFloat())
            .rotateYaw((-Math.toRadians(mc.thePlayer.rotationYaw.toDouble())).toFloat())

        RenderUtils.glColor(color)

        GL11.glVertex3d(eyeVector.xCoord, mc.thePlayer.getEyeHeight().toDouble() + eyeVector.yCoord, eyeVector.zCoord)
        GL11.glVertex3d(x, y, z)
        GL11.glVertex3d(x, y, z)
        GL11.glVertex3d(x, y + entity.height, z)
    }
}
