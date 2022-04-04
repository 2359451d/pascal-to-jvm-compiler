{$mode iso}
{$RANGECHECKS ON}
program arrayTestA011pre;

var
  arrVar3: array [Boolean] of array [1..10] of Integer;
  subarrVar3: array [1..10] of Integer;
begin
  arrVar3[true] := subarrVar3;
end.
