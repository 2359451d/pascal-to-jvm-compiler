{$mode iso}
{$RANGECHECKS ON}
program arrayTestA004;

type
  subrangeType = 1..50;
var
  arrVar2, arrVar2a: array [Boolean, 1..10] of real;
  arrVar4: array [1..9, 10..19] of array [Boolean, subrangeType] of Integer;

begin
  arrVar2 := arrVar2a;
  arrVar2 :=arrVar4; {incompatible array type}
end.
