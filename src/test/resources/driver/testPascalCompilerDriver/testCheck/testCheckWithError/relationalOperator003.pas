(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/5
 *)
program relationalOperator;
var
  bool1: Boolean;
  int1: Integer;
  char1: Char;
begin
  bool1:= not true;
  bool1 := not 1; {Not work, operand must be boolean}
end.