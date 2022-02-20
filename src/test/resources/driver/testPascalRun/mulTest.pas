(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 13/02/2022
 *)
program mulTest;
var intVar, intVar2,intVar3:Integer;
realVar:Real;
begin
  intVar2 := 1;
  intVar3 := 2;
  intVar := intVar2 * intVar3;
  WriteLn(intVar); {2}

  intVar := 1 * 2;
  WriteLn(intVar); {2}

  realVar := 1.0 * 2;
  WriteLn(realVar); {2.0000000000000000E+000}

  realVar := 1 * 2;
  WriteLn(realVar); {2.0000000000000000E+000}

  WriteLn('2*(1+2)=',2*(1+2));

  WriteLn('1.0*0=',1.0*0);
  WriteLn('2*3.0=',2*3.0);
  WriteLn('2.0*3=',2.0*3);
  WriteLn('1*(2*3.0)=',1*(2*3.0));
  WriteLn('1*(2*3)=',1*(2*3));

  realVar:= 5*3*2;
  WriteLn('5*3*2=',realVar);

end.