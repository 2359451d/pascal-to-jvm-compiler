(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 22/02/2022
 *)
program benchmark3(input, output);
var r,i: integer;

procedure call;
var i,r:integer;
begin
  r:=0;
  FOR i := 1 TO 1000 DO
  BEGIN
    r := r + i;
  END;
  writeln(r);
end;

BEGIN
  FOR i := 1 TO 1000000 DO
  BEGIN
    call;
  END;
  FOR i := 1 TO 1000000 DO
  BEGIN
    call;
  END;
  FOR i := 1 TO 1000000 DO
  BEGIN
    call;
  END;
  FOR i := 1 TO 1000000 DO
  BEGIN
    call;
  END;
END.
