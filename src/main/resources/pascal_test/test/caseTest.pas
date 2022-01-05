(*{$mode iso}
{$RANGECHECKS on}*)
program caseTest;
var
  intVar:Integer;
  grade: char;
  flagVar: Boolean;
  stringVar: string; {case string is not standard feature}
begin
  grade := 'A';

  case grade of
    'A' : grade:='A';
    'B', 'C': grade:='B';
    'D' : grade:='D';
    'F' : grade:='F';
  end;

  { Non standrad
  case stringVar of
    'abc': grade:='a';
  end;}

  {writeln('Your grade is  ', grade );

  flagVar:= false;

  case flagVar of
    true: grade:='t';
    false: grade:='f';
  end;}
end.