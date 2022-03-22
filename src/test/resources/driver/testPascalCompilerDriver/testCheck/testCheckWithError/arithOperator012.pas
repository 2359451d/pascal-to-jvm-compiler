(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator012;
var
  f: real;
begin
  {some statements below are not valid}
  f:= -1.0;
  f:= -'a';{Not work, operand must be int or real}
end.
