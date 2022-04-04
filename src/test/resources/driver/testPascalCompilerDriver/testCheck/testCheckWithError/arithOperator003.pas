(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator003;
var
  f: Integer;
begin
  f := 10 mod 10;
  f := 10.0 mod 10; {Not work, operands of modulus should be int}
end.
