(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator003;
var
  a,b,c : integer;
  d: real;
  e,f: Integer;
begin
  {some statements below are not valid}

  f := 10.0 mod 10; {Not work, operands of modulus should be int}
end.
