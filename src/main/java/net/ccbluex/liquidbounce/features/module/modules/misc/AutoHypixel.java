package net.ccbluex.liquidbounce.features.module.modules.misc;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;

@ModuleInfo(name = "AutoHypixel", spacedName = "AutoHypixel", description = "Automatically send you into random games on Hypixel after you die or win.", category = ModuleCategory.MISC)
public class AutoHypixel extends Module {

    private final IntegerValue delayValue = new IntegerValue("Delay", 0, 0, 5000);
    private final BoolValue autoGGValue = new BoolValue("Auto-GG", true);
    private final BoolValue antiAtlasValue = new BoolValue("Anti-Atlas", true);
    private final BoolValue checkValue = new BoolValue("CheckGameMode", true);
    private final BoolValue renderValue = new BoolValue("Render", true);
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Solo", "Teams", "Ranked", "Mega"}, "Solo");
    private final ListValue soloTeamsValue = new ListValue("Solo/Teams-Mode", new String[]{"Normal", "Insane"}, "Insane");
    private final ListValue megaValue = new ListValue("Mega-Mode", new String[]{"Normal", "Doubles"}, "Normal");

    private final MSTimer timer = new MSTimer();
    public static String gameMode = "NONE";

    public boolean shouldChangeGame, useOtherWord = false;
    private final DecimalFormat dFormat = new DecimalFormat("0.0");

    private float posY = -20F;

    private final String[] strings = new String[]{
        "1st Killer - ", 
        "1st Place - ", 
        "You died! Want to play again? Click here!",
        "You won! Want to play again? Click here!",
        " - Damage Dealt - ",
        "1st - ",
        "Winning Team - ",
        "Winners: ", 
        "Winner: ", 
        "Winning Team: ", 
        " win the game!", 
        "1st Place: ", 
        "Last team standing!", 
        "Winner #1 (", 
        "Top Survivors", 
        "Winners - "};

    @Override
    public void onEnable() {
        shouldChangeGame = false;
        timer.reset();
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        if (checkValue.get() && !gameMode.toLowerCase().contains("skywars"))
            return;

        ScaledResolution sc = new ScaledResolution(mc);
        float middleX = sc.getScaledWidth() / 2F;
        String detail = "Sending you to another game in " + dFormat.format((float)timer.hasTimeLeft(delayValue.get()) / 1000F) + "s...";
        float middleWidth = Fonts.font40.getStringWidth(detail) / 2F;

        posY = AnimationUtils.animate(shouldChangeGame ? 10F : -20F, posY, 0.25F);
        if (!renderValue.get() || posY < -15)
            return;

        RenderUtils.drawRoundedRect(middleX - 5F - middleWidth, posY, middleX + 5F + middleWidth, posY + 15F, 3F, 0xA0000000);

        GlStateManager.resetColor();
        Fonts.fontSFUI40.drawString(detail, middleX - middleWidth - 1F, posY + 4F, -1);
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        if ((!checkValue.get() || gameMode.toLowerCase().contains("skywars")) && shouldChangeGame && timer.hasTimePassed(delayValue.get())) {
            if (antiAtlasValue.get()) {
                for (EntityPlayer entity : (List<EntityPlayer>) mc.theWorld.playerEntities) {
                    if (entity == null && (mc.thePlayer.ticksExisted % 10 == 0 || entity == mc.thePlayer)) continue;
                    if (!LiquidBounce.moduleManager.getModule(AntiBot.class).getState() || !AntiBot.isBot(entity)) {
                        mc.thePlayer.sendChatMessage("/wdr " + entity.getName() + (useOtherWord ? " ka,speed,velocity" : " aimbot,safewalk"));
                        useOtherWord = !useOtherWord;
                    }
                }
            }
            mc.thePlayer.sendChatMessage("/play "+modeValue.get().toLowerCase()+(modeValue.get().equalsIgnoreCase("ranked")?"_normal":modeValue.get().equalsIgnoreCase("mega")?"_"+megaValue.get().toLowerCase():"_"+soloTeamsValue.get().toLowerCase()));
            shouldChangeGame = false;
        }
        if (!shouldChangeGame) timer.reset();
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat chat = (S02PacketChat) event.getPacket();
            if (chat.getChatComponent() != null)
                for (String s : strings)
                    if (chat.getChatComponent().getUnformattedText().contains(s)) {
                        //LiquidBounce.hud.addNotification(new Notification("Attempting to send you to the next game in "+dFormat.format((double)delayValue.get()/1000D)+"s.",1000L));
                        if (autoGGValue.get() && chat.getChatComponent().getUnformattedText().contains(strings[3])) mc.thePlayer.sendChatMessage("GG");
                        shouldChangeGame = true;
                        break;
                    }
        }
    }
    
}
