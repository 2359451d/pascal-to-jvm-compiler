(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest002;
const
  {3 builtin constant, used without prior definition}
  intConst = 123; {int}
  intSignedConst = -123; {singed int}
  realConst = 12.75; {real}
var
  int1: Integer;
begin
  int1 := intConst;
  int1 := intSignedConst;

  int1:= realConst; {illegal assignment}
end.
