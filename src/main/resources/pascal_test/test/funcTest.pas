(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/12
 *)
program funcTest;
Type
  FuncType = Function: Integer;
var
  a: Integer;
  b: Real;

Function AddOne(x:Integer) : Integer;
begin
  A := A+1;
  AddOne := A;
end;

Var F : FuncType;
  N : Real;

begin
  A := 0;
  {F := @AddOne; { Assign AddOne to F, Don't call AddOne}
  N := AddOne(1); { N := 1 !!}
{  WriteLn(N);} {1}
{  WriteLn(F);} {2}
end.
