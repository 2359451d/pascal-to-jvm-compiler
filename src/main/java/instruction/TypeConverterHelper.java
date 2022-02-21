package instruction;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TypeConverterHelper extends AbstractHelper {
    public static void I2D(MethodVisitor mv) {
        mv.visitInsn(Opcodes.I2D);
    }

    public static void I2D() {
        I2D(defaultMethodVisitor);
    }
}
