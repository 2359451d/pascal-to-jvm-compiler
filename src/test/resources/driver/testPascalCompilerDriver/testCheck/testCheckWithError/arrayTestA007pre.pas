{$mode iso}
{$RANGECHECKS ON}
program arrayTestA007pre;

type
  subrangeType = 1..50;
var
  n: integer;
  arrVar4: array [1..9, 10..19] of array [Boolean, subrangeType] of Integer;
begin
  n := arrVar4[9,12][true,50];
  n := arrVar4[9][12][true][50];
end.
