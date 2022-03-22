(*{$mode iso}
{$RANGECHECKS on}*)
program caseTest;

type
  DayOfWeek =
        (monday, tuesday, wednesday, thursday, friday, saturday, sunday);
var
  day: DayOfWeek;
  intVar1:Integer;

  grade: char;
  stringVar: string; {case string is not standard feature}
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

end.