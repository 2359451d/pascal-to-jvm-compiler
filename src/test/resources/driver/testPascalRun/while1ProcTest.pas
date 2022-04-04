(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 27/02/2022
 *)
program while1ProcTest(output);
var
  a: integer;

procedure whileProc;
begin
  a := 10;
  while  a < 20  do

  begin
    writeln('value of a: ', a);
    a := a + 1;
  end;
end;

begin
  whileProc()
end.