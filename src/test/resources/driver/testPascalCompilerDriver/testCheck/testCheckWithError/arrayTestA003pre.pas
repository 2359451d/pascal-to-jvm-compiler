{$mode iso}
{$RANGECHECKS ON}
program arrayTestA003pre;

var
  arrVar2, arrVar2a: array [Boolean, 1..10] of real;
  arrVar3: array [Boolean] of array [1..10] of Integer;

begin
  {array assignment}
  arrVar2:= arrVar2a;
end.
