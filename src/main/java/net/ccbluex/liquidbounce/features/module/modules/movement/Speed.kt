/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.movement

import net.ccbluex.liquidbounce.event.*
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.utils.ReflectUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import org.lwjgl.input.Keyboard

@ModuleInfo(name = "Speed", category = ModuleCategory.MOVEMENT, autoDisable = EnumAutoDisableType.FLAG, keyBind = Keyboard.KEY_V)
class Speed : Module() {
    val modes=ReflectUtils.getReflects("${this.javaClass.`package`.name}.speeds",SpeedMode::class.java)
        .map { it.newInstance() as SpeedMode }
        .sortedBy { it.modeName }

    val mode: SpeedMode
        get() = modes.filter { it.modeName.equals(modeValue.get(),true) }[0]

    val modeValue: ListValue = object : ListValue("Mode", modes.map { it.modeName }.toTypedArray(), "NCPBHop") {
        override fun onChange(oldValue: String, newValue: String) {
            if (state) onDisable()
        }

        override fun onChanged(oldValue: String, newValue: String) {
            if (state) onEnable()
        }
    }

    val customSpeedValue = FloatValue("CustomSpeed", 1.6f, 0.2f, 2f).displayable { modeValue.equals("Custom") }
    val customLaunchSpeedValue = FloatValue("CustomLaunchSpeed", 1.6f, 0.2f, 2f).displayable { modeValue.equals("Custom") }
    val customAddYMotionValue = FloatValue("CustomAddYMotion", 0f, 0f, 2f).displayable { modeValue.equals("Custom") }
    val customYValue = FloatValue("CustomY", 0f, 0f, 4f).displayable { modeValue.equals("Custom") }
    val customUpTimerValue = FloatValue("CustomUpTimer", 1f, 0.1f, 2f).displayable { modeValue.equals("Custom") }
    val customDownTimerValue = FloatValue("CustomDownTimer", 1f, 0.1f, 2f).displayable { modeValue.equals("Custom") }
    val customStrafeValue = ListValue("CustomStrafe", arrayOf("Strafe","Boost","Plus","PlusOnlyUp","Non-Strafe"),"Boost").displayable { modeValue.equals("Custom") }
    val customGroundStay = IntegerValue("CustomGroundStay",0,0,10).displayable { modeValue.equals("Custom") }
    val groundResetXZValue = BoolValue("CustomGroundResetXZ", false).displayable { modeValue.equals("Custom") }
    val resetXZValue = BoolValue("CustomResetXZ", false).displayable { modeValue.equals("Custom") }
    val resetYValue = BoolValue("CustomResetY", false).displayable { modeValue.equals("Custom") }
    val launchSpeedValue = BoolValue("CustomDoLaunchSpeed", true).displayable { modeValue.equals("Custom") }
    val noWater = BoolValue("NoWater", true)
    val portMax = FloatValue("AACPort-Length", 1F, 1F, 20F).displayable { modeValue.equals("AACPort") }
    val redeSkyHopGSpeed = FloatValue("RedeSkyHop-GSpeed", 0.3f, 0.1f, 0.7f).displayable { modeValue.equals("RedeSkyHop") }
    val redeSkyHeight = FloatValue("RedeSkyHeight", 0.45f, 0.30f, 0.55f).displayable { modeValue.contains("RedeSkyHop") }
    val redeSkyHopTimer = FloatValue("RedeSkyHop-Timer", 6f, 1.1f, 10f).displayable { modeValue.contains("RedeSkyHop") }
    val redeSkyHop3Speed = FloatValue("RedeSkyHop3-Speed", 0.07f, 0.01f, 0.1f).displayable { modeValue.equals("RedeSkyHop3") }
    val aacGroundTimerValue = FloatValue("AACGround-Timer", 3f, 1.1f, 10f).displayable { modeValue.equals("AACGround") }
    val cubecraftPortLengthValue = FloatValue("TeleportCubeCraft-PortLength", 1f, 0.1f, 2f).displayable { modeValue.equals("TeleportCubeCraft") }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (mc.thePlayer.isSneaking || (mc.thePlayer.isInWater() &&noWater.get())) return
        if (MovementUtils.isMoving()) mc.thePlayer.isSprinting = true
        mode.onUpdate()
    }

    @EventTarget
    fun onMotion(event: MotionEvent) {
        mode.onMotion(event)
        if (mc.thePlayer.isSneaking || event.eventState !== EventState.PRE || (mc.thePlayer.isInWater() &&noWater.get())) return
        if (MovementUtils.isMoving()) mc.thePlayer.isSprinting = true
        mode.onPreMotion()
    }

    @EventTarget
    fun onMove(event: MoveEvent) {
        if (mc.thePlayer.isSneaking || (mc.thePlayer.isInWater() &&noWater.get())) return
        mode.onMove(event)
    }

    @EventTarget
    fun onTick(event: TickEvent) {
        if (mc.thePlayer.isSneaking || (mc.thePlayer.isInWater() &&noWater.get())) return
        mode.onTick()
    }

    @EventTarget
    fun onJump(event: JumpEvent) {
        if(mode.noJump)
            event.cancelEvent()
    }

    override fun onEnable() {
        if (mc.thePlayer == null) return
        mc.timer.timerSpeed = 1f
        mode.onEnable()
    }

    override fun onDisable() {
        if (mc.thePlayer == null) return
        mc.timer.timerSpeed = 1f
        mode.onDisable()
    }

    override val tag: String
        get() = modeValue.get()
}
