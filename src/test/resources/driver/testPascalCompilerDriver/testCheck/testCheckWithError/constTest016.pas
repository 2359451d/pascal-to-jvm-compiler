(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest;

var
  flagVar: Boolean;
begin
  flagVar := 1> MaxInt;
  flagVar := 1 > 2147483648; {Illegal assignment, right operand overflows}
end.