package com.raoulvdberge.refinedstorageaddons.block;

import com.raoulvdberge.refinedstorage.block.BlockCable;
import com.raoulvdberge.refinedstorage.block.BlockNode;
import com.raoulvdberge.refinedstorage.block.info.BlockInfoBuilder;
import com.raoulvdberge.refinedstorageaddons.RSAddons;
import com.raoulvdberge.refinedstorageaddons.RSAddonsGui;
import com.raoulvdberge.refinedstorageaddons.tile.TileInfiniteWirelessTransmitter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockInfiniteWirelessTransmitter extends BlockNode {
    // From BlockTorch
    private static final AxisAlignedBB INFINITE_WIRELESS_TRANSMITTER_AABB = new AxisAlignedBB(0.4000000059604645D, 0.0D, 0.4000000059604645D, 0.6000000238418579D, 0.6000000238418579D, 0.6000000238418579D);

    public BlockInfiniteWirelessTransmitter() {
        super(BlockInfoBuilder.forMod(RSAddons.INSTANCE, RSAddons.ID, "infinite_wireless_transmitter").tileEntity(TileInfiniteWirelessTransmitter::new).create());

        setCreativeTab(RSAddons.INSTANCE.tab);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        return openNetworkGui(RSAddonsGui.INFINITE_WIRELESS_TRANSMITTER, player, world, pos, side);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!canPlaceBlockAt(world, pos) && world.getBlockState(pos).getBlock() == this) {
            dropBlockAsItem(world, pos, state, 0);

            world.setBlockToAir(pos);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return INFINITE_WIRELESS_TRANSMITTER_AABB;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return world.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() instanceof BlockCable;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean hasConnectedState() {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        tooltip.add(I18n.format("block.refinedstorage:wireless_transmitter.tooltip", TextFormatting.WHITE + I18n.format("block.refinedstorage:cable.name") + TextFormatting.GRAY));
    }
}
