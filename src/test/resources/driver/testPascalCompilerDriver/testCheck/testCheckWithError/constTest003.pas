(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest003;
const
  flagT = true;
var
  flagVar: Boolean;
begin
  flagVar := flagT;
  flagVar := -(-flagT); {Illegal assignment [flagVar:=-(-true)], monadic operator}
end.
