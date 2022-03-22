{$mode iso}
{$RANGECHECKS ON}
program arrayTestA011;

var
  arrVar3: array [Boolean] of array [1..10] of Integer;
  subarrVar3: array [1..10] of Integer;
begin
  arrVar3[true] := subarrVar3;
  arrVar3[true] := 1.0; {invalid, expected array of [1..10]}
end.
