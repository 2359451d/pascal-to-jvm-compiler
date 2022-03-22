(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator012;
var
  flag : Boolean;
begin
  flag:= (1>3) and (3<4);
  flag:= - ((1>3) > (3<4)); {- cannot apply on boolean}
end.
