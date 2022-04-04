(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator005;
var
  f: Integer;
begin
  f := 10 + 10;
  f := 10 + 10.0;{Not work, right is real, but left is int}
end.
