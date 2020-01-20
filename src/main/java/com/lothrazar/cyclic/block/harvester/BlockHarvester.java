package com.lothrazar.cyclic.block.harvester;

import com.lothrazar.cyclic.CyclicRegistry;
import com.lothrazar.cyclic.base.BlockBase;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockHarvester extends BlockBase {

  public BlockHarvester(Properties properties) {
    super(properties.hardnessAndResistance(1.3F));
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerClient() {
    //    ClientRegistry.bindTileEntityRenderer(TileHarvester.class, new RenderHarvester());
    ScreenManager.registerFactory(CyclicRegistry.ContainerScreens.harvester, ScreenHarvester::new);
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileHarvester();
  }

  @Override
  public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
    if (!world.isRemote) {
      TileEntity tileEntity = world.getTileEntity(pos);
      if (tileEntity instanceof INamedContainerProvider) {
        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
      }
      else {
        throw new IllegalStateException("Our named container provider is missing!");
      }
      return ActionResultType.SUCCESS;
    }
    return super.func_225533_a_(state, world, pos, player, hand, result);
  }
}