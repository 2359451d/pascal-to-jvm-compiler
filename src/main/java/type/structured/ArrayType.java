package type.structured;

import type.StringLiteral;
import type.Subrange;
import type.TypeDescriptor;
import type.enumerated.EnumeratedIdentifier;
import type.enumerated.EnumeratedType;
import type.primitive.Boolean;
import type.primitive.Character;
import type.primitive.integer.DefaultIntegerType;
import type.primitive.integer.IntegerBaseType;

import java.util.List;
import java.util.Map;

public class ArrayType extends StructuredBaseType {

    /**
     * component type of the array
     */
    private TypeDescriptor componentType;

    /**
     * ordered index types list, if size>1 then array is multi-dimensional
     */
    private List<TypeDescriptor> indexList;

    public ArrayType(TypeDescriptor componentType, List<TypeDescriptor> indexList) {
        this.componentType = componentType;
        this.indexList = indexList;
    }

    public TypeDescriptor getComponentType() {
        return componentType;
    }

    public List<TypeDescriptor> getIndexList() {
        return indexList;
    }

    public void setComponentType(TypeDescriptor componentType) {
        this.componentType = componentType;
    }

    public void setIndexList(List<TypeDescriptor> indexList) {
        this.indexList = indexList;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof ArrayType)) return false;
        ArrayType that = (ArrayType) type;
        TypeDescriptor thatComponentType = that.getComponentType();
        List<TypeDescriptor> thatIndexList = that.getIndexList();
        boolean thatPacked = that.isPacked();

        if (this.componentType.getClass() != thatComponentType.getClass()
                && this.isPacked() == thatPacked) return false;

        if (this.indexList.size() != thatIndexList.size()) return false;

        for (int i = 0; i < this.indexList.size(); i++) {
            TypeDescriptor thisType = this.indexList.get(i);
            System.out.println("thisType = " + thisType);
            TypeDescriptor thatType = thatIndexList.get(i);
            System.out.println("thatType = " + thatType);

            // if left is EnumeratedType then right type(subrange) must cover all the enum identifiers
            if (thisType instanceof EnumeratedType && thatType instanceof Subrange) {
                if (!checkEnumeratedAndSubrangeEquality((EnumeratedType) thisType, (Subrange) thatType)) return false;
            } else if (thisType instanceof Subrange && thatType instanceof EnumeratedType) {
                if (!checkEnumeratedAndSubrangeEquality((EnumeratedType) thatType,(Subrange) thisType)) return false;
            } else if (thisType instanceof Subrange && thatType instanceof Subrange) {
                // further checking lower & upper bounds of subrange type
                Subrange _thisType = (Subrange) thisType;
                Subrange _thatType = (Subrange) thatType;
                if (!isValidSubrange(_thisType, _thatType)) return false;
            } else if (!thisType.equiv(thatType)) return false;
        }
        return true;
    }

    /**
     * Check enumerated and subrange types equality
     * @param enumerated - enumerated type
     * @param subrange - subrange type
     * @return boolean - true: are equal, false: not equal
     */
    private boolean checkEnumeratedAndSubrangeEquality(EnumeratedType enumerated, Subrange subrange) {
        TypeDescriptor thatLowerBound = subrange.getLowerBound();
        TypeDescriptor thatUpperBound = subrange.getUpperBound();
        Map<String, Integer> valueMap = enumerated.getValueMap();
        if (! (thatLowerBound instanceof EnumeratedIdentifier) || ! (thatUpperBound instanceof EnumeratedIdentifier))
            return true;
        String lowerValue = ((EnumeratedIdentifier) thatLowerBound).getValue();
        String upperValue = ((EnumeratedIdentifier) thatUpperBound).getValue();

        // if not of the same enumerated type kind, directly return false
        if (!valueMap.containsKey(lowerValue) || !valueMap.containsKey(upperValue)) return false;

        // lower bound should correspond to 0
        if (valueMap.get(lowerValue)!=0) return false;

        // upper bound should correspond to the largest ordinal number
        // i.e. valueMap size - 1
        if (valueMap.get(upperValue)!=valueMap.size()-1) return false;
        return true;
    }

    private boolean isValidSubrange(Subrange leftType, Subrange rightType) {
        Class<? extends TypeDescriptor> leftTypeHostType = leftType.getHostType();
        TypeDescriptor leftTypeLowerBound = leftType.getLowerBound();
        TypeDescriptor leftTypeUpperBound = leftType.getUpperBound();

        Class<? extends TypeDescriptor> rightTypeHostType = rightType.getHostType();
        TypeDescriptor rightTypeLowerBound = rightType.getLowerBound();
        TypeDescriptor rightTypeUpperBound = rightType.getUpperBound();

        // TODO Enumerated subrange ???

        System.out.println("array type leftTypeHostType = " + leftTypeHostType);
        System.out.println("rightTypeHostType = " + rightTypeHostType);

        System.out.println("leftType = " + leftType);
        System.out.println("array type rightType  = " + rightType);

        // check other ordinal types
        if (rightTypeHostType == leftTypeHostType) {
            if (rightTypeHostType == Boolean.class) {
                Boolean _rightTypeLowerBound = (Boolean) rightTypeLowerBound;
                Boolean _rightTypeUpperBound = (Boolean) rightTypeUpperBound;
                Boolean _leftTypeLowerBound = (Boolean) leftTypeLowerBound;
                Boolean _leftTypeUpperBound = (Boolean) leftTypeUpperBound;
                return _rightTypeLowerBound.getValue() == _leftTypeLowerBound.getValue()
                        && _rightTypeUpperBound.getValue() == _leftTypeUpperBound.getValue();
            }

            if (rightTypeHostType == DefaultIntegerType.instance.getClass()) {
                Long rightLowerValue = ((IntegerBaseType) rightTypeLowerBound).getValue();
                Long rightUpperValue = ((IntegerBaseType) rightTypeUpperBound).getValue();

                Long leftLowerValue = ((IntegerBaseType) leftTypeLowerBound).getValue();
                Long leftUpperValue = ((IntegerBaseType) leftTypeUpperBound).getValue();
                return rightLowerValue.equals(leftLowerValue) && rightUpperValue.equals(leftUpperValue);
            }

            // char subrange
            if (rightTypeHostType == StringLiteral.class) {
                String rightLowerValue = ((StringLiteral) rightTypeLowerBound).getValue();
                String rightUpperValue = ((StringLiteral) rightTypeUpperBound).getValue();
                String leftLowerValue = ((StringLiteral) leftTypeLowerBound).getValue();
                String leftUpperValue = ((StringLiteral) leftTypeUpperBound).getValue();
                return rightLowerValue.equals(leftLowerValue) && rightUpperValue.equals(leftUpperValue);
            }
        }
        return false;
    }


}
