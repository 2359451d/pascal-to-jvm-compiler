(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 21/02/2022
 *)
{$mode iso}
program if1Test;
var intvar:Integer;
begin
  intvar := 9;
  if intvar> 10 then
    WriteLn('greater than 10')
  else writeln('<=10');
end.