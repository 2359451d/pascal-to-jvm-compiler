package type;

import java.util.ArrayList;

public class Sequence extends Type {

    public ArrayList<Type> args;

    public Sequence(ArrayList<Type> seq) {
        args = seq;
    }

    @Override
    public String toString() {
        return "Sequence{" +
                "args=" + args +
                '}';
    }

    @Override
    public boolean equiv(Type that) {
        if (that instanceof Sequence) {
            ArrayList<Type> thatSequence = ((Sequence)that).args;
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
