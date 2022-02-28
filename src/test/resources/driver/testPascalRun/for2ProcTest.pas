(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 22/02/2022
 *)
program for2ProcTest;
var
  a: integer;
realVar:Real;

procedure forProc;
begin
  for a := 10  to 20 do

  begin
    writeln('value of a: ', a);
  end;
end;

begin
  forProc()
end.