(*{$mode iso}
{$RANGECHECKS on}*)
program caseTest003;

var

  grade: char;
  stringVar: string; {case string is not standard feature}
begin
  {case string is Not standrad}
  case stringVar of
    'abc': grade:='a';
  end;
end.
