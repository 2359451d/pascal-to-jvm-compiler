(*{$mode iso}
{$RANGECHECKS on}*)
program caseTest001;

var
  grade: char;
begin
  grade := 'A';
  case grade of
    'A' : grade:='A';
  end;

  case grade of
    'ABC': grade:='A'; {Incompatible types}
  end;
end.
