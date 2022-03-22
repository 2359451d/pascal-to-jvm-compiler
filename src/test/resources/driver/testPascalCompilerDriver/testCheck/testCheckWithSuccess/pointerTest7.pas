program exPointers;
const
  MAX = 4;
type
  sptr = ^ string;

var
  i: integer;
  names: array [1..4] of char;
  parray: array[1..MAX] of sptr;

begin
  for i := 1 to MAX do
    parray[i] := @names[i];

  for i:= 1 to MAX do
    writeln('Value of names[', i, '] = ' , parray[i]^ );
end.