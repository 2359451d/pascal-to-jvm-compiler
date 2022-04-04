(*{$mode iso}
{$RANGECHECKS on}*)
program caseTest002;

type
  DayOfWeek =
        (monday, tuesday, wednesday, thursday, friday, saturday, sunday);
var
  weatherEnum: (sunny, cloudy);
  realVar:Real;
  intVar1:Integer;
  intVar2:Integer;
  intSubrangeVar:1..10;
  grade: char;
  flagVar: Boolean;
  stringVar: string; {case string is not standard feature}
  day: DayOfWeek;
begin
  grade := 'A';

  case grade of
    'A' : grade:='A';
  end;

  case grade of
    'A' : grade:='A';
    'A' : grade:='A'; {duplicate labels are not allowed}
  end;

end.
