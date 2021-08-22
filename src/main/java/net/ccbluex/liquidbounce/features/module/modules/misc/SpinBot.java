package net.ccbluex.liquidbounce.features.module.modules.misc;

import net.ccbluex.liquidbounce.features.module.*;
import net.ccbluex.liquidbounce.value.*;
import net.ccbluex.liquidbounce.event.*;
import org.jetbrains.annotations.*;

@ModuleInfo(name = "SpinBot", category = ModuleCategory.MISC)
public class SpinBot extends Module
{
    public final ListValue yawMode;
    public final ListValue pitchMode;
    private final IntegerValue YawSet;
    private final IntegerValue PitchSet;
    private final IntegerValue YawJitterTimer;
    private final IntegerValue PitchJitterTimer;
    private final IntegerValue YawSpin;
    public static float pitch;
    public static float lastSpin;
    public static float yawTimer;
    public static float pitchTimer;
    
    public SpinBot() {
        this.yawMode = new ListValue("Yaw", new String[] { "Static", "Offset", "Random", "Jitter", "Spin", "Off" }, "Offset");
        this.pitchMode = new ListValue("Pitch", new String[] { "Static", "Offset", "Random", "Jitter", "Off" }, "Offset");
        this.YawSet = new IntegerValue("YawSet", 0, -180, 180);
        this.PitchSet = new IntegerValue("PitchSet", 0, -180, 180);
        this.YawJitterTimer = new IntegerValue("YawJitterTimer", 1, 1, 40);
        this.PitchJitterTimer = new IntegerValue("PitchJitterTimer", 1, 1, 40);
        this.YawSpin = new IntegerValue("YawSpin", 5, -50, 50);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        pitch = -4.9531336E7f;
        lastSpin = 0.0f;
        yawTimer = 0.0f;
        pitchTimer = 0.0f;
    }
    
    @EventTarget
    public void onTick(final TickEvent e) {
        final String s = this.yawMode.get();
        float yaw = 0.0f;
        switch (s) {
            case "Static": {
                yaw = this.YawSet.get();
                break;
            }
            case "Offset": {
                yaw = mc.thePlayer.rotationYaw + this.YawSet.get();
                break;
            }
            case "Random": {
                yaw = (float)Math.floor(Math.random() * 360.0 - 180.0);
                break;
            }
            case "Jitter": {
                ++yawTimer;
                if (yawTimer % (this.YawJitterTimer.get() * 2) >= this.YawJitterTimer.get()) {
                    yaw = mc.thePlayer.rotationYaw;
                    break;
                }
                yaw = mc.thePlayer.rotationYaw - 180.0f;
                break;
            }
            case "Spin": {
                yaw = (lastSpin += this.YawSpin.get());
                break;
            }
            default: {
                yaw = this.YawSet.get();
                break;
            }
        }
        if (!this.yawMode.get().equalsIgnoreCase("off")) {
            mc.thePlayer.renderYawOffset = yaw;
            mc.thePlayer.rotationYawHead = yaw;
        }
        final String s2 = this.pitchMode.get();
        switch (s2) {
            case "Static": {
                pitch = this.PitchSet.get();
                break;
            }
            case "Offset": {
                pitch = mc.thePlayer.rotationPitch + this.PitchSet.get();
                break;
            }
            case "Random": {
                pitch = (float)Math.floor(Math.random() * 180.0 - 90.0);
                break;
            }
            case "Jitter": {
                ++pitchTimer;
                if (pitchTimer % (this.PitchJitterTimer.get() * 2) >= this.PitchJitterTimer.get()) {
                    pitch = 90.0f;
                    break;
                }
                pitch = -90.0f;
                break;
            }
            default: {
                pitch = this.PitchSet.get();
                break;
            }
        }
        if (this.pitchMode.get().equalsIgnoreCase("off")) {
            pitch = -4.9531336E7f;
        }
    }
    
    @Nullable
    @Override
    public String getTag() {
        return "Yaw " + this.yawMode.get() + ", Pitch " + this.pitchMode.get();
    }
    
    static {
        pitch = -4.9531336E7f;
    }
}
