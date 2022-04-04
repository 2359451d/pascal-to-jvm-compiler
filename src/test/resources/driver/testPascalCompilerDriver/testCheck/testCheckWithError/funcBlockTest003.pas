(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/12
 *)
{$mode iso}
{$RANGECHECKS on}
program funcBlockTest003;
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
  AddOne := A; {a is char not work}
end;


begin
end.
