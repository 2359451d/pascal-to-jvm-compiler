(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator002;
var
  f: Integer;
begin
  f := 10 div 10;
  f := 10.0 div 10; {Not work since the operand cannot be real}
end.
