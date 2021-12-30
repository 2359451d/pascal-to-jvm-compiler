package type;

public class File extends Type {

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
        System.out.println("fileType.type.toString() = " + fileType.type.toString());
        return fileType.type.toString().equals(this.type.toString());
    }
}
