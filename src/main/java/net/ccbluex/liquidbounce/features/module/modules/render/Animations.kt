package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue


@ModuleInfo(name = "Animations", category = ModuleCategory.RENDER)
class Animations : Module() {
    val presetValue = ListValue(
        "Preset", arrayOf(
            "Akrien", "Avatar", "ETB", "Exhibition", "Push", "Reverse",
            "Shield", "SigmaNew", "SigmaOld", "Slide", "SlideDown", "HSlide", "Swong", "VisionFX",
            "Swank", "Jello", "None","Rotate"
        ),
        "SlideDown"
    )
package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(name = "Animations", description = "Render items Animations", category = ModuleCategory.RENDER)
public class Animations extends Module {
    public String getTag() {
        return Animations.Sword.get();
    }

    //some ListValue
    public static final ListValue Sword = new ListValue("Preset Animation Sword", new String[]{"Normal","Exhibition","Swank","SlideDown1", "SlideDown2" , "Slide", "Minecraft",
            "Avatar", "Tap1", "Tap2", "Poke", "Push1", "Push2" , "Up" , "Shield", "Akrien", "VisionFX", "Swong",
            "SigmaOld", "ETB", "Lucky", "Rotate360", "SmoothFloat", "Strange" , "Reverse", "Zoom", "Move", "Stab", "Jello"}, "Exhibition");

    public static final ListValue transformFirstPersonRotate = new ListValue("TransformFirstPersonRotate", new String[]{"Rotate1", "Rotate2", "Custom" , "None"}, "Rotate1");

    //item hold in hand incompatible. idk why?
    public static final ListValue doBlockTransformationsRotate = new ListValue("DoBlockTransformationsRotate", new String[]{"Rotate1", "Rotate2", "Custom" , "None"}, "None");

    //modify swingSpeed animation.mode custom will break swing
    public static final ListValue swingMethod = new ListValue("SwingMethod", new String[]{"Swing", "Cancel", "Default"}, "Default");

    //swing cancel
    public static final ListValue swingCancelMode = new ListValue("NoSwingMode", new String[]{"ServerSide", "Default"}, "Default");


    //change Position Blocking Sword
    public static final FloatValue blockPosX = new FloatValue("BlockPosX", 0f, -1f, 1f);
    public static final FloatValue blockPosY = new FloatValue("BlockPosY", 0f, -1f, 1f);
    public static final FloatValue blockPosZ = new FloatValue("BlockPosZ", 0f, -1f, 1f);

    //custom item rotate (idk why i made this)
    public static final IntegerValue customRotate1 = new IntegerValue("CustomRotate1", 0, -360, 360);
    public static final IntegerValue customRotate2 = new IntegerValue("CustomRotate2", 0, -360, 360);
    public static final IntegerValue customRotate3 = new IntegerValue("CustomRotate3", 0, -360, 360);

    //scale all items
    public static final FloatValue Scale = new FloatValue("Scale", 0.4f, 0f, 4f);

    //modify item swing and rotate
    public static final BoolValue RotateItems = new BoolValue("RotateItems", false);
    public static final FloatValue SpeedRotate = new FloatValue("SpeedRotate", 1f, 0f, 10f);
    public static final IntegerValue SpeedSwing = new IntegerValue("SpeedSwing", 4, 0, 20);

    //custom animation sword
    public static final FloatValue mcSwordPos =  new FloatValue("MCSwordPos", 0.45f, 0, 0.5f);

    //idk why i add this. XD. but it is fun right :)
    public static final BoolValue fakeBlock = new BoolValue("FakeBlock",false);

    //some stuff i cannot add in actual modules
    public static final FloatValue bobbing = new FloatValue("Bobbing", 0.3f, 0.3f, 10F);


}




    val modeValue = ListValue("InvMode", arrayOf("None", "Slide", "Zoom"), "Slide")
    val timeValue = IntegerValue("InvTime",500,100,500).displayable { !modeValue.get().equals("None",true) }
    val translateX = FloatValue("TranslateX", 0.0f, 0.0f, 1.5f)
    val translateY = FloatValue("TranslateY", 0.0f, 0.0f, 0.5f)
    val translateZ = FloatValue("TranslateZ", 0.0f, 0.0f, -2.0f)
    val itemPosX = FloatValue("ItemPosX", 0.56F, -1.0F, 1.0F)
    val itemPosY = FloatValue("ItemPosY", -0.52F, -1.0F, 1.0F)
    val itemPosZ = FloatValue("ItemPosZ", -0.71999997F, -1.0F, 1.0F)
    val itemScale = FloatValue("ItemScale", 0.4f, 0.0f, 2.0f)
    val swingAnim = BoolValue("SwingAnim",false)
    val swingSpeed = FloatValue("SwingSpeed", 1f, 0.5f, 5.0f)

    override val tag: String
        get() = presetValue.get()
}
