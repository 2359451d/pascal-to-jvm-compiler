package type;

import java.util.List;

@Deprecated
public class Signature extends BaseType {

    private List<String> paramList;
    //private Set<Type> acceptableTypes;

    public Signature(List<String> paramList) {
        this.paramList = paramList;
    }

    //public Signature(List<Type> paramList, Set<Type> acceptableTypes) {
    //    this.paramList = paramList;
    //    this.acceptableTypes = acceptableTypes;
    //}

    public List<String> getParamList() {
        return paramList;
    }

    //public Set<Type> getAcceptableTypes() {
    //    return acceptableTypes;
    //}

    @Override
    public String toString() {
        return "Signature{" +
                "paramList=" + paramList +
                '}';
    }

    //@Override
    //public boolean equals(Object o) {
    //    if (this == o) return true;
    //    System.out.println("---------------equals");
    //    if (o == null || getClass() != o.getClass()) return false;
    //    Signature signature = (Signature) o;
    //    List<Type> paramList = signature.getParamList();
    //    for (int i = 0; i < this.paramList.size(); i++) {
    //        Type type1 = this.paramList.get(i);
    //        Type type2 = paramList.get(i);
    //        if (!type1.equiv(type2)) return false;
    //
    //    }
    //    return true;
    //}
    //
    //@Override
    //public int hashCode() {
    //    System.out.println("---------------hash");
    //    System.out.println("Objects.hash(paramList) = " + Objects.hash(paramList));
    //    return Objects.hash(paramList);
    //}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Signature signature = (Signature) o;

        return paramList.equals(signature.paramList);
    }

    @Override
    public int hashCode() {
        return paramList.hashCode();
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return false;
    }


}
