{$mode iso}
{$RANGECHECKS ON}
program arrayTestA008pre;

var
  arrVar3: array [Boolean] of array [1..10] of Integer;

begin
  arrVar3[true][1]:=1;
end.
