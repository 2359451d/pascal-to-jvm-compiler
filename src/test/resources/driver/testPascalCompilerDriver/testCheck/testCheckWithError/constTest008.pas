(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest;
var
  int1: Integer;
begin
  int1 := -2147483648;
  int1 := -2147483649; {underflows}
end.