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
begin
  A := A+1;
  AddOne := A;
  if A>x then
    a :=a+1;
end;

Var
  N : Real;

begin
  A := 0;
  {F := @AddOne; { Assign AddOne to F, Don't call AddOne}
  N := AddOne(1); { N := 1 !!}

  AddOne := 1; {Assign value to Func is not allowed here}
  AddOne(1); {Illegal procedure statement [AddOne(1)] which is not a procedure}
{  WriteLn(N);} {1}
{  WriteLn(F);} {2}
end.
