{$mode iso}
{$RANGECHECKS ON}
program arrayTestA008;

var
  arrVar3: array [Boolean] of array [1..10] of Integer;

begin
  arrVar3[true][1]:=1;
  arrVar3[1][1] := 1; {invalid index pos 0, should be boolean}
end.
