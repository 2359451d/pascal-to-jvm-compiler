{$mode iso}
{$RANGECHECKS ON}
program arrayTestA003;

var
  arrVar2, arrVar2a: array [Boolean, 1..10] of real;
  arrVar3: array [Boolean] of array [1..10] of Integer;

begin
  {array assignment}
  arrVar2:= arrVar2a;
  arrVar2 := arrVar3; {incompatible array type}
end.
