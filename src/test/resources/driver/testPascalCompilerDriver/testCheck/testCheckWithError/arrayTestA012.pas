{$mode iso}
{$RANGECHECKS ON}
program arrayTestA012;

var
  arrVar3, arrVar3_: array [Boolean] of array [1..10] of Integer;
begin
  arrVar3:= arrVar3_;
  arrVar3 := 1.0; {invalid, expected array of [boolean, 1..10]}
end.
