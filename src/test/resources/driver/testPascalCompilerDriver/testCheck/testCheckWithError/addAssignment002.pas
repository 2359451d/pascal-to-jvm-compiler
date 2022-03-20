program addAssignment002;
var
  first, second, sum: integer;
  str: packed array [1..6] of char;
  flag: Boolean;
begin
  first := 1;
  second :=2;
  str := 'string';
  flag := true;
  sum := first+second;
  str := 3+4; (* try to assign integer to a string varaible *)
end.
