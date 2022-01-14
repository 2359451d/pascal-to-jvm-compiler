(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/12
 *)
{$mode iso}
{$RANGECHECKS on}
program funcBlockTest;
var
  a: Integer;
  b: Real;

Function AddOne(x:Integer) : Integer;
const
  intConst = 10;
type
  SubrangeType = 1..10;
var
  a:Char;
  b: Char;
  subrangeType: 1..11; {duplicate in func AddOne}
begin
  A := A+1; {a is char not work}
  AddOne := A; {a is char not work}
  if A>x then {a is char, x is int, not work}
    a :=a+1; {a is char not work}
end;

Var
  N : Real;

begin
  A := 0;
  {F := @AddOne; { Assign AddOne to F, Don't call AddOne}
  N := AddOne(2); { N := 2 !!}

  AddOne := 3; {Assign value to Func is not allowed here}
  AddOne(100); {Illegal procedure statement [AddOne(1)] which is not a procedure}
{  WriteLn(N);} {1}
{  WriteLn(F);} {2}
end.