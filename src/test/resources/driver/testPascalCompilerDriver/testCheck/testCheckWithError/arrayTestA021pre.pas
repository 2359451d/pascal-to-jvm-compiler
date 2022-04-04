{$mode iso}
{$RANGECHECKS ON}
program arrayTestA021pre;

var
  x: array [0..100] of array [1..50] of Integer;
begin
  x[0][1] := 100;
  x[0, 2] := 1000;

end.
