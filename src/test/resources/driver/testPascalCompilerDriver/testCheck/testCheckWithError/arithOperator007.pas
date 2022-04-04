(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator007;
var
 f: Integer;
begin
  f:= 10 * 10 - 10;
  f:= 10 * 10.0 - 10;{Not work, right is real, left is int}
end.
