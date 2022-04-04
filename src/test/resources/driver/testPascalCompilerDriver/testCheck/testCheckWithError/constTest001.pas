(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest001;
const
  strConst = 'ABC';
var
  charVar: char;
begin
  charVar := 'A';
  charVar := strConst; {Illegal}
end.
