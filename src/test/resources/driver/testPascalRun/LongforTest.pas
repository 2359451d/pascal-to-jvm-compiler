(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 22/02/2022
 *)
program LongforTest(input, output);
var r,i: integer;

BEGIN
  r := 0;
  FOR i := 1 TO 10000 DO
  BEGIN
    r := r + i;
  END;

  WriteLn(r);
END.
