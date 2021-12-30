package type;

import java.util.ArrayList;

public class Sequence extends Type {

    public ArrayList<TypeDescriptor> args;

    public Sequence(ArrayList<TypeDescriptor> seq) {
        args = seq;
    }

    @Override
    public String toString() {
        return "Sequence{" +
                "args=" + args +
                '}';
    }

    @Override
    public boolean equiv(TypeDescriptor that) {
        if (that instanceof Sequence) {
            ArrayList<TypeDescriptor> thatSequence = ((Sequence)that).args;
            if (thatSequence.size() != args.size())
                return false;
            for (int i = 0; i < thatSequence.size(); i++)
                if (!(thatSequence.get(i).equiv(args.get(i))))
                    return false;
            return true;
        }
        else
            return false;
    }

}
