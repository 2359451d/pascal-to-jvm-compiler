(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest006;
const
  maxintConst = MaxInt;
var
  int1: Integer;
begin
  {[-2147483648] and [2147483647]}
  int1 := maxintConst;
  int1 := +2147483648; {overflow}
end.
