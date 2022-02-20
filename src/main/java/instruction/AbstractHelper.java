package instruction;

import ast.visitor.impl.PascalEncoderVisitor;
import org.objectweb.asm.MethodVisitor;

public abstract class AbstractHelper {
    protected static MethodVisitor defaultMethodVisitor = null;

    protected static MethodVisitor updateDefaultMethodVisitor() {
        return PascalEncoderVisitor.methodVisitor;
    }

    public static void setDefaultMethodVisitor(MethodVisitor defaultMethodVisitor) {
        AbstractHelper.defaultMethodVisitor = defaultMethodVisitor;
    }
}
