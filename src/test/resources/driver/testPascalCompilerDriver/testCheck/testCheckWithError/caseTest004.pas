(*{$mode iso}
{$RANGECHECKS on}*)
program caseTest004;

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

  intSubrangeVar:=1;
  case intSubrangeVar of
    1: intVar1:=1;
    9: intVar1:=1;
  end;

  intSubrangeVar:=1;
  case intSubrangeVar of
    'a': intVar1:=1; {invalid subrange expression}
  end;

end.
