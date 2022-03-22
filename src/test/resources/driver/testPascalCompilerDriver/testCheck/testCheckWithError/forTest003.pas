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
  For Counter := 1 to 2 do
    for Counter2:= 1 to 2 do
      for Counter3:= 1 to 1 do
        i := 1+i;

  For Counter := 1 to 2 do
    for Counter2:= 1 to 2 do
      for c:= 1 to 1 do {Not work, control var must be ordinal, cannot be real}
        i := 1+i;
End.