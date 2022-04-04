(*{$mode iso}
{$RANGECHECKS on}*)
program caseTest001pre;

var
  grade: char;
begin
  grade := 'A';
  case grade of
    'A' : grade:='A';
  end;

end.
