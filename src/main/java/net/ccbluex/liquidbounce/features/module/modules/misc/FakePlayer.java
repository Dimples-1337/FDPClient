package net.ccbluex.liquidbounce.features.module.modules.misc

import com.mojang.authlib.GameProfile;
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;

import java.util.UUID;

@Module.Register(name = "FakePlayer", category = Module.Category.MISC)
public class FakePlayer extends Module {
    public String nickname = "TheFakeDiOnFire", UUID_Data = "ab47040f-6bcb-4c00-9424-238be6eec731";
    OtherClientPlayerEntity player;
    @Override
    public void onEnable() {
        if (mc.world != null && mc.player != null) {
            player = new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.fromString(UUID_Data), nickname));
            player.copyPositionAndRotation(mc.player);
            player.setHeadYaw(mc.player.headYaw);
            mc.world.addEntity(-100, player);
        } else {
            disable();
        }
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) disable();
    }

    @Override
    public void onDisable() {
        assert mc.world != null;
        mc.world.removeEntity(player.getId(), Entity.RemovalReason.DISCARDED);
    }
}
