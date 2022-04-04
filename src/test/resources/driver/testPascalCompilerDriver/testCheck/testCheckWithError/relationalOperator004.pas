(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/5
 *)
program relationalOperator004;
var
  bool1: Boolean;
  int1: Integer;
  char1: Char;
begin
  bool1:= true and true;
  bool1 := 1 and true; {Not work, operand must be boolean}
end.
