(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 13/02/2022
 *)
program divByZeroTest(output);
var
realVar :Real;
intVar1,intVar2:Integer;
begin
  {real div, must return real type}
  realVar:= 2.0/5;

  realVar:= 2/5;

  realVar:= 2.0/5.0;

  {int div, must return int type}
  intVar1:= 1 div 2;

  realVar:= 1 div 2; {int division result ->real 0.0}

  {division by 0}
  intVar1:= 5 div 0;
end.