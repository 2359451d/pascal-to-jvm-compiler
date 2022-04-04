{$mode iso}
{$RANGECHECKS ON}
program arrayTestA009;

var
  arrVar3: array [Boolean] of array [1..10] of Integer;
begin
  arrVar3[true][5] := 1;
  arrVar3[false][10] := 1;
  arrVar3[false][11] := 1; {invalid index pos 1, should be of subrange 1..10}
end.
