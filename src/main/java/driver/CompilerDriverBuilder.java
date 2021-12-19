package driver;

import java.io.IOException;
import java.io.OutputStream;

public abstract class CompilerDriverBuilder {

    // default output stream
    private OutputStream out = System.out;

    public abstract CompilerDriverBuilder parse() throws IOException;

    public abstract CompilerDriverBuilder check() throws Exception;

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public OutputStream getOut() {
        return out;
    }
}
