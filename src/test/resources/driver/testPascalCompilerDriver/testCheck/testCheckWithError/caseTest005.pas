(*{$mode iso}
{$RANGECHECKS on}*)
program caseTest;

type
  DayOfWeek =
        (monday, tuesday, wednesday, thursday, friday, saturday, sunday);
var
  intVar1:Integer;
  day: DayOfWeek;
begin
  {CASE ENUMERATED TYPES}
  case day of
    Monday:    intVar1:=1;
    tuesday:   intVar1:=1;
    wednesday: intVar1:=1;
    thursday:  intVar1:=1;
    friday:    intVar1:=1;
    saturday:  intVar1:=1;
    sunday:    intVar1:=1;
  end;

  case day of
    Monday:    intVar1:=1;
    monday: intVar1:=1; {duplicate raise error}
    tuesday:   intVar1:=1;
    wednesday: intVar1:=1;
    thursday:  intVar1:=1;
    friday:    intVar1:=1;
    saturday:  intVar1:=1;
    sunday:    intVar1:=1;
  end;
end.