(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest;
const
  flagT = true;
  flag = flagT;
var
  flagVar: Boolean;
begin
  flagVar:=flag;
  flag := false; {Not allowed, Variable identifier expected, const reassignment}
end.