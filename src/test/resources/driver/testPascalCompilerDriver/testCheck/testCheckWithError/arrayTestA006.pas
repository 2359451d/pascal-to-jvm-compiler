{$mode iso}
{$RANGECHECKS ON}
program arrayTestA006;

type
  subrangeType = 1..50;
var
  arrVar4: array [1..9, 10..19] of array [Boolean, subrangeType] of Integer;
begin
  arrVar4[1,12][true,50] := 1;
  arrVar4[1, 12][true, 51] := 1; {invalid index 3, range should be 1..50}
end.
