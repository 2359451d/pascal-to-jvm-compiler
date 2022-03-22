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
import utils.ErrorMessage;

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

        if (type instanceof StringLiteral) {
            return isValidStringAssignment(this, (StringLiteral) type);
        }

        if (!(type instanceof ArrayType)) return false;
        ArrayType that = (ArrayType) type;
        TypeDescriptor thatComponentType = that.getComponentType();
        List<TypeDescriptor> thatIndexList = that.getIndexList();
        boolean thatPacked = that.isPacked;

        if (this.componentType.getClass() != thatComponentType.getClass()
                && this.isPacked == thatPacked) return false;


        if (this.indexList.size() != thatIndexList.size()) return false;

        for (int i = 0; i < this.indexList.size(); i++) {
            TypeDescriptor thisType = this.indexList.get(i);
            TypeDescriptor thatType = thatIndexList.get(i);

            // if left is EnumeratedType then right type(subrange) must cover all the enum identifiers
            if (thisType instanceof EnumeratedType && thatType instanceof Subrange) {
                if (!checkEnumeratedAndSubrangeEquality((EnumeratedType) thisType, (Subrange) thatType)) return false;
            } else if (thisType instanceof Subrange && thatType instanceof EnumeratedType) {
                if (!checkEnumeratedAndSubrangeEquality((EnumeratedType) thatType, (Subrange) thisType)) return false;
            } else if (thisType instanceof Subrange && thatType instanceof Subrange) {
                // further checking lower & upper bounds of subrange type
                Subrange _thisType = (Subrange) thisType;
                Subrange _thatType = (Subrange) thatType;
                if (!isValidSubrange(_thisType, _thatType)) return false;
            } else if (!thisType.equiv(thatType)) return false;
        }
        return true;
    }

    private boolean isValidStringAssignment(ArrayType left, StringLiteral right) {
        StringBuilder stringBuilder = new StringBuilder();
        TypeDescriptor componentType = (left).getComponentType();
        if (!left.isPacked() && !(componentType instanceof Character)) return false;

        TypeDescriptor type = left.getIndexList().get(0);
        if (type instanceof Subrange) {
            TypeDescriptor lowerBound = ((Subrange) type).getLowerBound();
            TypeDescriptor upperBound = ((Subrange) type).getUpperBound();
            // calculate the expected string length
            Long expectedLength = null;
            if (lowerBound instanceof IntegerBaseType && upperBound instanceof IntegerBaseType) {
                Long lowerValue = ((IntegerBaseType) lowerBound).getValue();
                Long upperValue = ((IntegerBaseType) upperBound).getValue();
                if (lowerValue != 1) {
                    return false;
                }
                expectedLength = upperValue - lowerValue + 1;
                // check whether right operand is a string type with the same length
                int actualLength = right.getValue().replace("'", "").length();
                if (expectedLength != null && expectedLength.intValue() != actualLength) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Check enumerated and subrange types equality
     *
     * @param enumerated - enumerated type
     * @param subrange   - subrange type
     * @return boolean - true: are equal, false: not equal
     */
    private boolean checkEnumeratedAndSubrangeEquality(EnumeratedType enumerated, Subrange subrange) {
        TypeDescriptor thatLowerBound = subrange.getLowerBound();
        TypeDescriptor thatUpperBound = subrange.getUpperBound();
        Map<String, Integer> valueMap = enumerated.getValueMap();
        if (!(thatLowerBound instanceof EnumeratedIdentifier) || !(thatUpperBound instanceof EnumeratedIdentifier))
            return true;
        String lowerValue = ((EnumeratedIdentifier) thatLowerBound).getValue();
        String upperValue = ((EnumeratedIdentifier) thatUpperBound).getValue();

        // if not of the same enumerated type kind, directly return false
        if (!valueMap.containsKey(lowerValue) || !valueMap.containsKey(upperValue)) return false;

        // lower bound should correspond to 0
        if (valueMap.get(lowerValue) != 0) return false;

        // upper bound should correspond to the largest ordinal number
        // i.e. valueMap size - 1
        if (valueMap.get(upperValue) != valueMap.size() - 1) return false;
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
