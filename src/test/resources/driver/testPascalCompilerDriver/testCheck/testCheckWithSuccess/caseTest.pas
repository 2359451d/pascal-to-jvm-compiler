(*{$mode iso}
{$RANGECHECKS on}*)
program caseTest;

var
  grade: char;
  flagVar: Boolean;
  intVar1:Integer;
  intVar2:Integer;
  intSubrangeVar:1..10;
begin
  grade := 'A';
  case grade of
    'A': grade := 'A';
  end;

  case grade of
    'B': grade := 'A'; {raise RUNTIME error if this selector does not exist}
  end;

  case grade of
    'A': grade := 'A';
    'B', 'C': grade := 'B';
    'D': grade := 'D';
    'F': grade := 'F';
  end;

  flagVar := false;
  case flagVar = false of
    true: grade := 't';
      {raise RUNTIME error if this selector does not exist}
  end;

  case flagVar = false of
    true: grade := 't';
    false: grade := 'f';
  end;

  intVar1 := 1;
  intVar2 := intVar1; {rUNTIME check, do not raise error though cannot match the expression value}
  case intVar2 of
    1: intVar1 := 1;
    10: intVar1 := 1;
  end;

  case intVar1 of
    1: intVar1 := 1;
    10: intVar1 := 1;
  end;

  intVar1:=1;
  intSubrangeVar:=1;
  case intSubrangeVar of
    1: intVar1:=1;
    9: intVar1:=1;
  end;

  (** NOT Standard*)
  (*case intVar2 of
  1..10: intVar1:=100;
  9: intVar1:=1; {duplicate labels not allowed}
  11..100: intVar1:=10;
  end;*)
end.
