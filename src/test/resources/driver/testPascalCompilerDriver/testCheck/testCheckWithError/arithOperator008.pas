(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator008;
var
  f: Integer;
begin
  f := 10 + (10 + 1);
  f := 10 + (10.0 + 1);{Not work, right is real, left is int}
end.
