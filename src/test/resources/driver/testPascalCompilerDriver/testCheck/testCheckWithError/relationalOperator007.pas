(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/5
 *)
program relationalOperator007;
var
  bool1: Boolean;
  int1: Integer;
  char1: Char;
begin
  bool1 := 10>10;
  bool1 := 10>true; {Not work, operands type are not compatible}
end.
