
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;

@Mixin(GuiButton.class)
public abstract class MixinGuiButton extends Gui {
   public int alpha;
   public int cs;
   @Shadow
   public int xPosition;

   @Shadow
   public int yPosition;

   @Shadow
   public int width;

   @Shadow
   public int height;

   @Shadow
   protected boolean hovered;

   @Shadow
   public boolean enabled;

   @Shadow
   public boolean visible;

   @Shadow
   protected abstract void mouseDragged(Minecraft mc, int mouseX, int mouseY);

   @Shadow
   public String displayString;

   private double animation = 0.0;
   private long lastUpdate=System.currentTimeMillis();

   /**
    * @author liuli
    */
//   @Overwrite
//   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
//      if(!visible)
//         return;
//
//      this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
//      this.mouseDragged(mc, mouseX, mouseY);
//      long time=System.currentTimeMillis();
//      double pct=(time-lastUpdate)/500D;
//
//      if (this.hovered) {
//         if(animation<1){
//            animation+=pct;
//         }
//         if(animation>1){
//            animation=1;
//         }
//      } else {
//         if(animation>0){
//            animation-=pct;
//         }
//         if(animation<0){
//            animation=0;
//         }
//      }
//
//      double percent = EaseUtils.easeInOutQuad(animation);
//      RenderUtils.drawRect(this.xPosition,this.yPosition,this.xPosition + width,this.yPosition + height, new Color(31,31,31,150).getRGB());
//      double half=this.width / 2.0;
//      double center=this.xPosition + half;
//      if(enabled)
//         RenderUtils.drawRect(center - percent*(half), this.yPosition + this.height - 1, center + percent*(half), this.yPosition + this.height, Color.WHITE.getRGB());
//      mc.fontRendererObj.drawString(this.displayString, this.xPosition + this.width / 2F - mc.fontRendererObj.getStringWidth(this.displayString) / 2F
//              ,this.yPosition+this.height/2F-Fonts.font40.getHeight()/2F+1, enabled?Color.WHITE.getRGB():Color.GRAY.getRGB(), false);
//      lastUpdate=time;
//   }
   /**
    * @author Popcorn
    * */
   @Overwrite
   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
      if (this.visible) {
         FontRenderer var4 = mc.fontRendererObj;
         this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width
                 && mouseY < this.yPosition + this.height);
         updatefade();
         if (this.hovered) {
            if (this.cs >= 4)
               this.cs = 4;
            this.cs++;
         } else {
            if (this.cs <= 0)
               this.cs = 0;
            this.cs--;
         }
         if (this.enabled) {
            int color = new Color(0, 0, 0, this.alpha / 255.0F).getRGB();
            int radius = 5;
            Gui.drawRect(xPosition + radius, yPosition + radius, xPosition + width - radius,
                    yPosition + height - radius, color);
            Gui.drawRect(xPosition, yPosition + radius, xPosition + radius, yPosition + height - radius, color);
            Gui.drawRect(xPosition + width - radius, yPosition + radius, xPosition + width,
                    yPosition + height - radius, color);
            Gui.drawRect(xPosition + radius, yPosition, xPosition + width - radius, yPosition + radius, color);
            Gui.drawRect(xPosition + radius, yPosition + height - radius, xPosition + width - radius,
                    yPosition + height, color);
            drawFilledCircle(xPosition + radius, yPosition + radius, radius, color, 1);
            drawFilledCircle(xPosition + radius, yPosition + height - radius, radius, color, 2);
            drawFilledCircle(xPosition + width - radius, yPosition + radius, radius, color, 3);
            drawFilledCircle(xPosition + width - radius, yPosition + height - radius, radius, color, 4);
//                Gui.drawRect((this.xPosition + this.cs), this.yPosition, (this.xPosition + this.width - this.cs), (this.yPosition + this.height), col1);
//                RenderUtil.drawGradientSideways((this.xPosition + this.cs), (this.yPosition + this.height - 1), (this.xPosition + this.width) - this.width / 2, (this.yPosition + this.height), new Color(255,120,255,255).getRGB(), new Color(120,120,255,255).getRGB());
//                RenderUtil.drawGradientSideways((this.xPosition) + this.width / 2, (this.yPosition + this.height - 1), (this.xPosition + this.width - this.cs), (this.yPosition + this.height), new Color(120,120,255,255).getRGB(), new Color(255,120,255,255).getRGB());
         } else {
            int color = new Color(70, 70, 70, 180).getRGB();
            int radius = 5;
            drawRect(xPosition + radius, yPosition + radius, xPosition + width - radius,
                    yPosition + height - radius, color);
            drawRect(xPosition, yPosition + radius, xPosition + radius, yPosition + height - radius, color);
            drawRect(xPosition + width - radius, yPosition + radius, xPosition + width, yPosition + height - radius,
                    color);
            drawRect(xPosition + radius, yPosition, xPosition + width - radius, yPosition + radius, color);
            drawRect(xPosition + radius, yPosition + height - radius, xPosition + width - radius,
                    yPosition + height, color);
            drawFilledCircle(xPosition + radius, yPosition + radius, radius, color, 1);
            drawFilledCircle(xPosition + radius, yPosition + height - radius, radius, color, 2);
            drawFilledCircle(xPosition + width - radius, yPosition + radius, radius, color, 3);
            drawFilledCircle(xPosition + width - radius, yPosition + height - radius, radius, color, 4);
//                Gui.drawRect(this.xPosition, this.yPosition, (this.xPosition + this.width), (this.yPosition + this.height), (new Color(0.5F, 0.5F, 0.5F, 0.5F)).hashCode());
//                RenderUtil.drawGradientSideways(this.xPosition, this.yPosition + this.height - 1, (this.xPosition + this.width) - this.width / 2, (this.yPosition + this.height), new Color(255,120,60,255).getRGB(), new Color(255,0,0,255).getRGB());
//                RenderUtil.drawGradientSideways((this.xPosition) + this.width / 2, this.yPosition + this.height - 1, this.xPosition + this.width, (this.yPosition + this.height), new Color(255,0,0,255).getRGB(), new Color(255,120,60,255).getRGB());
         }
         mouseDragged(mc, mouseX, mouseY);
         int var5 = new Color(255, 255, 255, 255).getRGB();
         drawCenteredString(var4, StringUtils.stripControlCodes(this.displayString), this.xPosition + this.width / 2,
                 this.yPosition + (this.height - 5) / 2 - 2, var5);
      }
   }
   public void drawFilledCircle(double x, double y, double r, int c, int id) {
      float f = (float) (c >> 24 & 0xff) / 255F;
      float f1 = (float) (c >> 16 & 0xff) / 255F;
      float f2 = (float) (c >> 8 & 0xff) / 255F;
      float f3 = (float) (c & 0xff) / 255F;
      glEnable(GL_BLEND);
      glDisable(GL_TEXTURE_2D);
      glColor4f(f1, f2, f3, f);
      glBegin(GL_POLYGON);
      if (id == 1) {
         glVertex2d(x, y);
         for (int i = 0; i <= 90; i++) {
            double x2 = Math.sin((i * 3.141526D / 180)) * r;
            double y2 = Math.cos((i * 3.141526D / 180)) * r;
            glVertex2d(x - x2, y - y2);
         }
      } else if (id == 2) {
         glVertex2d(x, y);
         for (int i = 90; i <= 180; i++) {
            double x2 = Math.sin((i * 3.141526D / 180)) * r;
            double y2 = Math.cos((i * 3.141526D / 180)) * r;
            glVertex2d(x - x2, y - y2);
         }
      } else if (id == 3) {
         glVertex2d(x, y);
         for (int i = 270; i <= 360; i++) {
            double x2 = Math.sin((i * 3.141526D / 180)) * r;
            double y2 = Math.cos((i * 3.141526D / 180)) * r;
            glVertex2d(x - x2, y - y2);
         }
      } else if (id == 4) {
         glVertex2d(x, y);
         for (int i = 180; i <= 270; i++) {
            double x2 = Math.sin((i * 3.141526D / 180)) * r;
            double y2 = Math.cos((i * 3.141526D / 180)) * r;
            glVertex2d(x - x2, y - y2);
         }
      } else {
         for (int i = 0; i <= 360; i++) {
            double x2 = Math.sin((i * 3.141526D / 180)) * r;
            double y2 = Math.cos((i * 3.141526D / 180)) * r;
            glVertex2f((float) (x - x2), (float) (y - y2));
         }
      }
      glEnd();
      glEnable(GL_TEXTURE_2D);
      glDisable(GL_BLEND);
   }
   private void updatefade() {
      if (this.enabled)
         if (this.hovered) {
            this.alpha += 25;
            if (this.alpha >= 210)
               this.alpha = 210;
         } else {
            this.alpha -= 25;
            if (this.alpha <= 120)
               this.alpha = 120;
         }
   }
}