package runtime.func;

import runtime.RuntimeFunction;
import type.enumerated.EnumeratedIdentifier;
import type.enumerated.EnumeratedType;

import java.util.List;
import java.util.Set;

public class Pred extends RuntimeFunction {
    public Pred() {
        this.formalParamsMap = Set.of(
                List.of(
                        EnumeratedIdentifier.class
                ),
                List.of(
                        EnumeratedType.class
                )
        );
        this.resultType = new EnumeratedType();
    }
}
