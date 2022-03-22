{$mode iso}
{$RANGECHECKS ON}
program arrayTestA010;

var
  arrVar3: array [Boolean] of array [1..10] of Integer;
begin
  arrVar3[true][1] := 1;
  arrVar3[true][1] := 1.0; {invalid, expected integer}
end.
