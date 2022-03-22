program enumeratedTest;
type
  Rainbow = (RED, ORANGE, YELLOW, GREEN, LIGHT_BLUE, BLUE, VIOLET);

var
  r1,r2:Rainbow;
  pos:Integer;
begin

  r1 := GREEN;
  r2 := Pred(r1); { r2 = YELLOW, assignemnt validility would be checked at runtime}
  r1 := succ(YELLOW); { r1 = Green, assignemnt validility would be checked at runtime}

  pos := Ord(r1); { pos = 3}
  pos := Ord(RED); { pos = 0}

end.