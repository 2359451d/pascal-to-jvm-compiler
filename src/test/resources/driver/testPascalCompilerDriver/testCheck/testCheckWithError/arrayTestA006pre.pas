{$mode iso}
{$RANGECHECKS ON}
program arrayTestA006pre;

type
  subrangeType = 1..50;
var
  arrVar4: array [1..9, 10..19] of array [Boolean, subrangeType] of Integer;
begin
  arrVar4[1,12][true,50] := 1;
end.
