program pointerTest6;
const MAX = 3;
type
  iptr = ^integer;

var
  arr: array [1..MAX] of integer;
  i: integer;
  parray: array[1..MAX] of iptr;

begin
  (* let us assign the addresses to parray *)
  for i:= 1 to MAX do
    parray[i] := @arr[i];

  (* let us print the values using the pointer array *)
  for i:=1 to MAX do
    writeln(' Value of arr[', i, '] = ' , parray[i]^ );
end.
