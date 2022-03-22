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
  char1 := 'a';
  bool1 := false and false;
  bool1 := true and false;
  bool1 := not true;
  bool1 := true or false;
  bool1 := (10>0) and (0>5);
  bool1 := 'a' < 'b';
  bool1 := 1 <> 10;
end.