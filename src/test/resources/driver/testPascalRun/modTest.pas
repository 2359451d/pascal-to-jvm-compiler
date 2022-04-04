(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 13/02/2022
 *)
program modTest(output);
var intVar1,intVar2:Integer;
realVar:Real;
begin
  intVar1:=10;
  intVar2:=2;
  WriteLn(intVar1 mod intVar2); {0}

  WriteLn(8 mod 3);{2}

  realVar:= 8 mod 3;
  WriteLn(realVar);{ 2.0000000000000000E+000}

end.