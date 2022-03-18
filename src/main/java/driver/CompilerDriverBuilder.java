package driver;

import exception.PascalCompilerException;
import utils.log.GlobalLogger;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

public abstract class CompilerDriverBuilder {

    // default output stream
    private OutputStream out = System.out;

    public abstract CompilerDriverBuilder parse() throws IOException, PascalCompilerException;

    public abstract CompilerDriverBuilder check() throws PascalCompilerException, IOException;

    public abstract CompilerDriverBuilder run() throws PascalCompilerException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public OutputStream getOut() {
        return out;
    }
}
