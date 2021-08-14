/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.utils.render.GLUtils;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.ModelUpdateEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.util.Map;
import java.util.WeakHashMap;

@ModuleInfo(
        name = "SSkeltal",
        description = "Skeltal.",
        category = ModuleCategory.RENDER
)
public final class SpookySkeltal
extends Module {
    private final Map<EntityPlayer, float[][]> playerRotationMap = new WeakHashMap<>();
    public final BoolValue smoothLinesValue = new BoolValue("SmoothLines", true);

    @EventTarget
    public final void onModelUpdate(ModelUpdateEvent event) {
        ModelPlayer model = event.getModelPlayer();
        this.playerRotationMap.put(event.getPlayer(), new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
    }

    @EventTarget
    public final void onRender(Render3DEvent event) {
        this.setupRender(true);
        GL11.glEnable(2903);
        GL11.glDisable(2848);
        this.playerRotationMap.keySet().removeIf(this::contains);
        Map<EntityPlayer, float[][]> playerRotationMap = this.playerRotationMap;
        EntityPlayer[] players = (EntityPlayer[]) playerRotationMap.keySet().toArray();
        for (EntityPlayer player : players) {
            float[][] entPos = playerRotationMap.get(player);
            if (entPos == null || player.getEntityId() == -1488 || !player.isEntityAlive() || !RenderUtils.isInViewFrustrum(player) || player.isDead || player == mc.thePlayer || player.isPlayerSleeping() || player.isInvisible())
                continue;
            GL11.glPushMatrix();
            float[][] modelRotations = playerRotationMap.get(player);
            GL11.glLineWidth(1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            double x = GLUtils.interpolate(player.posX, player.lastTickPosX, event.getPartialTicks()) - mc.getRenderManager().renderPosX;
            double y = GLUtils.interpolate(player.posY, player.lastTickPosY, event.getPartialTicks()) - mc.getRenderManager().renderPosY;
            double z = GLUtils.interpolate(player.posZ, player.lastTickPosZ, event.getPartialTicks()) - mc.getRenderManager().renderPosZ;
            GL11.glTranslated(x, y, z);
            float bodyYawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * mc.timer.renderPartialTicks;
            GL11.glRotatef( (-bodyYawOffset),0.0f, 1.0f,0.0f);
            GL11.glTranslated(0.0,  0.0,  (player.isSneaking() ? -0.235 : 0.0));
            float legHeight = player.isSneaking() ? 0.6f : 0.75f;
            float rad = 57.29578f;
            GL11.glPushMatrix();
            GL11.glTranslated(-0.125, legHeight, 0.0);
            if (modelRotations[3][0] != 0.0f) {
                GL11.glRotatef((modelRotations[3][0] * 57.29578f), 1.0f, 0.0f, 0.0f);
            }
            if (modelRotations[3][1] != 0.0f) {
                GL11.glRotatef((modelRotations[3][1] * 57.29578f), 0.0f, 1.0f, 0.0f);
            }
            if (modelRotations[3][2] != 0.0f) {
                GL11.glRotatef( (modelRotations[3][2] * 57.29578f), 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, (-legHeight), 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.125, legHeight, 0.0);
            if (modelRotations[4][0] != 0.0f) {
                GL11.glRotatef((modelRotations[4][0] * 57.29578f), 1.0f, 0.0f, 0.0f);
            }
            if (modelRotations[4][1] != 0.0f) {
                GL11.glRotatef((modelRotations[4][1] * 57.29578f), 0.0f, 1.0f, 0.0f);
            }
            if (modelRotations[4][2] != 0.0f) {
                GL11.glRotatef((modelRotations[4][2] * 57.29578f), 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, (-legHeight), 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glTranslated(0.0, 0.0, (player.isSneaking() ? 0.25 : 0.0));
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, (player.isSneaking() ? -0.05 : 0.0), (player.isSneaking() ? -0.01725 : 0.0));
            GL11.glPushMatrix();
            GL11.glTranslated(-0.375, (legHeight + 0.55), 0.0);
            if (modelRotations[1][0] != 0.0f) {
                GL11.glRotatef((modelRotations[1][0] * 57.29578f), 1.0f, 0.0f, 0.0f);
            }
            if (modelRotations[1][1] != 0.0f) {
                GL11.glRotatef((modelRotations[1][1] * 57.29578f), 0.0f, 1.0f, 0.0f);
            }
            if (modelRotations[1][2] != 0.0f) {
                GL11.glRotatef((-modelRotations[1][2] * 57.29578f), 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -0.5, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.375, (legHeight + 0.55), 0.0);
            if (modelRotations[2][0] != 0.0f) {
                GL11.glRotatef((modelRotations[2][0] * 57.29578f), 1.0f, 0.0f, 0.0f);
            }
            if (modelRotations[2][1] != 0.0f) {
                GL11.glRotatef((modelRotations[2][1] * 57.29578f), 0.0f, 1.0f, 0.0f);
            }
            if (modelRotations[2][2] != 0.0f) {
                GL11.glRotatef((-modelRotations[2][2] * 57.29578f), 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -0.5, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glRotatef((bodyYawOffset - player.rotationYawHead), 0.0f, 1.0f, 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, (legHeight + 0.55), 0.0);
            if (modelRotations[0][0] != 0.0f) {
                GL11.glRotatef((modelRotations[0][0] * 57.29578f), 1.0f, 0.0f, 0.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.3, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glRotatef((player.isSneaking() ? 25.0f : 0.0f), 1.0f, 0.0f, 0.0f);
            GL11.glTranslated(0.0, (player.isSneaking() ? -0.16175 : 0.0), (player.isSneaking() ? -0.48025 : 0.0));
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, legHeight, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.125, 0.0, 0.0);
            GL11.glVertex3d(0.125, 0.0, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, legHeight, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.55, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, (legHeight + 0.55), 0.0);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.375, 0.0, 0.0);
            GL11.glVertex3d(0.375, 0.0, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
        this.setupRender(false);
    }

    private void setupRender(boolean start) {
        boolean smooth = this.smoothLinesValue.get();
        if (start) {
            if (smooth) {
                GLUtils.startSmooth();
            } else {
                GL11.glDisable(2848);
            }
            GL11.glDisable(2929);
            GL11.glDisable(3553);
        } else {
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            if (smooth) {
                GLUtils.endSmooth();
            }
        }
        GL11.glDepthMask(!start);
    }

    private boolean contains(EntityPlayer entityPlayer) {
        return !mc.theWorld.playerEntities.contains(entityPlayer);
    }
}
