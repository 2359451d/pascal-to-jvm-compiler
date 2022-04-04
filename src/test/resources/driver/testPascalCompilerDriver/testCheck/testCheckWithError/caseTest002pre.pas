(*{$mode iso}
{$RANGECHECKS on}*)
program caseTest002pre;

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
  day: DayOfWeek;
begin
  grade := 'A';

  case grade of
    'A' : grade:='A';
  end;

end.
