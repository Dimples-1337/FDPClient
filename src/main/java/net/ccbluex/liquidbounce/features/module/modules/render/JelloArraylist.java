/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.MathHelper;
import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.opengl.GL11;
import java.util.*;
import java.util.stream.Collectors;
import java.awt.Color;

@ModuleInfo(name = "JelloArraylist", category = ModuleCategory.RENDER)
public class JelloArraylist extends Module {
    ResourceLocation wtf = new ResourceLocation("fdpclient/arraylistshadow.png");
    List<Module> modules = new ArrayList<Module>();

    private final BoolValue useTrueFont = new BoolValue("Use-TrueFont", true);
    private final IntegerValue animateSpeed = new IntegerValue("Animate-Speed", 5, 1, 20);

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        this.updateElements(event.getPartialTicks()); 
        this.renderArraylist();
    }

    public void updateElements(float partialTicks) {
        modules = LiquidBounce.moduleManager.getModules()
        .stream()
        .filter(mod -> mod.getArray() && !mod.getName().equalsIgnoreCase("JelloArraylist"))
        .sorted(new ModComparator())
        .collect(Collectors.toCollection(ArrayList::new));

        float tick = 1F - partialTicks;

        for (Module module : modules) {
            module.setAnimation(module.getAnimation() + ((module.getState() ? animateSpeed.get() : -animateSpeed.get()) * tick));
            module.setAnimation(MathHelper.clamp_float(module.getAnimation(), 0F, 20F));
        }
    }

    public void renderArraylist() { 
        ScaledResolution sr = new ScaledResolution(mc);
        float yStart = 1;
        float xStart = 0;

        for (Module module : modules) {
            if (module.getAnimation() <= 0F) continue;
            xStart = (float) (sr.getScaledWidth() - Fonts.fontSFUI40.getStringWidth(module.getName()) - 5);

            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            RenderUtils.drawImage3(wtf, xStart - 8 - 2 - 1, yStart + 2 - 2.5f - 1.5f - 1.5f - 1.5f - 6 - 1, (int)(Fonts.fontSFUI40.getStringWidth(module.getName())*1 + 20 + 10), (int)(18.5 + 6 + 12 + 2), 1f, 1f, 1f, (module.getAnimation() / 20F) * 0.7f);
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();

            yStart += (7.5f + 5.25f) * (module.getAnimation() / 20F);
        }   

        yStart = 1;
        xStart = 0;

        for (Module module : modules) {
            if (module.getAnimation() <= 0F) continue;
            xStart = (float) (sr.getScaledWidth() - Fonts.fontSFUI40.getStringWidth(module.getName()) - 5);

            GlStateManager.pushMatrix();
            //GlStateManager.resetColor();
            if (useTrueFont.get()) {
                GlStateManager.disableAlpha();
            }
            Fonts.fontSFUI40.drawString(module.getName(), xStart, yStart + 7.5f, new Color(1F, 1F, 1F, (module.getAnimation() / 20F) * 0.7f).getRGB());
            if (useTrueFont.get()) {
                GlStateManager.enableAlpha();
            }
            GlStateManager.popMatrix();

            yStart += (7.5f + 5.25f) * (module.getAnimation() / 20F);
        }       

        GlStateManager.resetColor();
    }

    class ModComparator implements Comparator<Module> {
        @Override
        public int compare(Module e1, Module e2) {
            return (Fonts.fontSFUI40.getStringWidth(e1.getName()) < Fonts.fontSFUI40.getStringWidth(e2.getName()) ? 1 : -1);
        }
    }
}
