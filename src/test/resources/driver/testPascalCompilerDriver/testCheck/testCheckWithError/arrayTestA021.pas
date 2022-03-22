{$mode iso}
{$RANGECHECKS ON}
program arrayTestA021;

var
  x: array [0..100] of array [1..50] of Integer;
begin
  x[0][1] := 100;
  x[0, 2] := 1000;

  x[100][0] := 100;  {invalid scripting, pos 1 should be range of 1..50}
end.
