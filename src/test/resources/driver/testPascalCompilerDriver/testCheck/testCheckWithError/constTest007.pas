(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest;
const
  minIntConst =-2147483648 ; {Works}
var
  int1: Integer;
begin
  {[-2147483648] and [2147483647]}
  int1 := +minIntConst;
  int1 := -(minIntConst); {overflow}
end.