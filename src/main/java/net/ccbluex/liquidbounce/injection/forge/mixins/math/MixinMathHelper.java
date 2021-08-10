/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.math;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.client.BetterFPS;
import net.ccbluex.liquidbounce.features.betterfps.BetterFPSCore;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MathHelper.class)
public class MixinMathHelper {
    private static BetterFPS betterFPS = null;
    
    @Inject(method = "sin", at = @At("HEAD"), cancellable = true)
    private static void sin(float value, CallbackInfoReturnable<Float> callbackInfoReturnable){
        if(LiquidBounce.INSTANCE.isStarting())
            return;

        if(betterFPS==null) betterFPS= LiquidBounce.moduleManager.getModule(BetterFPS.class);

        BetterFPSCore core = LiquidBounce.betterFPSCore;

        switch (betterFPS.getSinMode().get().toLowerCase()){
            case "taylor": {
                callbackInfoReturnable.setReturnValue(core.getTaylor().sin(value));
                break;
            }
            case "libgdx": {
                callbackInfoReturnable.setReturnValue(core.getLibGDX().sin(value));
                break;
            }
            case "rivensfull": {
                callbackInfoReturnable.setReturnValue(core.getRivensFull().sin(value));
                break;
            }
            case "rivenshalf": {
                callbackInfoReturnable.setReturnValue(core.getRivensHalf().sin(value));
                break;
            }
            case "rivens": {
                callbackInfoReturnable.setReturnValue(core.getRivens().sin(value));
                break;
            }
            case "java": {
                callbackInfoReturnable.setReturnValue((float) Math.sin(value));
                break;
            }
            case "1.16": {
                callbackInfoReturnable.setReturnValue(core.getNewMC().sin(value));
                break;
            }
        }
    }

    @Inject(method = "cos", at = @At("HEAD"), cancellable = true)
    private static void cos(float value, CallbackInfoReturnable<Float> callbackInfoReturnable){
        if(LiquidBounce.INSTANCE.isStarting())
            return;

        if(betterFPS==null) betterFPS= LiquidBounce.moduleManager.getModule(BetterFPS.class);

        BetterFPSCore core = LiquidBounce.betterFPSCore;
        switch (betterFPS.getCosMode().get().toLowerCase()){
            case "taylor": {
                callbackInfoReturnable.setReturnValue(core.getTaylor().cos(value));
                break;
            }
            case "libgdx": {
                callbackInfoReturnable.setReturnValue(core.getLibGDX().cos(value));
                break;
            }
            case "rivensfull": {
                callbackInfoReturnable.setReturnValue(core.getRivensFull().cos(value));
                break;
            }
            case "rivenshalf": {
                callbackInfoReturnable.setReturnValue(core.getRivensHalf().cos(value));
                break;
            }
            case "rivens": {
                callbackInfoReturnable.setReturnValue(core.getRivens().cos(value));
                break;
            }
            case "java": {
                callbackInfoReturnable.setReturnValue((float) Math.cos(value));
                break;
            }
            case "1.16": {
                callbackInfoReturnable.setReturnValue(core.getNewMC().cos(value));
                break;
            }
        }
    }
}
