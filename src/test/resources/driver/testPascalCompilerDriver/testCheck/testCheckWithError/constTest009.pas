(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest009;
const
  flagT = true;
  flag = flagT;
var
  flagVar: Boolean;
begin
  flagVar:=flag;
  flag := false; {Not allowed, Variable identifier expected, const reassignment}
end.
