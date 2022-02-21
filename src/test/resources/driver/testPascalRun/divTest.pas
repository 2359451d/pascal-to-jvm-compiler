(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 13/02/2022
 *)
program divTest;
var
realVar :Real;
intVar1,intVar2:Integer;
begin
  {real div, must return real type}
  realVar:= 2.0/5;
  WriteLn(realVar);

  realVar:= 2/5;
  WriteLn(realVar);

  realVar:= 2.0/5.0;
  WriteLn(realVar);

  {int div, must return int type}
  intVar1:= 1 div 2;
  WriteLn(intVar1);
  WriteLn(5 div 1);
  WriteLn(0 div 5);
  {WriteLn(5 div 0);} {division by 0}

  realVar:= 1 div 2; {int division result ->real 0.0}
  WriteLn(realVar);

end.