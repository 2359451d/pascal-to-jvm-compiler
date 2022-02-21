package instruction;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TypeConverterHelper extends AbstractHelper {
    public static void I2F(MethodVisitor mv) {
        mv.visitInsn(Opcodes.I2F);
    }

    public static void I2F() {
        I2F(defaultMethodVisitor);
    }
}
