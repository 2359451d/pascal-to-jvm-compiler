package runtime.proc;

import annotation.RuntimeAlias;
import runtime.RuntimeProcedure;
import type.PointerType;

import java.util.List;
import java.util.Set;

/**
 * new(PointerType)
 */
@RuntimeAlias("new")
public class NewPointer extends RuntimeProcedure {
    public NewPointer() {
        this.formalParamsMap = Set.of(
                List.of(
                    PointerType.class
                )
        );
    }
}
