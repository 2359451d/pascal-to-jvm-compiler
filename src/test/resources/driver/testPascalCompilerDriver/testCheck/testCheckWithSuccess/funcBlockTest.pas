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

Function AddOne(x:Integer) : Integer;
const
  intConst = 10;
type
  SubrangeType = 1..10;
var
  a:Char;
  b: Char;
begin
  A := 'A';
  AddOne := 1;
  if A>'B' then
    a :='C';
end;

Var
  N : Real;

begin
  A := 0;
  N := AddOne(2); { N := 2 !!}

  WriteLn(N); {1}
end.
