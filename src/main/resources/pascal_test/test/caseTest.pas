(*{$mode iso}
{$RANGECHECKS on}*)
program caseTest;

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
  {realVar :=intVar1;
  intVar1 := realVar;}

  grade := 'A';
  case grade of
    'ABC': grade:='A'; {Incompatible types}
    'A' : grade:='A'; {raise RUNTIME error if this selector does not exist}
    'A' : grade:='A'; {duplicate labels are not allowed}
    1: grade:=1; {Constant and case type does not match., Illegal assignment [grade:=1] }
    'B', 'C': grade:='B';
    'D' : grade:='D';
    'F' : grade:='F';
  end;

  flagVar:= false;
  case flagVar=false of
    true: grade:='t';
    false: grade:='f'; {raise RUNTIME error if this selector does not exist}
  end;

  intVar1:=1;
  intVar2:=intVar1; {rUNTIME check, do not raise error though cannot match the expression value}
  case intVar2 of
    1: intVar1:=1;
    10: intVar1:=1;
  end;

  case intVar1 of
    1: intVar1:=1;
    10: intVar1:=1;
  end;

  intVar1:=1;
  intSubrangeVar:=1;
  case intSubrangeVar of
    1: intVar1:=1;
    9: intVar1:=1;
  end;

  (*case intVar2 of
    1..10: intVar1:=100;
    9: intVar1:=1; {duplicate labels not allowed}
    11..100: intVar1:=10;
  end;* NOT Standard*)

  {CASE ENUMERATED TYPES}
  case day of
    Monday:    intVar1:=1;
    monday: intVar1:=1; {duplicate raise error}
    sunny: intVar1:=1; {incompatible enum type}
    tuesday:   intVar1:=1;
    wednesday: intVar1:=1;
    thursday:  intVar1:=1;
    friday:    intVar1:=1;
    saturday:  intVar1:=1;
    sunday:    intVar1:=1;
  end;

  {case string is Not standrad}
  case stringVar of
    'abc': grade:='a';
  end;
end.