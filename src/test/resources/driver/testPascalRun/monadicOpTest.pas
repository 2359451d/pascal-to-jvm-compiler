(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 13/02/2022
 *)
program monadicOpTest(output);
var intVar1,intVar2:Integer;
  realVar:Real;
begin
  intVar1 := 1;
  WriteLn(-intVar1); {-1}
  intVar2 := 2;

  {1}
  WriteLn(-(intVar1 - intVar2)); {1}

  WriteLn(-5 + 1 - 2); {-6}
  WriteLn(-5 - (1 - 1)); {-5}
  WriteLn(-5 - 1 - 1); {-7}

  WriteLn(+5 - 1); {4}
  WriteLn(+5 - (+1)); {4}

  WriteLn(-5.0); {-5.0}
  WriteLn(-5.0+1.0+(+1));{-3.0}
  WriteLn(-5.0+1+(+1.0));{-3.0}
  WriteLn(-5.0*1+(1.0*1)); {-4.0}

end.