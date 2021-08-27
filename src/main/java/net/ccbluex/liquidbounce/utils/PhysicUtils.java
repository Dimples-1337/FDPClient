package net.ccbluex.liquidbounce.utils;
/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.utils;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

public class PhysicUtils {
    public static Random random = new Random();
    public static Minecraft mc = Minecraft.getMinecraft();
    public static RenderItem renderItem = mc.getRenderItem();
    public static long tick;
    public static double rotation;
    public static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    public static void doRenderItemPhysic(Entity par1Entity, double x, double y, double z, float par8, float par9) {
        EntityItem item;
        ItemStack itemstack;
        rotation = (double)(System.nanoTime() - tick) / 3000000.0 * 1.0;
        if (!PhysicUtils.mc.inGameHasFocus) {
            rotation = 0.0;
        }
        if ((itemstack = (item = (EntityItem)par1Entity).getEntityItem()).getItem() != null) {
            random.setSeed(187L);
            boolean flag = false;
            if (TextureMap.locationBlocksTexture != null) {
                PhysicUtils.mc.getRenderManager().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                PhysicUtils.mc.getRenderManager().renderEngine.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
                flag = true;
            }
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc((int)516, (float)0.1f);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
            GlStateManager.pushMatrix();
            IBakedModel ibakedmodel = renderItem.getItemModelMesher().getItemModel(itemstack);
            int i = PhysicUtils.func_177077_a(item, x, y - 0.1f, z, par9, ibakedmodel);
            BlockPos pos = new BlockPos((Entity)item);
            if (item.rotationPitch > 360.0f) {
                item.rotationPitch = 0.0f;
            }
            if (!(item == null || Double.isNaN((double)item.getAge()) || Double.isNaN((double)item.getAir()) || Double.isNaN((double)item.getEntityId()) || item.getPosition() == null)) {
                if (item.onGround) {
                    if (item.rotationPitch != 0.0f && item.rotationPitch != 90.0f && item.rotationPitch != 180.0f && item.rotationPitch != 270.0f) {
                        double Abstand0 = PhysicUtils.formPositiv(item.rotationPitch);
                        double Abstand90 = PhysicUtils.formPositiv(item.rotationPitch - 90.0f);
                        double Abstand180 = PhysicUtils.formPositiv(item.rotationPitch - 180.0f);
                        double Abstand270 = PhysicUtils.formPositiv(item.rotationPitch - 270.0f);
                        if (Abstand0 <= Abstand90 && Abstand0 <= Abstand180 && Abstand0 <= Abstand270) {
                            if (item.rotationPitch < 0.0f) {
                                EntityItem e1 = item;
                                e1.rotationPitch = (float)((double)e1.rotationPitch + rotation);
                            } else {
                                EntityItem e2 = item;
                                e2.rotationPitch = (float)((double)e2.rotationPitch - rotation);
                            }
                        }
                        if (Abstand90 < Abstand0 && Abstand90 <= Abstand180 && Abstand90 <= Abstand270) {
                            if (item.rotationPitch - 90.0f < 0.0f) {
                                EntityItem e3 = item;
                                e3.rotationPitch = (float)((double)e3.rotationPitch + rotation);
                            } else {
                                EntityItem e4 = item;
                                e4.rotationPitch = (float)((double)e4.rotationPitch - rotation);
                            }
                        }
                        if (Abstand180 < Abstand90 && Abstand180 < Abstand0 && Abstand180 <= Abstand270) {
                            if (item.rotationPitch - 180.0f < 0.0f) {
                                EntityItem e5 = item;
                                e5.rotationPitch = (float)((double)e5.rotationPitch + rotation);
                            } else {
                                EntityItem e6 = item;
                                e6.rotationPitch = (float)((double)e6.rotationPitch - rotation);
                            }
                        }
                        if (Abstand270 < Abstand90 && Abstand270 < Abstand180 && Abstand270 < Abstand0) {
                            if (item.rotationPitch - 270.0f < 0.0f) {
                                EntityItem e7 = item;
                                e7.rotationPitch = (float)((double)e7.rotationPitch + rotation);
                            } else {
                                EntityItem e8 = item;
                                e8.rotationPitch = (float)((double)e8.rotationPitch - rotation);
                            }
                        }
                    }
                } else {
                    BlockPos posUp = new BlockPos((Entity)item);
                    posUp.add(0, 0.2f, 0);
                    Material m1 = item.worldObj.getBlockState(posUp).getBlock().getMaterial();
                    Material m2 = item.worldObj.getBlockState(pos).getBlock().getMaterial();
                    boolean m3 = item.isInsideOfMaterial(Material.water);
                    boolean m4 = item.isInWater();
                    if (m3 | m1 == Material.water | m2 == Material.water | m4) {
                        EntityItem tmp748_746 = item;
                        tmp748_746.rotationPitch = (float)((double)tmp748_746.rotationPitch + rotation / 4.0);
                    } else {
                        EntityItem tmp770_768 = item;
                        tmp770_768.rotationPitch = (float)((double)tmp770_768.rotationPitch + rotation * 2.0);
                    }
                }
            }
            GL11.glRotatef((float)item.rotationYaw, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(item.rotationPitch + 90.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            int j = 0;
            while (j < i) {
                if (ibakedmodel.isAmbientOcclusion()) {
                    GlStateManager.pushMatrix();
                    GlStateManager.scale((float)0.5f, (float)0.5f, (float)0.5f);
                    renderItem.renderItem(itemstack, ibakedmodel);
                    GlStateManager.popMatrix();
                } else {
                    GlStateManager.pushMatrix();
                    if (j > 0 && PhysicUtils.shouldSpreadItems()) {
                        GlStateManager.translate((float)0.0f, (float)0.0f, (float)(0.046875f * (float)j));
                    }
                    renderItem.renderItem(itemstack, ibakedmodel);
                    if (!PhysicUtils.shouldSpreadItems()) {
                        GlStateManager.translate((float)0.0f, (float)0.0f, (float)0.046875f);
                    }
                    GlStateManager.popMatrix();
                }
                ++j;
            }
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            PhysicUtils.mc.getRenderManager().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            if (flag) {
                PhysicUtils.mc.getRenderManager().renderEngine.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
            }
        }
    }

    public static int func_177077_a(EntityItem item, double x, double y, double z, float p_177077_8_, IBakedModel p_177077_9_) {
        ItemStack itemstack = item.getEntityItem();
        Item item2 = itemstack.getItem();
        if (item2 == null) {
            return 0;
        }
        boolean flag = p_177077_9_.isAmbientOcclusion();
        int i = PhysicUtils.getModelCount(itemstack);
        float f1 = 0.25f;
        float f2 = 0.0f;
        GlStateManager.translate((float)((float)x), (float)((float)y + f2 + 0.25f), (float)((float)z));
        float f3 = 0.0f;
        if (flag || PhysicUtils.mc.getRenderManager().renderEngine != null && PhysicUtils.mc.gameSettings.fancyGraphics) {
            GlStateManager.rotate((float)f3, (float)0.0f, (float)1.0f, (float)0.0f);
        }
        if (!flag) {
            f3 = -0.0f * (float)(i - 1) * 0.5f;
            float f4 = -0.0f * (float)(i - 1) * 0.5f;
            float f5 = -0.046875f * (float)(i - 1) * 0.5f;
            GlStateManager.translate((float)f3, (float)f4, (float)f5);
        }
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        return i;
    }

    public static int getModelCount(ItemStack stack) {
        byte b0 = 1;
        if (stack.animationsToGo > 48) {
            b0 = 5;
        } else if (stack.animationsToGo > 32) {
            b0 = 4;
        } else if (stack.animationsToGo > 16) {
            b0 = 3;
        } else if (stack.animationsToGo > 1) {
            b0 = 2;
        }
        return b0;
    }

    public static byte getMiniBlockCount(ItemStack stack, byte original) {
        return original;
    }

    public static byte getMiniItemCount(ItemStack stack, byte original) {
        return original;
    }

    public static boolean shouldSpreadItems() {
        return true;
    }

    public static double formPositiv(float rotationPitch) {
        if (rotationPitch > 0.0f) {
            return rotationPitch;
        }
        return - rotationPitch;
    }

}
