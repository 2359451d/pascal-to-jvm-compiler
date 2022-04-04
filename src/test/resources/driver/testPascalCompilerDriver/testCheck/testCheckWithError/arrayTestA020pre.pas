{$mode iso}
{$RANGECHECKS ON}
program arrayTestA020pre;

var
  x: array [0..100] of array [1..50] of Integer;
  y: array [1..50] of Integer;

begin
  {invalid scripting, pos 0 should be range of 0..100}
  {got "int" expected "Array[1..50] Of int"}
  x[101] := y;
end.
