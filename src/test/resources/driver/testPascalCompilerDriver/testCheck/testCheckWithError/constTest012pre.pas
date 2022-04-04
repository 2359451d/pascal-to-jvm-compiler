(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest012pre;

var
  int1: Integer;
begin
  int1 := +(+(-2147483648)); {-, works}
  int1 := -(+2147483648);{-+2147483648 works}
end.
