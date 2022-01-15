(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/6
 *)
{$mode iso}
{$RANGECHECKS on}
program recursiveFunc;
var
  num, f: integer;
function fact(x: integer): integer; (* calculates factorial of x - x! *)
begin
  if x=0 then
    fact := 1
  else
    fact := x * fact(x-1); (* recursive call *)
end; { end of function fact}
begin
  f := fact(num);
end.