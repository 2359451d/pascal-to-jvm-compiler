(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 22/02/2022
 *)
program benchmark4(input, output);
var r,i,j: integer;

procedure call;
var i,r:integer;
begin
  r:=0;
  writeln(r);
end;

BEGIN
  FOR i := 1 TO 1000 DO
    for j := 1 to 1000 do
      BEGIN
      call;
      END;
END.
