(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/5
 *)
program relationalOperator005;
var
  bool1: Boolean;
  int1: Integer;
  char1: Char;
begin
  bool1 := true or true;
  bool1 := 1.0 or 1.0; {Not work, operand must be boolean}
end.
