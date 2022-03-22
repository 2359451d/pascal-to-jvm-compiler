(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator004;
var
  f: Integer;
begin
  f := 10 + 1;
  f := 10 + true;{Not work, operands must be real or int}
end.
