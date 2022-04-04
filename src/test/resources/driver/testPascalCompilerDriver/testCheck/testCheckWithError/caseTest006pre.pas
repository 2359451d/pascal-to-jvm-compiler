(*{$mode iso}
{$RANGECHECKS on}*)
program caseTest006pre;

type
  DayOfWeek =
        (monday, tuesday, wednesday, thursday, friday, saturday, sunday);
var
  weatherEnum: (sunny, cloudy);
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

end.
