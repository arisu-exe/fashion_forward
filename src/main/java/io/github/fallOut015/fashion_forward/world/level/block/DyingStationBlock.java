package io.github.fallOut015.fashion_forward.world.level.block;

import io.github.fallOut015.fashion_forward.stats.StatsFashionForward;
import io.github.fallOut015.fashion_forward.world.inventory.DyingStationMenu;
import io.github.fallOut015.fashion_forward.world.inventory.SewingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class DyingStationBlock extends Block {
    private static final Component CONTAINER_TITLE;

    static {
        CONTAINER_TITLE = new TranslatableComponent("container.dying_station");
    }

    public DyingStationBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(blockState.getMenuProvider(level, blockPos));
            player.awardStat(StatsFashionForward.INTERACT_WITH_DYING_STATION);
            return InteractionResult.CONSUME;
        }
    }
    @Override
    public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        return new SimpleMenuProvider((containerId, inventory, player) -> new DyingStationMenu(containerId, inventory, ContainerLevelAccess.create(level, blockPos)), CONTAINER_TITLE);
    }
}