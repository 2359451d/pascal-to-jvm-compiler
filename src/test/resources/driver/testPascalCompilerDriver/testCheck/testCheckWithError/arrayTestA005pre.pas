{$mode iso}
{$RANGECHECKS ON}
program arrayTestA005pre;

type
  arrType = array [Boolean, 1..10] of Integer;
var
  arrVar2, arrVar2a: array [Boolean, 1..10] of real;
  arrVar3: array [Boolean] of array [1..10] of Integer;
  arrVar4: arrType;

begin
  arrVar3 := arrVar4;
end.
