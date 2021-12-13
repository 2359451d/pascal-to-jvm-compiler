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

  bool1 := not 1; {Not work, operand must be boolean}
  bool1 := 1 and true; {Not work, operand must be boolean}
  bool1 := 1.0 or 1.0; {Not work, operand must be boolean}
  int1 := true and false; {Not work, output is boolean}
  bool1 := 10>true; {Not work, operands type are not compatible}
  bool1 := 10>0 and 0>5; {Not work,Remark the usage of parenthesis, logical operators has higher priority than relational}

  bool1 := char1 <'a'; {works,though left is char, right is string}
end.