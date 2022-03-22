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

Function AddOnew(x:Integer) : Integer;
const
  intConst = 10;
type
  SubrangeType = 1..10;
var
  a:Char;
  b: Char;
begin
  A := 'A';
  AddOnew := 1;
  if A>'B' then
    a :='C';
end;

Function AddOne(x:Integer) : Integer;
const
  intConst = 10;
type
  SubrangeType = 1..10;
var
  a:Char;
  b: Char;
begin
  AddOne := 1;
  if A>x then {a is char, x is int, not work}
    a :='C';
end;


begin
end.