(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/12
 *)
{$mode iso}
{$RANGECHECKS on}
program funcBlockTest007;
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
begin
  AddOne:=1;
end;

Var
  N : Real;

begin
  N := AddOne(2);
  AddOne(100); {Illegal procedure statement  which is not a procedure}
end.
