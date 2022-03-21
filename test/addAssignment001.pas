program addAssignment001;
var
  first, second, sum: integer;
  str: packed array [1..6] of char;
  flag: Boolean;
begin
  first := 1;
  second :=2;
  str := 'string';
  flag := true;
  flag := 1;{illegal assignment}
  sum := first+second;
end.
