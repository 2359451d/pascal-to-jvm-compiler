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
  bool1 := (10>0) and (0>5);
  bool1 := 10>0 and 0>5; {Not work,Remark the usage of parenthesis, logical operators has higher priority than relational}
end.