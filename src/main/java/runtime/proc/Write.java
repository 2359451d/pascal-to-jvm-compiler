package runtime.proc;

import runtime.RTLManager;
import runtime.RunTimeLibFactory;
import type.*;
import type.primitive.Primitive;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Write implements RTLManager {
    public static SignatureSet signatureSet;
    private Set<Signature> acceptableSignatures;

    /**
     * The Write command accepts an arbitrary number of arguments.
     * The V1, V2, V3 in the declaration here are in fact just samples,
     * the actual number may be much higher.
     * The types of arguments (Type1 etc.) are limited to the following types:
     * <p>
     * Any character type.
     * Any string type (including pchar).
     * Any ordinal type (integer, enumerated).
     * The Int64 and QWord type.
     * Any floating-point type (such as double, single, extended).
     *
     * Note: File, and other non-standard types haven't been implemented
     */
    public static final Set<String> acceptableTypes = new HashSet<String>() {{
        add(Primitive.class.getName());
        //add(Type.BOOLEAN);
        //add(Type.CHARACTER);
        //add(Type.STR);
        //add(Type.REAL);
        //add(Type.VOID);
        add(StringLiteral.class.getName());
        add(File.class.getName());
    }};

    public Write() {
        acceptableSignatures = new HashSet<>();
        signatureSet = new SignatureSet(acceptableSignatures, acceptableTypes);
    }

    private void addNewSignature(Signature signature) {
        acceptableSignatures.add(signature);
    }

    //private Signature writeInt() {
    //    // write int
    //    List<Type> paramList = Stream.of(
    //            new Param(Type.INTEGER)
    //    ).collect(Collectors.toList());
    //    return new Signature(paramList);
    //}
    //
    //private Signature writeString() {
    //    return new Signature(
    //            Stream.of(new Param(Type.STR))
    //                    .collect(Collectors.toList()));
    //}
    //
    //private Signature writeChar() {
    //    return new Signature(
    //            Stream.of(new Param(Type.CHARACTER))
    //                    .collect(Collectors.toList()));
    //}
    //
    //private Signature writeReal() {
    //    return new Signature(
    //            Stream.of(new Param(Type.REAL))
    //                    .collect(Collectors.toList()));
    //}
    //
    //private Signature writeBool() {
    //    return new Signature(
    //            Stream.of(new Param(Type.BOOLEAN))
    //                    .collect(Collectors.toList()));
    //}

    @Override
    public void addToFactory() {
        generateSignatureSet();
        RunTimeLibFactory.lib.put("write", signatureSet);
    }

    @Override
    public void generateSignatureSet() {
        //acceptableSignatures.add(writeInt());
        //acceptableSignatures.add(writeBool());
        //acceptableSignatures.add(writeChar());
        //acceptableSignatures.add(writeReal());
        //acceptableSignatures.add(writeString());

        //acceptableSignatures.add(new Signature(
        //        Stream.of(new UnorderedParam(acceptableTypes))
        //                .collect(Collectors.toList())
        //        )
        //);
        acceptableSignatures.add(new Signature(
                Stream.of(new File(Type.INTEGER).toString(), Type.INTEGER.toString()).collect(Collectors.toList())
                //Stream.of(new File(Type.INTEGER), Type.INTEGER).collect(Collectors.toSet())
        ));
        Set<String> typeOrderToBeChecked = new HashSet<>();
        typeOrderToBeChecked.add(new File(Type.INTEGER).toString());
        signatureSet = new SignatureSet(acceptableSignatures, acceptableTypes, typeOrderToBeChecked);
    }
}
