(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 22/02/2022
 *)
program while1Test;
var
  a: integer;

begin
  a := 10;
  while  a < 20  do

  begin
    writeln('value of a: ', a);
    a := a + 1;
    if (a=15) then WriteLn('a=15')
    else begin
      if (a=16) then writeln('a=16')
      end;
  end;
end.