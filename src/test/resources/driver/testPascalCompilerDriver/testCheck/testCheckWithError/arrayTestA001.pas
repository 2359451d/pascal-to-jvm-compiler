{$mode iso}
{$RANGECHECKS ON}
program arrayTestA001;

var
  charArr : array [char] of Integer;
begin
  charArr['1'] :=1;
  charArr['AS']:= 1; {got "Constant String" expected "Char"}
end.
