(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest010;
var
  int1: Integer;
begin
  int1:= 2147483647;
  int1 := -(-2147483648); {--2147483648 overflows, right: expr}
end.
