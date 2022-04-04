(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/5
 *)
program relationalOperator009;
var
  bool1: Boolean;
  char1: Char;
begin
  bool1 := char1 <'a'; {works,though left is char, right is string}
  bool1 := char1 <'abc';{not work}
end.
