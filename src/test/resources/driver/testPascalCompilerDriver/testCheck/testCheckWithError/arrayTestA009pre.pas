{$mode iso}
{$RANGECHECKS ON}
program arrayTestA009pre;

var
  arrVar3: array [Boolean] of array [1..10] of Integer;
begin
  arrVar3[true][5] := 1;
  arrVar3[false][10] := 1;
end.
