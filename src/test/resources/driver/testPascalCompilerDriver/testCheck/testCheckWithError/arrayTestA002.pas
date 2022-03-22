{$mode iso}
{$RANGECHECKS ON}
program arrayTestA002;

var
  charArr : array [char] of Integer;
begin
  charArr['1'] :=1;
  charArr['1'] := 1.0; {invalid assignment}
end.
