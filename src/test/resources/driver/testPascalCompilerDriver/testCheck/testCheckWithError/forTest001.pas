(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/5
 *)
program forTest;
Var Counter,Counter2,Counter3 : Integer; { loop counter declared as integer }
    i: Integer;
Begin
  for Counter3:= 1 to 3 do i:= i+1; {works}

  For Counter := 1.2 to 3 do {Not work, initial expression is real, but control var is int}
      i := 1+i;
End.