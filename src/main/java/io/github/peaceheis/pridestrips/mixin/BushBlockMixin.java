package io.github.peaceheis.pridestrips.mixin;

import io.github.peaceheis.pridestrips.PrideStrips;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// used so that plants can still generate on the pride strips
@Mixin(BushBlock.class)
public class BushBlockMixin {
	@Inject(method = "mayPlaceOn", at = @At("RETURN"), cancellable = true)
	protected void prideStrips$mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if(Registry.BLOCK.getOrCreateTag(PrideStrips.PRIDESTRIPS_USABLE).contains(floor.getBlockHolder())) {
			cir.setReturnValue(true);
		}
		cir.cancel();
	}
}
