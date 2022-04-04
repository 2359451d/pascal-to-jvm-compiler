(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest015;
var
  int1: Integer;
begin
  int1 := -(+2147483648); {works}
  int1 := -(+(-2147483648)); {overflows}
end.
