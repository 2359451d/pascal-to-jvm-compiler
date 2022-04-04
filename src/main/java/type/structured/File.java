package type.structured;

import type.BaseType;
import type.TypeDescriptor;

public class File extends BaseType {

    private TypeDescriptor type;

    public File() {
    }

    public File(TypeDescriptor type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "File{" +
                "type=" + type +
                '}';
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof File)) return false;
        File fileType = (File) type;
        return fileType.type.toString().equals(this.type.toString());
    }
}
