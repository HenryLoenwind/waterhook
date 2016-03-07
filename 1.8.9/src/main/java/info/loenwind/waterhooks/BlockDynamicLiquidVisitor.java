package info.loenwind.waterhooks;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class BlockDynamicLiquidVisitor extends ClassVisitor implements IClassTransformer {

	// PART I - Common
	
	public BlockDynamicLiquidVisitor() {
		super(Opcodes.ASM4);
	}

	// PART II - IClassTransformer

	@Override
	public byte[] transform(final String name, String transformedName, byte[] bytes) {
		if (bytes != null && transformedName.equals("net.minecraft.block.BlockDynamicLiquid")) { 
			ClassReader cr = new ClassReader(bytes);
			ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
			FMLRelaunchLog.info("["+WaterHooksMod.MODID+"] Trying to patch BlockDynamicLiquid.updateTick (class: %s)", name);
			this.obfClassName = name;
			this.cv = cw;
			cr.accept(this, 0);
			return cw.toByteArray();
		} else {
			return bytes;
		}
	}

	// PART III - ClassVisitor
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor parent = super.visitMethod(access, name, desc, signature, exceptions);
		return match(name, desc)? new HookMethodVisitor(parent) : parent;
	}

	private String obfClassName;
	private final String description = "(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V";
	private final String srgName = "updateTick";
	private final String mcpName = "func_180650_b"; //"func_149674_a";

	public boolean match(String methodName, String methodDesc) {
		if (!methodDesc.equals(description)) return false;
		if (methodName.equals(mcpName)) return true;
		String mapped = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(obfClassName, methodName, methodDesc);
		return mapped.equals(srgName);
	}

	// PART IV - MethodVisitor
	
	private static class HookMethodVisitor extends MethodVisitor {
		public HookMethodVisitor(MethodVisitor mv) {
			super(Opcodes.ASM4, mv);
		}

		/* State Table:
           mv.visitLineNumber(72, l18);
           mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
      0->1 mv.visitVarInsn(ALOAD, 0);
      1->2 mv.visitFieldInsn(GETFIELD, "net/minecraft/block/BlockDynamicLiquid", "field_149815_a", "I");
      2->3 mv.visitInsn(ICONST_2);
           Label l23 = new Label();
      3->4 mv.visitJumpInsn(IF_ICMPLT, l23);
      4->5 mv.visitVarInsn(ALOAD, 0);
      5->6 mv.visitFieldInsn(GETFIELD, "net/minecraft/block/BlockDynamicLiquid", "blockMaterial", "Lnet/minecraft/block/material/Material;");
      6->7 mv.visitFieldInsn(GETSTATIC, "net/minecraft/block/material/Material", "water", "Lnet/minecraft/block/material/Material;");
      7->8 mv.visitJumpInsn(IF_ACMPNE, l23);
      
      
           mv.visitLineNumber(70, l17);
           mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
           mv.visitVarInsn(ALOAD, 0);
           mv.visitFieldInsn(GETFIELD, "net/minecraft/block/BlockDynamicLiquid", "adjacentSourceBlocks", "I");
           mv.visitInsn(ICONST_2);
           Label l22 = new Label();
           mv.visitJumpInsn(IF_ICMPLT, l22);
           mv.visitVarInsn(ALOAD, 0);
           mv.visitFieldInsn(GETFIELD, "net/minecraft/block/BlockDynamicLiquid", "blockMaterial", "Lnet/minecraft/block/material/Material;");
           mv.visitFieldInsn(GETSTATIC, "net/minecraft/block/material/Material", "water", "Lnet/minecraft/block/material/Material;");
           mv.visitJumpInsn(IF_ACMPNE, l22);
		 */
		
		public static boolean HookSuccess = false;
		
		int state = 0;
		
		@Override
		public void visitInsn(int opcode) {
			super.visitInsn(opcode);
			if (state == 2) {
				if (opcode == Opcodes.ICONST_2) {
					state ++;
				} else {
					state = 0;
				}
			}
		}

		@Override
		public void visitVarInsn(int opcode, int var) {
			super.visitVarInsn(opcode, var);
			if (state == 0) {
				if (opcode == Opcodes.ALOAD && var == 0) {
					state ++;
				}
			} else if (state == 4) {
				if (opcode == Opcodes.ALOAD && var == 0) {
					state ++;
				} else {
					state = 0;
				}
			}
		}

		@Override
		public void visitFieldInsn(int opcode, String owner, String name, String desc) {
			super.visitFieldInsn(opcode, owner, name, desc);
			if (state == 1) {
				if (opcode == Opcodes.GETFIELD && "net/minecraft/block/BlockDynamicLiquid".equals(owner) && ("field_149815_a".equals(name) || "adjacentSourceBlocks".equals(name))) {
					state ++;
				} else {
					state = 0;
				}
			} else if (state == 5) {
				if (opcode == Opcodes.GETFIELD && "net/minecraft/block/BlockDynamicLiquid".equals(owner) && ("field_149764_J".equals(name) || "blockMaterial".equals(name))) {
					state ++;
				} else {
					state = 0;
				}
			} else if (state == 6) {
				if (opcode == Opcodes.GETSTATIC && "net/minecraft/block/material/Material".equals(owner) && ("field_151586_h".equals(name) || "water".equals(name))) {
					state ++;
				} else {
					state = 0;
				}
			}
		}

		@Override
		public void visitJumpInsn(int opcode, Label label) {
			super.visitJumpInsn(opcode, label);
			if (state == 3) {
				if (opcode == Opcodes.IF_ICMPLT) {
					state ++;
				} else {
					state = 0;
				}
			} else if (state == 7) {
				if (opcode == Opcodes.IF_ACMPNE) {
					state ++;
					// We are now just at the end of:
					// 72: if (this.field_149815_a >= 2 && this.blockMaterial == Material.water)
					// and append this:
					// && info.loenwind.waterhooks.Hooks.allowFormWaterSourceBlock(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)
					mv.visitVarInsn(Opcodes.ALOAD, 1);
					mv.visitVarInsn(Opcodes.ALOAD, 2);
					mv.visitMethodInsn(Opcodes.INVOKESTATIC, "info/loenwind/waterhooks/Hooks", "allowFormWaterSourceBlock", "(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z", false);
					mv.visitJumpInsn(Opcodes.IFEQ, label);
					// ...
					HookSuccess = true;
					FMLRelaunchLog.info("["+WaterHooksMod.MODID+"] net.minecraft.block.BlockDynamicLiquid.updateTick() patch applied.");
				} else {
					state = 0;
				}
			}
		}

		@Override
		public void visitEnd() {
			super.visitEnd();
			if (!HookSuccess) {
				FMLRelaunchLog.warning("["+WaterHooksMod.MODID+"] net.minecraft.block.BlockDynamicLiquid.updateTick() failed to apply patch!");
			}
		}

	}

	

}