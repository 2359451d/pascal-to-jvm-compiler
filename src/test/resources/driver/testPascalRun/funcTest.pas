(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/12
 *)
program funcTest;
var
  a: Integer;
  b: Real;

Function AddOne(x:Integer) : Integer;
var
   funcIntVar : Integer;
   funcRealVar: Real;
   funcCharVar:Char;
   funcBoolVar:Boolean;
begin
  A := A+1;{global var value would be changed}
  AddOne := A;
  AddOne :=999;

  funcIntVar :=1;
  writeln('funcRealVar->',funcRealVar+1);
  writeln('funcBoolVar->',funcBoolVar);

  {if A>x then
    a :=a+1;}
end;

Var
  N : Real;

begin
  WriteLn(A);
  A := 0;
  N := AddOne(1); { N := 1.0000 }
  WriteLn(N); {1.0}
  WriteLn('A->',A);

  b := AddOne(999);
  WriteLn(b);
  WriteLn('A->',A);

  {AddOne := 1;} {Assign value to Func is not allowed here}
  {AddOne(1);} {Illegal procedure statement [AddOne(1)] which is not a procedure}
end.