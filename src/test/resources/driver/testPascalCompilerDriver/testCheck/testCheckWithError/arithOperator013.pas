(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator013;
var
  f: Integer;
begin
  f:= -1+1+1+1*1 div 1;
  f:= -1+1+1+1*1 / 1;{Not work, result is real}
end.
