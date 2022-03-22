package runtime.func;

import runtime.RuntimeFunction;
import type.enumerated.EnumeratedIdentifier;
import type.enumerated.EnumeratedType;
import type.primitive.integer.Integer32;

import java.util.List;
import java.util.Set;

public class Ord extends RuntimeFunction {
    public Ord() {
        this.formalParamsMap = Set.of(
                List.of(
                        EnumeratedIdentifier.class
                ),
                List.of(
                        EnumeratedType.class
                )
        );
        this.resultType = new Integer32();
    }
}
