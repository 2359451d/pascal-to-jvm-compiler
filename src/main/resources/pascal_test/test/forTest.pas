(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/5
 *)
program forTest;
Var Counter,Counter2,Counter3 : Integer; { loop counter declared as integer }
    c : Real;
    i: Integer;
Begin
  for Counter3:= 1 to 3 do i:= i+1; {works}

  For Counter := 1.2 to 2 do {Not work, initial expression is real, but control var is int}
    for Counter2:= 1 to 2.0 do {Not work, final expression is real, but control var is int}
      for c:= 1 to 1 do {Not work, control var must be ordinal, cannot be real}
      i := 1+i;
End.